package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.LineSegment;
import com.ffbit.geometry.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class IncrementalConvexHull implements ConvexHull {

    @Override
    public List<Point> convexHull(List<Point> points) {
        Iterator<Point> sortedPoints = sortPoints(points).iterator();

        List<Point> convexHull = constructTriangleConvexHull(sortedPoints);
        while (sortedPoints.hasNext()) {
            Point point = sortedPoints.next();

            int leftTangentPointIndex = leftTangentPointIndex(convexHull, point);
            int rightTangentPointIndex = rightTangentPointIndex(convexHull, point);

            addPointToConvexHull(convexHull, point, leftTangentPointIndex, rightTangentPointIndex);
        }

        return convexHull;
    }

    private void addPointToConvexHull(List<Point> convexHull,
                                      Point point,
                                      int leftTangentPointIndex,
                                      int rightTangentPointIndex) {
        if (leftTangentPointIndex == 0) {
            leftTangentPointIndex = convexHull.size();
        }

        int left = Math.min(leftTangentPointIndex, rightTangentPointIndex);
        int right = Math.max(leftTangentPointIndex, rightTangentPointIndex);

        if (left < right) {
            convexHull.subList(left + 1, right).clear();
        }

        if (left < convexHull.size()) {
            convexHull.add(left + 1, point);
        } else {
            convexHull.add(point);
        }
    }

    private int leftTangentPointIndex(List<Point> convexHull, Point point) {
        int size = convexHull.size();
        int bPointIndex;
        for (bPointIndex = 0; bPointIndex < size; bPointIndex++) {
            Point a = convexHull.get((bPointIndex - 1 + size) % size);
            Point b = convexHull.get(bPointIndex);
            Point c = convexHull.get((bPointIndex + 1) % size);

            LineSegment ab = new LineSegment(a, b);
            LineSegment bc = new LineSegment(b, c);

            if ((ab.pointIsToTheLeftSide(point) || ab.pointIsCollinear(point))
                    && bc.pointIsToTheRightSide(point)) {
                break;
            }
        }

        return bPointIndex;
    }

    private int rightTangentPointIndex(List<Point> convexHull, Point point) {
        int size = convexHull.size();
        int bPointIndex;
        for (bPointIndex = 0; bPointIndex < size; bPointIndex++) {
            int aPointIndex = (bPointIndex - 1 + size) % size;
            Point a = convexHull.get(aPointIndex);
            Point b = convexHull.get(bPointIndex);
            Point c = convexHull.get((bPointIndex + 1) % size);

            LineSegment ab = new LineSegment(a, b);
            LineSegment bc = new LineSegment(b, c);

            if (ab.pointIsToTheRightSide(point)
                    && bc.pointIsToTheLeftSide(point)) {
                break;
            }

            if (ab.pointIsCollinear(point)) {
                return aPointIndex;
            }
        }

        return bPointIndex;
    }

    private List<Point> constructTriangleConvexHull(Iterator<Point> points) {
        Point a = points.next();
        Point b = points.next();

        List<Point> triangleConvexHull = new ArrayList<>();
        triangleConvexHull.add(a);

        while (points.hasNext() && triangleConvexHull.size() < 3) {
            Point c = points.next();
            LineSegment ac = new LineSegment(a, c);

            if (ac.pointLiesOnSegment(b)) {
                b = c;
                continue;
            }

            if (ac.pointIsToTheRightSide(b)) {
                triangleConvexHull.add(c);
                triangleConvexHull.add(b);
            } else {
                triangleConvexHull.add(b);
                triangleConvexHull.add(c);
            }
        }

        return triangleConvexHull;
    }

    private List<Point> sortPoints(List<Point> points) {
        List<Point> sortedPoints = new ArrayList<>(points);
        Collections.sort(sortedPoints);

        return sortedPoints;
    }

}
