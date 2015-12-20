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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void itShouldMatchShiftedSequence(List<Point> actualSequence,
                                             List<Point> expectedSequence) {
        assertThat(actualSequence, is(shiftedSequence(expectedSequence)));
    }

    private Object[][] shiftedSequences() {
        return new Object[][]{
                {asList(), asList()},

                {asList(p(0, 1)), asList(p(0, 1))},

                {asList(p(0, 1), p(2, 3)), asList(p(0, 1), p(2, 3))},
                {asList(p(0, 1), p(2, 3)), asList(p(2, 3), p(0, 1))},
                {asList(p(2, 3), p(0, 1)), asList(p(0, 1), p(2, 3))},
                {asList(p(2, 3), p(0, 1)), asList(p(2, 3), p(0, 1))},

                {asList(p(0, 1), p(2, 3), p(4, 5)),
                        asList(p(0, 1), p(2, 3), p(4, 5))},
                {asList(p(0, 1), p(2, 3), p(4, 5)),
                        asList(p(2, 3), p(4, 5), p(0, 1))},
                {asList(p(0, 1), p(2, 3), p(4, 5)),
                        asList(p(4, 5), p(0, 1), p(2, 3))},
                {asList(p(4, 5), p(0, 1), p(2, 3)),
                        asList(p(2, 3), p(4, 5), p(0, 1))}
        };
    }

    @Test
    @Parameters(method = "mismatchingSequences")
    public void itShouldMismatchShiftedSequence(List<Point> actualSequence,
                                                List<Point> expectedSequence) {
        assertThat(actualSequence, is(not(shiftedSequence(expectedSequence))));
    }

    private Object[][] mismatchingSequences() {
        return new Object[][]{
                {null, asList()},

                {asList(p(0, 1)), asList(p(2, 3))},

                {asList(p(0, 1), p(2, 3)), asList(p(0, 1), p(0, 1))},
                {asList(p(0, 1), p(2, 3)), asList(p(2, 3), p(2, 3))},

                {asList(p(0, 1), p(2, 3), p(4, 5)),
                        asList(p(0, 1), p(2, 3), p(4, 5), p(6, 7))},
                {asList(p(0, 1), p(2, 3), p(4, 5), p(6, 7)),
                        asList(p(0, 1), p(2, 3), p(4, 5))}
        };
    }

    @Test
    public void itShouldDescribeItself() {
        Description description = new StringDescription();

        shiftedSequence(p(0, 1), p(2, 3)).describeTo(description);

        assertThat(description,
                hasToString("a shifted copy of the sequence <[(0 1), (2 3)]>"));
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

        shiftedSequence((List<Point>) null);
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
        assertThat(shiftedSequence().matches(asList(p(0, 1))), is(false));
    }

    @Test
    public void itShouldDescribeMismatchOnExpectedEmptySequence() {
        Description description = new StringDescription();

        shiftedSequence().describeMismatch(asList(p(0, 1)), description);

        assertThat(description,
                hasToString("got a nonempty sequence <[(0 1)]>"));
    }

    @Test
    @Parameters(method = "missingNextExpectedPointSequences")
    public void itShouldMismatchOnMissingPoint(List<Point> expected,
                                               List<Point> actual,
                                               Point missing) {
        assertThat(actual, is(not(shiftedSequence(expected))));
    }

    @Test
    @Parameters(method = "missingNextExpectedPointSequences")
    public void itShouldDescribeMismatchOnMissingPoint(List<Point> expected,
                                                       List<Point> actual,
                                                       Point missing) {
        Description description = new StringDescription();

        shiftedSequence(expected).describeMismatch(actual, description);

        String prefix = "missing next expected point <" + missing + ">";
        assertThat(description, hasToString(startsWith(prefix)));
    }

    private Object[][] missingNextExpectedPointSequences() {
        return new Object[][]{
                {asList(p(0, 1)),
                        asList(p(2, 3)),
                        p(0, 1)},
                {asList(p(0, 1), p(2, 3)),
                        asList(p(0, 1)),
                        p(2, 3)},
                {asList(p(3, 4), p(0, 1), p(2, 3)),
                        asList(p(2, 3)),
                        p(3, 4)},
                {asList(p(3, 4), p(0, 1), p(2, 3)),
                        asList(p(2, 3), p(3, 4)),
                        p(0, 1)},
                {asList(p(0, 1), p(2, 3), p(4, 5)),
                        asList(p(4, 5), p(0, 1)),
                        p(2, 3)}
        };
    }

    @Test
    @Parameters(method = "unexpectedPointSequences")
    public void itShouldMismatchOnUnexpectedPoint(List<Point> expected,
                                                  List<Point> actual,
                                                  Point unexpected) {
        assertThat(actual, is(not(shiftedSequence(expected))));
    }

    @Test
    @Parameters(method = "unexpectedPointSequences")
    public void itShouldDescribeMismatchOnFirstUnexpectedPoint(List<Point> expected,
                                                               List<Point> actual,
                                                               Point unexpected) {
        Description description = new StringDescription();

        shiftedSequence(expected).describeMismatch(actual, description);

        String prefix = "got an unexpected point <" + unexpected + ">";
        assertThat(description, hasToString(startsWith(prefix)));
    }

    private Object[][] unexpectedPointSequences() {
        return new Object[][]{
                {asList(p(0, 1), p(2, 3)),
                        asList(p(0, 1), p(2, 3), p(4, 5)),
                        p(4, 5)},
                {asList(p(0, 1), p(2, 3)),
                        asList(p(4, 5), p(0, 1), p(2, 3)),
                        p(4, 5)}
        };
    }

    private Point p(int x, int y) {
        return new Point(x, y);
    }

    private List<Point> asList(Point... points) {
        return Collections.unmodifiableList(Arrays.asList(points));
    }

}
