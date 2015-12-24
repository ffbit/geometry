package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import org.hamcrest.Matcher;

import java.util.List;

public abstract class GeometryMatchers {

    public static Matcher<List<Point>> clockwiseRing() {
        return IsClockwiseRing.clockwiseRing();
    }

    public static Matcher<List<Point>> shiftedSequence(
            List<Point> expectedSequence) {
        return IsShiftedSequence.shiftedSequence(expectedSequence);
    }

    public static Matcher<List<Point>> shiftedSequence(
            Point... expectedSequence) {
        return IsShiftedSequence.shiftedSequence(expectedSequence);
    }

}
