package com.ffbit.geometry;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PointTest {
    private int anyInt = -1;

    @Test
    public void itShouldHaveXCoordinate() {
        int x = 1;

        Point point = new Point(x, anyInt);

        assertThat(point.getX(), is(x));
    }

    @Test
    public void itShouldHaveYCoordinate() {
        int y = 2;

        Point point = new Point(anyInt, y);

        assertThat(point.getY(), is(y));
    }

    @Test
    public void itShouldHaveToString() {
        int x = 2;
        int y = 3;

        Point point = new Point(x, y);

        assertThat(point, hasToString("(" + x + " " + y + ")"));
    }

    @Test
    @Parameters(method = "equalPoints")
    public void itShouldBeEqualToAnotherPoint(Point a, Point b) {
        assertThat(a, is(b));
    }

    @Test
    @Parameters(method = "equalPoints")
    public void twoEqualPointsShouldHaveTheSameHashCode(Point a, Point b) {
        assertThat(a.hashCode(), is(b.hashCode()));
    }

    private Object[][] equalPoints() {
        Point samePoint = new Point(0, 0);

        return new Object[][]{
                {samePoint, samePoint},
                {new Point(4, 5), new Point(4, 5)}
        };
    }

    @Test
    @Parameters(method = "unequalPoints")
    public void itShouldBeUnequalToAnotherPoint(Point a, Point b) {
        assertThat(a, is(not(b)));
    }

    private Object[][] unequalPoints() {
        return new Object[][]{
                {new Point(anyInt, anyInt), null},
                {new Point(1, anyInt), new Point(2, anyInt)},
                {new Point(anyInt, 1), new Point(anyInt, 2)},
                {new Point(1, 2), new Point(3, 4)}
        };
    }

    @Test
    @Parameters(method = "comparablePoints")
    public void itShouldBeComparable(Point[] notSorted, Point[] sorted) {
        Arrays.sort(notSorted);

        assertThat(notSorted, is(sorted));
    }

    private Object[][] comparablePoints() {
        return new Object[][]{
                {new Point[]{new Point(anyInt, anyInt)},
                        new Point[]{new Point(anyInt, anyInt)}},
                {new Point[]{new Point(anyInt, anyInt), new Point(anyInt, anyInt)},
                        new Point[]{new Point(anyInt, anyInt), new Point(anyInt, anyInt)}},
                {new Point[]{new Point(2, anyInt), new Point(1, anyInt)},
                        new Point[]{new Point(1, anyInt), new Point(2, anyInt)}},
                {new Point[]{new Point(anyInt, 1), new Point(anyInt, 0)},
                        new Point[]{new Point(anyInt, 0), new Point(anyInt, 1)}}
        };
    }

}
