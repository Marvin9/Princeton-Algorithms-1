import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
	
	private final ArrayList<LineSegment> lineSeg;
	
	public BruteCollinearPoints(Point[] points) {
		validate(points);
		
		int i, j, k, l_, len = points.length;
		lineSeg = new ArrayList<LineSegment>();
		
		Arrays.sort(points);
		
		
		for (i = 0; i < len - 3; i++) {
			for (j = i + 1; j < len - 2; j++) {
				for (k = j + 1; k < len - 1; k++) {
					for (l_ = k + 1; l_ < len; l_++) {
						if (
							points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
							&& points[i].slopeTo(points[k]) == points[i].slopeTo(points[l_])
						) {
							lineSeg.add(new LineSegment(points[i], points[l_]));
						}
					}
				}
			}
		}
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
	
	public int numberOfSegments() {
		return lineSeg.size();
	}
	
	public LineSegment[] segments() {
		return lineSeg.toArray(new LineSegment[lineSeg.size()]);
	}
	
	public static void main(String[] args) {
//		Point p1 = new Point(19, 10);
//		Point p2 = new Point(18, 10);
//		Point p3 = new Point(32, 10);
//		Point p4 = new Point(21, 10);
//		Point p5 = new Point(1234, 25678);
//		Point p6 = new Point(14, 10);
//		Point p1 = new Point(10, 0);
//		Point p2 = new Point(0, 10);
//		Point p3 = new Point(3, 7);
//		Point p4 = new Point(7, 3);
//		Point p5 = new Point(20, 21);
//		Point p6 = new Point(3, 4);
//		Point p7 = new Point(14, 15);
//		Point p8 = new Point(6, 7);
//		
		Point p1 = new Point(2893, 15116);
		Point p2 = new Point(13605, 15116);
		Point p3 = new Point(8486, 15116);
		Point p4 = new Point(13231, 15116);
		
		Point[] parr = {
			p1, p2, p3, p4
		};
		
		BruteCollinearPoints b1 = new BruteCollinearPoints(parr);
		
		LineSegment[] l = b1.segments();
		
		for (int i = 0; i < l.length; i++) {
			System.out.println(l[i].toString());
		}
	}
}
