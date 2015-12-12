package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.LineSegment;
import com.ffbit.geometry.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpperLowerConvexHull {

    public List<Point> convexHull(List<Point> points) {
        List<Point> sortedPoints = sortPoints(points);

        List<Point> upperHull = buildUpperHull(sortedPoints);
        List<Point> lowerHull = buildLowerHull(sortedPoints);

        return combineUpperAndLowerHulls(upperHull, lowerHull);
    }

    private List<Point> sortPoints(List<Point> points) {
        List<Point> sortedPoints = new ArrayList<>(points);
        Collections.sort(sortedPoints);

        return sortedPoints;
    }

    private List<Point> buildUpperHull(List<Point> points) {
        List<Point> upperHull = new ArrayList<>();
        upperHull.add(points.get(0));
        upperHull.add(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            Point point = points.get(i);

            while (upperHull.size() > 1) {
                int beforeLastHullPointIndex = upperHull.size() - 2;
                int lastHullPointIndex = upperHull.size() - 1;
                LineSegment segment = new LineSegment(
                        upperHull.get(beforeLastHullPointIndex),
                        upperHull.get(lastHullPointIndex));

                if (!segment.pointIsToTheRightSide(point)) {
                    upperHull.remove(lastHullPointIndex);
                } else {
                    break;
                }
            }

            upperHull.add(point);
        }

        return upperHull;
    }

    private List<Point> buildLowerHull(List<Point> points) {
        List<Point> lowerHull = new ArrayList<>();
        lowerHull.add(points.get(points.size() - 1));
        lowerHull.add(points.get(points.size() - 2));

        for (int i = points.size() - 3; i >= 0; i--) {
            Point point = points.get(i);

            while (lowerHull.size() > 1) {
                int beforeLastHullPointIndex = lowerHull.size() - 2;
                int lastHullPointIndex = lowerHull.size() - 1;
                LineSegment segment = new LineSegment(
                        lowerHull.get(beforeLastHullPointIndex),
                        lowerHull.get(lastHullPointIndex));

                if (!segment.pointIsToTheRightSide(point)) {
                    lowerHull.remove(lastHullPointIndex);
                } else {
                    break;
                }
            }

            lowerHull.add(point);
        }

        return lowerHull;
    }

    private List<Point> combineUpperAndLowerHulls(List<Point> upperHull,
                                                  List<Point> lowerHull) {
        upperHull.addAll(lowerHull.subList(1, lowerHull.size() - 1));

        return upperHull;
    }

}
