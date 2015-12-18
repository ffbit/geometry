package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.LineSegment;
import com.ffbit.geometry.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * A O(h N<sup>2</sup>) Gift Wrapping convex hull algorithm.
 * </p>
 */
public class GiftWrappingConvexHull implements ConvexHull {

    @Override
    public List<Point> convexHull(List<Point> points) {
        List<Point> sortedPoints = sortPoints(points);

        List<Point> convexHull = new ArrayList<>();
        Point startPoint = sortedPoints.get(0);
        Point convexPoint = startPoint;

        do {
            convexHull.add(convexPoint);
            convexPoint = findNextConvexPoint(sortedPoints, convexPoint);
        } while (!startPoint.equals(convexPoint));

        return convexHull;
    }

    private Point findNextConvexPoint(List<Point> points, Point convexPoint) {
        Point nextConvexPoint = convexPoint;

        for (Point candidateConvexPoint : points) {
            LineSegment segment =
                    new LineSegment(convexPoint, candidateConvexPoint);
            boolean valid = true;

            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);

                if (!(segment.pointIsToTheRightSide(point)
                        || segment.pointLiesOnSegment(point))) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                nextConvexPoint = candidateConvexPoint;
                break;
            }
        }

        return nextConvexPoint;
    }

    private List<Point> sortPoints(List<Point> points) {
        List<Point> sortedPoints = new ArrayList<>(points);
        Collections.sort(sortedPoints);

        return sortedPoints;
    }

}
