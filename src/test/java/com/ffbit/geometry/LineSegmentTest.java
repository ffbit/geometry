package com.ffbit.geometry;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class LineSegmentTest {
    private Point anyPoint = new Point(-1, -1);

    @Test
    public void itShouldHavePointA() {
        Point a = new Point(1, 2);

        LineSegment lineSegment = new LineSegment(a, anyPoint);

        assertThat(lineSegment.getA(), is(a));
    }

    @Test
    public void itShouldHavePointB() {
        Point b = new Point(3, 4);

        LineSegment lineSegment = new LineSegment(anyPoint, b);

        assertThat(lineSegment.getB(), is(b));
    }

    @Test
    @Parameters(method = "lineLengths")
    public void itShouldHaveLength(LineSegment lineSegment, int length) {
        assertThat(lineSegment.length(), is(length));
    }

    private Object[][] lineLengths() {
        return new Object[][]{
                {new LineSegment(new Point(0, 0), new Point(3, 4)), 5},
                {new LineSegment(new Point(53, 40), new Point(59, 48)), 10}
        };
    }

    @Test
    public void itShouldHaveToString() {
        Point a = new Point(0, 1);
        Point b = new Point(2, 3);

        LineSegment lineSegment = new LineSegment(a, b);

        assertThat(lineSegment, hasToString("(0 1, 2 3)"));
    }

    @Test
    @Parameters(method = "rightSidePoints")
    public void itShouldFindIfThirdPointIsToTheRightSide(LineSegment segment,
                                                         Point point) {
        assertThat(segment.pointIsToTheRightSide(point), is(true));
    }

    private Object[][] rightSidePoints() {
        return new Object[][]{
                {new LineSegment(new Point(1, 2), new Point(5, 6)),
                        new Point(4, 3)},
                {new LineSegment(new Point(0, 0), new Point(2, 0)),
                        new Point(-1, -1)},
                {new LineSegment(new Point(2, 2), new Point(2, -1)),
                        new Point(1, 2)}
        };
    }

    @Test
    @Parameters(method = "leftSidePoints,collinearPoints")
    public void itShouldFindIfThirdPointIsNotToTheRightSide(LineSegment segment,
                                                            Point point) {
        assertThat(segment.pointIsToTheRightSide(point), is(false));
    }

    private Object[][] leftSidePoints() {
        return new Object[][]{
                {new LineSegment(new Point(1, 2), new Point(5, 6)),
                        new Point(3, 5)},
                {new LineSegment(new Point(0, 0), new Point(2, 0)),
                        new Point(1, 1)},
                {new LineSegment(new Point(2, 2), new Point(2, -1)),
                        new Point(3, 1)}
        };
    }

    private Object[][] collinearPoints() {
        return new Object[][]{
                {new LineSegment(new Point(1, 2), new Point(5, 6)),
                        new Point(3, 4)},
                {new LineSegment(new Point(0, 0), new Point(2, 0)),
                        new Point(-1, 0)},
                {new LineSegment(new Point(2, 2), new Point(2, -1)),
                        new Point(2, 3)}
        };
    }

    @Test
    @Parameters(method = "equalSegmentsOfSameDirection")
    public void itShouldBeEqualToAnotherSegmentOfSameDirection(LineSegment ab,
                                                               LineSegment cd) {
        assertThat(ab, is(cd));
    }

    @Test
    @Parameters(method = "equalSegmentsOfSameDirection")
    public void twoEqualSegmentsShouldHaveTheSameHashCode(LineSegment ab,
                                                          LineSegment cd) {
        assertThat(ab.hashCode(), is(cd.hashCode()));
    }

    private Object[][] equalSegmentsOfSameDirection() {
        LineSegment sameSegment =
                new LineSegment(new Point(0, 0), new Point(1, 1));

        return new Object[][]{
                {sameSegment, sameSegment},
                {new LineSegment(new Point(0, 1), new Point(2, 3)),
                        new LineSegment(new Point(0, 1), new Point(2, 3))}
        };
    }

    @Test
    @Parameters(method = "unequalSegments")
    public void itShouldBeEqualToAnotherSegment(LineSegment ab,
                                                LineSegment cd) {
        assertThat(ab, is(not(cd)));
    }

    private Object[][] unequalSegments() {
        return new Object[][]{
                {new LineSegment(new Point(0, 1), new Point(2, 3)), null},
                {new LineSegment(new Point(0, 1), new Point(2, 3)),
                        new LineSegment(new Point(4, 5), new Point(6, 7))},

                {new LineSegment(new Point(0, 1), new Point(2, 3)),
                        new LineSegment(new Point(2, 3), new Point(0, 1))}
        };
    }

    @Test
    @Parameters(method = "pointsOnSegment")
    public void itShouldLieOnSegment(LineSegment segment, Point point) {
        assertThat(segment.pointLiesOnSegment(point), is(true));
    }

    private Object[][] pointsOnSegment() {
        return new Object[][]{
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(2, 3)},
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(1, 2)},
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(3, 4)},

                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(2, 1)},
                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(1, 1)},
                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(3, 1)},

                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(1, 1)},
                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(1, 2)},
                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(1, 3)},
                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(1, 4)}
        };
    }

    @Test
    @Parameters(method = "pointsNotOnSegment")
    public void itShouldNotLieOnSegment(LineSegment segment, Point point) {
        assertThat(segment.pointLiesOnSegment(point), is(false));
    }

    private Object[][] pointsNotOnSegment() {
        return new Object[][]{
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(0, 1)},
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(4, 5)},
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(1, 3)},
                {new LineSegment(new Point(1, 2), new Point(3, 4)),
                        new Point(3, 3)},

                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(0, 1)},
                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(4, 1)},
                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(2, 0)},
                {new LineSegment(new Point(1, 1), new Point(3, 1)),
                        new Point(2, 4)},

                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(1, 5)},
                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(1, -1)},
                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(0, 3)},
                {new LineSegment(new Point(1, 0), new Point(1, 4)),
                        new Point(2, 4)}
        };
    }

}
