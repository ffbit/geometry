package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import org.hamcrest.Matcher;

import java.util.List;

public class GeometryMatchers {

    public static Matcher<List<Point>> clockwiseRing() {
        return IsClockwiseRing.clockwiseRing();
    }

}
