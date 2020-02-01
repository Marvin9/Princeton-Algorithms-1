import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {

	private final LineSegment[] segment;
	
	public FastCollinearPoints(Point[] points) {
		validate(points);
		
		int len = points.length;
		Point[] pCopy = Arrays.copyOf(points, len);
		ArrayList<LineSegment> segmentArrayList = new ArrayList<LineSegment>();
		
		for (int i = 0; i < len; i++) {
			Comparator<Point> slpOrder = points[i].slopeOrder();
			mergeSort(pCopy, slpOrder);
			
			int j = 0;
			int counter = 0;
			
			while (j < len) {
				int cmp = points[i].compareTo(pCopy[j]);
				
				if (cmp >= 0) {
					j++;
					continue;
				}
				
				j++;
				counter++;
				
				while (j < len && slpOrder.compare(pCopy[j - 1], pCopy[j]) == 0) {
					j++;
					counter++;
				}
				
				if (counter >= 3) {
					segmentArrayList.add(new LineSegment(points[i], pCopy[j - 1]));
				}
				
				counter = 0;
			}
		}
		
		segment = segmentArrayList.toArray(new LineSegment[segmentArrayList.size()]);
	}
	
	private void validate(Point[] points) {
		if (points == null) throw new IllegalArgumentException();
		for (int i = 0, iBound = points.length; i < iBound; i++) {
			if (points[i] == null) throw new IllegalArgumentException();
			for (int j = i + 1; j < iBound; j++) {
				if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
			}
		}
	}
	
	private void merge(Point[] points, Point[] aux, int start, int mid, int end, Comparator<Point> relativePoint) {
		for (int i = start; i <= end; i++) {
			aux[i] = points[i];
		}

		int i = start, j = mid + 1;
		for (int k = start; k <= end; k++) {
			if (i > mid)
				points[k] = aux[j++];
			else if (j > end)
				points[k] = aux[i++];
			else if (relativePoint.compare(aux[i], aux[j]) < 0)
				points[k] = aux[i++];
			else if (relativePoint.compare(aux[i], aux[j]) > 0)
				points[k] = aux[j++];
			else {
				int minYAxis = aux[i].compareTo(aux[j]);
				if (minYAxis < 0)
					points[k] = aux[i++];
				else
					points[k] = aux[j++];
			}
		}
	}

	private void mergeSort(Point[] points, Point[] aux, int start, int end, Comparator<Point> relativePoint) {
		if (end <= start)
			return;
		int mid = (end + start) / 2;

		mergeSort(points, aux, start, mid, relativePoint);
		mergeSort(points, aux, mid + 1, end, relativePoint);

		merge(points, aux, start, mid, end, relativePoint);
	}

	private void mergeSort(Point[] points, Comparator<Point> relativePoint) {
		int len = points.length;
		Point[] aux = new Point[len];
		mergeSort(points, aux, 0, len - 1, relativePoint);
	}

	
	public int numberOfSegments() {
		return segment.length;
	}
	
	public LineSegment[] segments() {
		return segment.clone();
	}
	
	public static void main(String[] args) {
		// read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();		
	}
}
