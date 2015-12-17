package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Objects;

/**
 * Checks if a given actual sequence is a shifted copy of the expected one.
 */
public class IsShiftedSequence extends TypeSafeDiagnosingMatcher<Point[]> {
    private final Point[] expectedSequence;

    public IsShiftedSequence(Point... expectedSequence) {
        if (expectedSequence == null) {
            String message = "A non-null sequence is required for the shiftedSequence() matcher";

            throw new IllegalArgumentException(message);
        }

        this.expectedSequence = expectedSequence.clone();
    }

    @Override
    protected boolean matchesSafely(Point[] sequence,
                                    Description mismatchDescription) {
        if (isEmpty(expectedSequence) && isEmpty(sequence)) {
            return true;
        }

        if (isEmpty(expectedSequence)) {
            mismatchDescription.appendText("got a nonempty sequence ")
                    .appendValue(sequence);

            return false;
        }

        int actualStartIndex = 0;
        while (actualStartIndex < sequence.length
                && !equals(expectedSequence[0], sequence[actualStartIndex])) {
            actualStartIndex++;
        }

        if (actualStartIndex == sequence.length) {
            mismatchDescription.appendText("missing next expected point ")
                    .appendValue(expectedSequence[0]);

            return false;
        }

        int actualIndex = actualStartIndex;
        for (int i = 0; i < expectedSequence.length; i++) {
            actualIndex = (actualStartIndex + i) % sequence.length;

            if (!equals(expectedSequence[i], sequence[actualIndex])) {
                mismatchDescription.appendText("missing next expected point ")
                        .appendValue(expectedSequence[i]);

                return false;
            }
        }

        if (expectedSequence.length < sequence.length) {
            Point unexpected = sequence[(actualIndex + 1) % sequence.length];
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
        if (isEmpty(expectedSequence)) {
            description.appendText("an empty sequence");
        } else {
            description.appendText("a shifted copy of the sequence ")
                    .appendValue(expectedSequence);
        }
    }

    private static boolean isEmpty(Point[] sequence) {
        return sequence.length == 0;
    }

    @Factory
    public static Matcher<Point[]> shiftedSequence(Point... expectedSequence) {
        return new IsShiftedSequence(expectedSequence);
    }

}
