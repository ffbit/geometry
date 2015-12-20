package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Checks if a given actual sequence is a shifted copy of the expected one.
 */
public class IsShiftedSequence extends TypeSafeDiagnosingMatcher<List<Point>> {
    private final List<Point> expectedSequence;

    public IsShiftedSequence(List<Point> expectedSequence) {
        if (expectedSequence == null) {
            String message = "A non-null sequence is required for the shiftedSequence() matcher";

            throw new IllegalArgumentException(message);
        }

        this.expectedSequence = new ArrayList<>(expectedSequence);
    }

    @Override
    protected boolean matchesSafely(List<Point> sequence,
                                    Description mismatchDescription) {
        if (expectedSequence.isEmpty() && sequence.isEmpty()) {
            return true;
        }

        if (expectedSequence.isEmpty()) {
            mismatchDescription.appendText("got a nonempty sequence ")
                    .appendValue(sequence);

            return false;
        }

        int actualStartIndex = 0;
        while (actualStartIndex < sequence.size()
                && !equals(expectedSequence.get(0), sequence.get(actualStartIndex))) {
            actualStartIndex++;
        }

        if (actualStartIndex == sequence.size()) {
            mismatchDescription.appendText("missing next expected point ")
                    .appendValue(expectedSequence.get(0));

            return false;
        }

        int actualIndex = actualStartIndex;
        for (int i = 0; i < expectedSequence.size(); i++) {
            actualIndex = (actualStartIndex + i) % sequence.size();

            if (!equals(expectedSequence.get(i), sequence.get(actualIndex))) {
                mismatchDescription.appendText("missing next expected point ")
                        .appendValue(expectedSequence.get(i));

                return false;
            }
        }

        if (expectedSequence.size() < sequence.size()) {
            Point unexpected = sequence.get((actualIndex + 1) % sequence.size());
            mismatchDescription.appendText("got an unexpected point ")
                    .appendValue(unexpected);

            return false;
        }

        return true;
    }

    private boolean equals(Point a, Point b) {
        return Objects.equals(a, b);
    }

    @Override
    public void describeTo(Description description) {
        if (expectedSequence.isEmpty()) {
            description.appendText("an empty sequence");
        } else {
            description.appendText("a shifted copy of the sequence ")
                    .appendValue(expectedSequence);
        }
    }

    @Factory
    public static Matcher<List<Point>> shiftedSequence(
            List<Point> expectedSequence) {
        return new IsShiftedSequence(expectedSequence);
    }

    @Factory
    public static Matcher<List<Point>> shiftedSequence(
            Point... expectedSequence) {
        return new IsShiftedSequence(Arrays.asList(expectedSequence));
    }

}
