package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.LineSegment;
import com.ffbit.geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A O(h N) Jarvis's march Gift Wrapping convex hull algorithm implementation.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Gift_wrapping_algorithm">Gift wrapping algorithm</a>
 * </p>
 */
public class GiftWrappingConvexHull implements ConvexHull {

    @Override
    public List<Point> convexHull(List<Point> points) {
        Point lowestLeftmostPoint = findLowestLeftmostPoint(points);

        List<Point> convexHull = new ArrayList<>();
        Point pointOnHull = lowestLeftmostPoint;

        do {
            convexHull.add(pointOnHull);

            Point endPoint = points.get(0);
            for (Point point : points) {
                LineSegment segment = new LineSegment(pointOnHull, point);

                if (segment.pointIsToTheRightSide(endPoint)
                        || segment.pointLiesOnSegment(endPoint)) {
                    endPoint = point;
                }
            }

            pointOnHull = endPoint;
        } while (!pointOnHull.equals(lowestLeftmostPoint));

        return convexHull;
    }

    private Point findLowestLeftmostPoint(List<Point> points) {
        Point lowestLeftmostPoint = points.get(0);

        for (Point point : points) {
            if (less(point, lowestLeftmostPoint)) {
                lowestLeftmostPoint = point;
            }
        }

        return lowestLeftmostPoint;
    }

    private boolean less(Point a, Point b) {
        return a.compareTo(b) < 0;
    }

}
