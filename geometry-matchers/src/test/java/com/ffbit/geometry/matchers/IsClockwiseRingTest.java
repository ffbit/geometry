package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class IsClockwiseRingTest {
    private Matcher<Point[]> clockwiseRing = IsClockwiseRing.clockwiseRing();

    @Test
    @Parameters(method = "clockwiseRings")
    public void itShouldMatchClockwiseRing(Point[] points) {
        assertThat(points, is(clockwiseRing));
    }

    private Object[][] clockwiseRings() {
        return new Point[][][]{
                {{p(0, 0), p(0, 1), p(1, 1)}},
                {{p(0, 0), p(0, 1), p(1, 1), p(1, 0)}}
        };
    }

    @Test
    @Parameters(method = "notEnoughPointsForRing,notClockwiseRings")
    public void itShouldNotMatchClockwiseRing(Point[] points) {
        assertThat(points, is(not(clockwiseRing)));
    }

    private Object[][] notEnoughPointsForRing() {
        return new Point[][][]{
                {{}},
                {{p(0, 1)}},
                {{p(0, 1), p(0, 0)}}
        };
    }

    private Object[][] notClockwiseRings() {
        return new Point[][][]{
                {{p(0, 1), p(0, 0), p(1, 1)}},
                {{p(0, 0), p(0, 1), p(0, 2), p(0, 1)}},
                {{p(0, 0), p(1, 0), p(1, 1), p(0, 1)}}
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
    public void itShouldProvideMismatchDescøriptionOnNullRing() {
        Description description = new StringDescription();

        clockwiseRing.describeMismatch(null, description);

        assertThat(description, hasToString("was null"));
    }

    @Test
    @Parameters(method = "notEnoughPointsForRing")
    public void itShouldProvideMismatchDescriptionWhenNotEnoughPoints(Point[] points) {
        Description description = new StringDescription();

        clockwiseRing.describeMismatch(points, description);

        assertThat(description,
                hasToString(startsWith("not enough points to make a ring ")));
    }

    @Test
    @Parameters(method = "nonClockwiseRingsWithFirstNotClockwiseTurnPoint")
    public void itShouldProvideMismatchDescriptionWhenNotClockwise(
            Point[] points, Point point) {
        Description description = new StringDescription();

        clockwiseRing.describeMismatch(points, description);

        String prefix = "found a non clockwise point <" + point + "> among [";
        assertThat(description, hasToString(startsWith(prefix)));
    }

    private Object[][] nonClockwiseRingsWithFirstNotClockwiseTurnPoint() {
        return new Object[][]{
                {new Point[]{p(0, 1), p(0, 0), p(1, 1)}, p(1, 1)},
                {new Point[]{p(0, 0), p(0, 1), p(0, 2), p(0, 1)}, p(0, 2)},
                {new Point[]{p(0, 0), p(1, 0), p(1, 1), p(0, 1)}, p(1, 1)}
        };
    }

    private Point p(int x, int y) {
        return new Point(x, y);
    }

}
