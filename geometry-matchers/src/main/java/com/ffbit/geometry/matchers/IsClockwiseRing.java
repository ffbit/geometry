package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Optional;

/**
 * Checks if all points in a ring make clockwise turn.
 */
public class IsClockwiseRing extends TypeSafeDiagnosingMatcher<Point[]> {

    @Override
    protected boolean matchesSafely(Point[] points,
                                    Description mismatchDescription) {
        if (points.length < 3) {
            mismatchDescription.appendText("not enough points to make a ring ")
                    .appendValue(points);
            return false;
        }

        Optional<Point> nonClockwisePoint = findNonClockwisePoint(points);

        if (!nonClockwisePoint.isPresent()) {
            return true;
        }

        mismatchDescription.appendText("found a non clockwise point ")
                .appendValue(nonClockwisePoint.get())
                .appendText(" among ")
                .appendValue(points);

        return false;
    }

    private Optional<Point> findNonClockwisePoint(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            int aIndex = i;
            int bIndex = (i + 1) % points.length;
            int cIndex = (i + 2) % points.length;

            if (!clockwise(points[aIndex], points[bIndex], points[cIndex])) {
                return Optional.of(points[cIndex]);
            }
        }

        return Optional.empty();
    }

    private boolean clockwise(Point a, Point b, Point c) {
        return twiceTheSignedArea(a, b, c) < 0;
    }

    private int twiceTheSignedArea(Point a, Point b, Point c) {
        return (b.getX() - a.getX()) * (c.getY() - a.getY())
                - (b.getY() - a.getY()) * (c.getX() - a.getX());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a clockwise ring");
    }

    @Factory
    public static Matcher<Point[]> clockwiseRing() {
        return new IsClockwiseRing();
    }

}
