package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class IsClockwiseRingTest {
    private Matcher<List<Point>> clockwiseRing = IsClockwiseRing.clockwiseRing();

    @Test
    @Parameters(method = "clockwiseRings")
    public void itShouldMatchClockwiseRing(List<Point> points) {
        assertThat(points, is(clockwiseRing));
    }

    private Object[][] clockwiseRings() {
        return new Object[][]{
                {asList(p(0, 0), p(0, 1), p(1, 1))},
                {asList(p(0, 0), p(0, 1), p(1, 1), p(1, 0))}
        };
    }

    @Test
    @Parameters(method = "notEnoughPointsForRing,notClockwiseRings")
    public void itShouldNotMatchClockwiseRing(List<Point> points) {
        assertThat(points, is(not(clockwiseRing)));
    }

    private Object[][] notEnoughPointsForRing() {
        return new Object[][]{
                {asList()},
                {asList(p(0, 1))},
                {asList(p(0, 1), p(0, 0))}
        };
    }

    private Object[][] notClockwiseRings() {
        return new Object[][]{
                {asList(p(0, 1), p(0, 0), p(1, 1))},
                {asList(p(0, 0), p(0, 1), p(0, 2), p(0, 1))},
                {asList(p(0, 0), p(1, 0), p(1, 1), p(0, 1))}
        };
    }

    @Test
    public void nullRingShouldNotBeClockwise() {
        assertThat(null, is(not(clockwiseRing)));
    }

    @Test
    public void itShouldDescribeItself() {
        Description description = new StringDescription();

        clockwiseRing.describeTo(description);

        assertThat(description, hasToString("a clockwise ring"));
    }

    @Test
    public void itShouldMismatchOnNullRing() {
        assertThat(null, is(not(clockwiseRing)));
    }

    @Test
    public void itShouldProvideMismatchDescriptionOnNullRing() {
        Description description = new StringDescription();

        clockwiseRing.describeMismatch(null, description);

        assertThat(description, hasToString("was null"));
    }

    @Test
    @Parameters(method = "notEnoughPointsForRing")
    public void itShouldMismatchWhenNotEnoughPoints(List<Point> points) {
        assertThat(points, is(not(clockwiseRing)));
    }

    @Test
    @Parameters(method = "notEnoughPointsForRing")
    public void itShouldProvideMismatchDescriptionWhenNotEnoughPoints(
            List<Point> points) {
        Description description = new StringDescription();

        clockwiseRing.describeMismatch(points, description);

        assertThat(description,
                hasToString(startsWith("not enough points to make a ring ")));
    }

    @Test
    @Parameters(method = "nonClockwiseRingsWithFirstNotClockwiseTurnPoint")
    public void itShouldMismatchOnNonClockwiseRing(List<Point> points,
                                                   Point point) {
        assertThat(points, is(not(clockwiseRing)));
    }

    @Test
    @Parameters(method = "nonClockwiseRingsWithFirstNotClockwiseTurnPoint")
    public void itShouldProvideMismatchDescriptionOnNonClockwiseRing(
            List<Point> points, Point point) {
        Description description = new StringDescription();

        clockwiseRing.describeMismatch(points, description);

        String prefix = "found a non clockwise point <" + point + "> among <[";
        assertThat(description, hasToString(startsWith(prefix)));
    }

    private Object[][] nonClockwiseRingsWithFirstNotClockwiseTurnPoint() {
        return new Object[][]{
                {asList(p(0, 1), p(0, 0), p(1, 1)), p(1, 1)},
                {asList(p(0, 0), p(0, 1), p(0, 2), p(0, 1)), p(0, 2)},
                {asList(p(0, 0), p(1, 0), p(1, 1), p(0, 1)), p(1, 1)}
        };
    }

    private Point p(int x, int y) {
        return new Point(x, y);
    }

    private List<Point> asList(Point... points) {
        return Collections.unmodifiableList(Arrays.asList(points));
    }

}
