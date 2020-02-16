import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

class KdNode {
    public final Point2D point;
    public final boolean split;
    public KdNode left;
    public KdNode right;
    public static final boolean HORIZONTAL = true;
    public static final boolean VERTICAL = false;
    
    public KdNode(Point2D point, boolean split) {
        this.point = point;
        this.split = split;
        left = null;
        right = null;
    }
}

public class KdTree {
    private KdNode root;
    private int nodes;
    
    public KdTree() {
        root = null;
        nodes = 0;
    }
    
    public boolean isEmpty() {
        return nodes == 0;
    }
    
    public int size() {
        return nodes;
    }
    
    public void insert(Point2D point) {
        if (root == null) {
            root = new KdNode(point, KdNode.VERTICAL);
            nodes++;
            return;
        }
        
        KdNode itr = root;
        while (itr != null) {
            if (itr.point.equals(point)) return;
            boolean split = itr.split;
            KdNode newNode = new KdNode(point, !split);
            if (split == KdNode.VERTICAL) {
                if (point.x() < itr.point.x()) {
                    if (itr.left == null) {
                        itr.left = newNode;
                        nodes++;
                        return;
                    }
                    else itr = itr.left;
                } else {
                    if (itr.right == null) {
                        itr.right = newNode;
                        nodes++;
                        return;
                    }
                    else itr = itr.right;
                }
            } else {
                if (point.y() < itr.point.y()) {
                    if (itr.left == null) {
                        itr.left = newNode;
                        nodes++;
                        return;
                    }
                    else itr = itr.left;
                } else {
                    if (itr.right == null) {
                        itr.right = newNode;
                        nodes++;
                        return;
                    }
                    else itr = itr.right;
                }
            }
        }
    }
    
    private boolean search(KdNode rt, Point2D p) {
        if (rt == null) return false;
        if (rt.point.equals(p)) return true;
        if (rt.split == KdNode.VERTICAL) {
            if (p.x() < rt.point.x()) return search(rt.left, p);
            else return search(rt.right, p);
        } else {
            if (p.y() < rt.point.y()) return search(rt.left, p);
            else return search(rt.right, p);
        }
    }
    
    public boolean contains(Point2D p) {
        return search(root, p);
    }
    
    public void draw() {
        StdDraw.setCanvasSize();
        preorderDraw(root, null);
    }
    
    private static boolean isGreater(Point2D p1, Point2D p2, boolean split) {
        if (split == KdNode.VERTICAL) {
            return p1.x() > p2.x();
        }
        return p1.y() > p2.y();
    }
    
    private void preorderDraw(KdNode rt, KdNode pre) {
        if (rt == null) return;
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.02);
        StdDraw.setScale();
        rt.point.draw();
        StdDraw.setPenRadius(0.001);
        if (rt.split == KdNode.VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            double yStart;
            double yEnd;
            
            if (pre == null) {
                yStart = 0;
                yEnd = 512;
            } else {
                if (isGreater(rt.point, pre.point, !rt.split)) {
                    yEnd = pre.point.y();
                    yStart = 1;
                } else {
                    yStart = pre.point.y();
                    yEnd = 0;
                }
            }
            
            rt.point.drawTo(new Point2D(rt.point.x(), yStart));
            rt.point.drawTo(new Point2D(rt.point.x(), yEnd));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            
            double xStart;
            double xEnd;
            
            if (pre == null) {
                xStart = 0;
                xEnd = 1;
            } else {
                if (isGreater(rt.point, pre.point, !rt.split)) {
                    xStart = pre.point.x();
                    xEnd = 1;
                } else {
                    xStart = 0;
                    xEnd = pre.point.x();
                }
            }
            
            
            rt.point.drawTo(new Point2D(xStart, rt.point.y()));
            rt.point.drawTo(new Point2D(xEnd, rt.point.y()));
        }
        StdDraw.pause(1000);
        preorderDraw(rt.left, rt);
        preorderDraw(rt.right, rt);
    }
    
    private void rangeSearch(KdNode rt, RectHV rect, ArrayList<Point2D> list) {
        if (rt == null) return;
        if (rect.contains(rt.point)) list.add(rt.point);
        boolean split = rt.split;
        if (split == KdNode.VERTICAL) {
            double minx = rect.xmin();
            double maxx = rect.xmax();
            double ptx = rt.point.x();
            if (minx < ptx) rangeSearch(rt.left, rect, list);
            if (maxx > ptx) rangeSearch(rt.right, rect, list);
        } else {
            double miny = rect.ymin();
            double maxy = rect.ymax();
            double pty = rt.point.y();
            if (miny < pty) rangeSearch(rt.left, rect, list);
            if (maxy > pty) rangeSearch(rt.right, rect, list);
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> pointsInRect = new ArrayList<Point2D>();
        rangeSearch(root,  rect, pointsInRect);
        return pointsInRect;
    }
    
    private Point2D searchMin(KdNode root, Point2D minSoFar, Point2D queryPoint) {
        if (root == null) return minSoFar;
        if (minSoFar == null || root.point.distanceTo(queryPoint) < minSoFar.distanceTo(queryPoint)) {
            minSoFar = root.point;
        }
        Point2D minLeft = searchMin(root.left, minSoFar, queryPoint);
        Point2D minRight = searchMin(root.right, minSoFar, queryPoint);
        
        double distSoFar = minSoFar.distanceTo(queryPoint);
        double distLeft = minLeft.distanceTo(queryPoint);
        double distRight = minRight.distanceTo(queryPoint);
        
        if (distSoFar < distLeft && distSoFar < distRight) return minSoFar;
        else if (distLeft < distSoFar && distLeft < distRight) return minLeft;
        else return minRight;
    }
    
    public Point2D nearest(Point2D point) {
        if (isEmpty()) return null;
        return searchMin(root, null, point);
    }
    
    public static void main(String args[]) {
        KdTree kd = new KdTree();
//        kd.insert(new Point2D(0.7, 0.2));
//        kd.insert(new Point2D(0.8, 0.3));
//        kd.insert(new Point2D(0.5, 0.4));
//        kd.insert(new Point2D(0.2, 0.3));
//        kd.insert(new Point2D(0.4, 0.7));
//        kd.insert(new Point2D(0.9, 0.6));
//        kd.insert(new Point2D(0.8, 0.5));
//        kd.insert(new Point2D(0.95, 0.7));
          kd.insert(new Point2D(0.1, 0.1));
          kd.insert(new Point2D(0.1, 0.1));
          System.out.println(kd.size());
    }
}
