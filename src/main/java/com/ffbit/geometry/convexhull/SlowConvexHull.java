package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.LineSegment;
import com.ffbit.geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A O(N<sup>3</sup>) convex hull algorithm from the
 * "Computational Geometry: Algorithms and Applications" book.
 * </p>
 */
public class SlowConvexHull {

    public List<Point> convexHull(List<Point> points) {
        List<LineSegment> convexHullEdges = new ArrayList<>();

        for (int p = 0; p < points.size(); p++) {
            Point pPoint = points.get(p);
            for (int q = 0; q < points.size(); q++) {
                if (p == q) {
                    continue;
                }

                Point qPoint = points.get(q);
                LineSegment edge = new LineSegment(pPoint, qPoint);

                boolean valid = true;

                for (int i = 0; i < points.size() && valid; i++) {
                    if (i == q || i == p) {
                        continue;
                    }

                    if (!(edge.pointIsToTheRightSide(points.get(i))
                            || edge.pointLiesOnSegment(points.get(i)))) {
                        valid = false;
                    }
                }

                if (valid) {
                    convexHullEdges.add(edge);
                }
            }
        }

        return constructConvexHull(convexHullEdges);
    }

    private List<Point> constructConvexHull(List<LineSegment> convexHullEdges) {
        List<Point> convexHullPoints = new ArrayList<>(convexHullEdges.size());

        LineSegment startEdge = convexHullEdges.remove(0);

        convexHullPoints.add(startEdge.getA());
        convexHullPoints.add(startEdge.getB());

        Point nextOriginPoint = startEdge.getB();

        while (convexHullEdges.size() > 1) {
            for (int i = 0; i < convexHullEdges.size(); i++) {
                LineSegment edge = convexHullEdges.get(0);
                Point originPoint = edge.getA();

                if (nextOriginPoint.equals(originPoint)) {
                    Point destinationPoint = edge.getB();
                    convexHullPoints.add(destinationPoint);
                    nextOriginPoint = destinationPoint;

                    convexHullEdges.remove(i);
                    break;
                }
            }
        }

        return convexHullPoints;
    }

}
