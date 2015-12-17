package com.ffbit.geometry.matchers;

import com.ffbit.geometry.Point;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static com.ffbit.geometry.matchers.IsShiftedSequence.shiftedSequence;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class IsShiftedSequenceTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    @Parameters(method = "shiftedSequences")
    public void itShouldMatchShiftedSequence(Point[] actualSequence,
                                             Point[] expectedSequence) {
        assertThat(actualSequence, is(shiftedSequence(expectedSequence)));
    }

    private Object[][] shiftedSequences() {
        return new Object[][]{
                {new Point[]{}, new Point[]{}},

                {new Point[]{p(0, 1)}, new Point[]{p(0, 1)}},

                {new Point[]{p(0, 1), p(2, 3)}, new Point[]{p(0, 1), p(2, 3)}},
                {new Point[]{p(0, 1), p(2, 3)}, new Point[]{p(2, 3), p(0, 1)}},
                {new Point[]{p(2, 3), p(0, 1)}, new Point[]{p(0, 1), p(2, 3)}},
                {new Point[]{p(2, 3), p(0, 1)}, new Point[]{p(2, 3), p(0, 1)}},

                {new Point[]{p(0, 1), p(2, 3), p(4, 5)},
                        new Point[]{p(0, 1), p(2, 3), p(4, 5)}},
                {new Point[]{p(0, 1), p(2, 3), p(4, 5)},
                        new Point[]{p(2, 3), p(4, 5), p(0, 1)}},
                {new Point[]{p(0, 1), p(2, 3), p(4, 5)},
                        new Point[]{p(4, 5), p(0, 1), p(2, 3)}},
                {new Point[]{p(4, 5), p(0, 1), p(2, 3)},
                        new Point[]{p(2, 3), p(4, 5), p(0, 1)}}
        };
    }

    @Test
    @Parameters(method = "mismatchingSequences")
    public void itShouldMismatchShiftedSequence(Point[] actualSequence,
                                                Point[] expectedSequence) {
        assertThat(actualSequence, is(not(shiftedSequence(expectedSequence))));
    }

    private Object[][] mismatchingSequences() {
        return new Object[][]{
                {null, new Point[]{}},

                {new Point[]{p(0, 1)}, new Point[]{p(2, 3)}},

                {new Point[]{p(0, 1), p(2, 3)}, new Point[]{p(0, 1), p(0, 1)}},
                {new Point[]{p(0, 1), p(2, 3)}, new Point[]{p(2, 3), p(2, 3)}},

                {new Point[]{p(0, 1), p(2, 3), p(4, 5)},
                        new Point[]{p(0, 1), p(2, 3), p(4, 5), p(6, 7)}},
                {new Point[]{p(0, 1), p(2, 3), p(4, 5), p(6, 7)},
                        new Point[]{p(0, 1), p(2, 3), p(4, 5)}}
        };
    }

    @Test
    public void itShouldDescribeItself() {
        Description description = new StringDescription();

        shiftedSequence(p(0, 1), p(2, 3)).describeTo(description);

        assertThat(description,
                hasToString("a shifted copy of the sequence [<(0 1)>, <(2 3)>]"));
    }

    @Test
    public void itShouldDescribeItselfForEmptyExpectedSequence() {
        Description description = new StringDescription();

        shiftedSequence().describeTo(description);

        assertThat(description, hasToString("an empty sequence"));
    }

    @Test
    public void nullIsNotAllowedAsExpectedShiftedSequence() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(
                "A non-null sequence is required for the shiftedSequence() matcher");

        shiftedSequence((Point[]) null);
    }

    @Test
    public void itShouldMismatchOnNullActualSequence() {
        assertThat(null, is(not(shiftedSequence(p(0, 1), p(2, 3)))));
    }

    @Test
    public void itShouldDescribeMismatchOnNullActualSequence() {
        Description description = new StringDescription();

        shiftedSequence(p(0, 1), p(2, 3)).describeMismatch(null, description);

        assertThat(description, hasToString("was null"));
    }

    @Test
    public void itShouldMismatchOnExpectedEmptySequence() {
        assertThat(shiftedSequence().matches(new Point[]{p(0, 1)}), is(false));
    }

    @Test
    public void itShouldDescribeMismatchOnExpectedEmptySequence() {
        Description description = new StringDescription();

        shiftedSequence().describeMismatch(new Point[]{p(0, 1)}, description);

        assertThat(description,
                hasToString("got a nonempty sequence [<(0 1)>]"));
    }

    @Test
    @Parameters(method = "missingNextExpectedPointSequences")
    public void itShouldMismatchOnMissingPoint(Point[] expected,
                                               Point[] actual,
                                               Point missing) {
        assertThat(actual, is(not(shiftedSequence(expected))));
    }

    @Test
    @Parameters(method = "missingNextExpectedPointSequences")
    public void itShouldDescribeMismatchOnMissingPoint(Point[] expected,
                                                       Point[] actual,
                                                       Point missing) {
        Description description = new StringDescription();

        shiftedSequence(expected).describeMismatch(actual, description);

        String prefix = "missing next expected point <" + missing + ">";
        assertThat(description, hasToString(startsWith(prefix)));
    }

    private Object[][] missingNextExpectedPointSequences() {
        return new Object[][]{
                {new Point[]{p(0, 1)},
                        new Point[]{p(2, 3)},
                        p(0, 1)},
                {new Point[]{p(0, 1), p(2, 3)},
                        new Point[]{p(0, 1)},
                        p(2, 3)},
                {new Point[]{p(3, 4), p(0, 1), p(2, 3)},
                        new Point[]{p(2, 3)},
                        p(3, 4)},
                {new Point[]{p(3, 4), p(0, 1), p(2, 3)},
                        new Point[]{p(2, 3), p(3, 4)},
                        p(0, 1)},
                {new Point[]{p(0, 1), p(2, 3), p(4, 5)},
                        new Point[]{p(4, 5), p(0, 1)},
                        p(2, 3)}
        };
    }

    @Test
    @Parameters(method = "unexpectedPointSequences")
    public void itShouldMismatchOnUnexpectedPoint(Point[] expected,
                                                  Point[] actual,
                                                  Point unexpected) {
        assertThat(actual, is(not(shiftedSequence(expected))));
    }

    @Test
    @Parameters(method = "unexpectedPointSequences")
    public void itShouldDescribeMismatchOnFirstUnexpectedPoint(Point[] expected,
                                                               Point[] actual,
                                                               Point unexpected) {
        Description description = new StringDescription();

        shiftedSequence(expected).describeMismatch(actual, description);

        String prefix = "got an unexpected point <" + unexpected + ">";
        assertThat(description, hasToString(startsWith(prefix)));
    }

    private Object[][] unexpectedPointSequences() {
        return new Object[][]{
                {new Point[]{p(0, 1), p(2, 3)},
                        new Point[]{p(0, 1), p(2, 3), p(4, 5)},
                        p(4, 5)},
                {new Point[]{p(0, 1), p(2, 3)},
                        new Point[]{p(4, 5), p(0, 1), p(2, 3)},
                        p(4, 5)}
        };
    }

    private Point p(int x, int y) {
        return new Point(x, y);
    }

}
