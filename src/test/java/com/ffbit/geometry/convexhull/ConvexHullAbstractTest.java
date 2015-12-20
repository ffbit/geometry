package com.ffbit.geometry.convexhull;

import com.ffbit.geometry.Point;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ffbit.geometry.matchers.GeometryMatchers.clockwiseRing;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public abstract class ConvexHullAbstractTest {
    private ConvexHull convexHullAlgorithm = createConvexHullAlgorithm();

    protected abstract ConvexHull createConvexHullAlgorithm();

    @Test
    @Parameters(method = "points")
    public void itShouldFindConvexHull(List<Point> points,
                                       List<Point> convexHullPoints) {
        assertThat(convexHullAlgorithm.convexHull(points), is(clockwiseRing()));
        assertThat(convexHullAlgorithm.convexHull(points), is(convexHullPoints));
    }

    private Object[][] points() {
        return new Object[][]{
                {asList(p(1, 4), p(4, 10), p(3, 7), p(6, 9), p(7, 3), p(10, 8),
                        p(11, 4), p(8, 6), p(5, 2), p(4, 5)),
                        asList(p(1, 4), p(4, 10), p(10, 8), p(11, 4), p(5, 2))},
                {asList(p(2, 8), p(3, 8), p(4, 8), p(6, 8), p(5, 8), p(3, 7),
                        p(4, 7), p(4, 6), p(5, 7)),
                        asList(p(2, 8), p(6, 8), p(4, 6))},
                {asList(p(8, 2), p(1, 4), p(3, 6)),
                        asList(p(1, 4), p(3, 6), p(8, 2))},
                {asList(p(8, 2), p(1, 4), p(3, 0)),
                        asList(p(1, 4), p(8, 2), p(3, 0))}
        };
    }

    private Point p(int x, int y) {
        return new Point(x, y);
    }

    private List<Point> asList(Point... points) {
        return Collections.unmodifiableList(Arrays.asList(points));
    }

}
