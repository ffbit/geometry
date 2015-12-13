package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.LineSegment;
import com.ffbit.geometry.Point;

import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(convexHullEdges, (n, m) -> n.getA().compareTo(m.getA()));

        List<Point> convexHullPoints = new ArrayList<>(convexHullEdges.size());
        LineSegment currentEdge = convexHullEdges.get(0);
        convexHullPoints.add(currentEdge.getA());
        convexHullPoints.add(currentEdge.getB());

        while (convexHullPoints.size() < convexHullEdges.size()) {
            LineSegment nextEdge = findNextEdge(convexHullEdges, currentEdge.getB());
            convexHullPoints.add(nextEdge.getB());
            currentEdge = nextEdge;
        }

        return convexHullPoints;
    }

    private LineSegment findNextEdge(List<LineSegment> edges, Point startPoint) {
        int low = 0;
        int high = edges.size() - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            LineSegment middleEdge = edges.get(middle);
            int cmp = startPoint.compareTo(middleEdge.getA());

            if (cmp == 0) {
                return middleEdge;
            } else if (cmp < 0) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }

        throw new IllegalArgumentException("Found a disjoint point "
                + startPoint + " on edges" + edges);
    }

}
