import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private SET<Point2D> points;
    
    public PointSET() {
        points = new SET<Point2D>();
    }
    
    public boolean isEmpty() {
        return points.isEmpty();
    }
    
    public int size() {
        return points.size();
    }
    
    public void insert(Point2D p) {
        validateNull(p);
        points.add(p);
    }
    
    public boolean contains(Point2D p) {
        validateNull(p);
        return points.contains(p);
    }
    
    public void draw() {
        StdDraw.setCanvasSize();
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.02);
        for (Point2D point: points) {
            point.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        validateNull(rect);
        ArrayList<Point2D> lst = new ArrayList<Point2D>();
        for (Point2D point: points) {
            if (rect.contains(point)) lst.add(point);
        }
        return lst;
    }
    
    public Point2D nearest(Point2D point) {
        validateNull(point);
        if (points.isEmpty()) return null;
        Point2D mn = new Point2D(0, 0);
        double dist = Double.MAX_VALUE;
        for (Point2D pt: points) {
            double tmpdist = pt.distanceTo(point);
            if (tmpdist < dist) {
                dist = tmpdist;
                mn = pt;
            }
        }
        return mn;
    }
    
    private void validateNull(Object ob) {
        if (ob == null) throw new IllegalArgumentException();
    }
    
    public static void main(String args[]) {
        PointSET ps = new PointSET();
        Point2D p = new Point2D(0.1, 0.2);
        ps.insert(p);
        p = new Point2D(0.3, 0.6);
        ps.insert(p);
        p = new Point2D(0.4, 0.7);
        ps.insert(p);
        System.out.println(ps.nearest(new Point2D(0.2, 0.6)));
    }
}
