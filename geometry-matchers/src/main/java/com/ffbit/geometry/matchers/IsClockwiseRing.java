package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.List;
import java.util.Optional;

/**
 * Checks if all points in a ring make clockwise turn.
 */
public class IsClockwiseRing extends TypeSafeDiagnosingMatcher<List<Point>> {

    @Override
    protected boolean matchesSafely(List<Point> points,
                                    Description mismatchDescription) {
        if (points.size() < 3) {
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

    private Optional<Point> findNonClockwisePoint(List<Point> points) {
        int length = points.size();

        for (int i = 0; i < length; i++) {
            int a = i;
            int b = (i + 1) % length;
            int c = (i + 2) % length;

            if (!clockwise(points.get(a), points.get(b), points.get(c))) {
                return Optional.of(points.get(c));
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
    public static Matcher<List<Point>> clockwiseRing() {
        return new IsClockwiseRing();
    }

}
