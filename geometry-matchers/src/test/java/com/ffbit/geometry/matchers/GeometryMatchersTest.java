package com.ffbit.geometry.matchers;

import org.junit.Test;

import java.util.Collections;

import static com.ffbit.geometry.matchers.GeometryMatchers.clockwiseRing;
import static com.ffbit.geometry.matchers.GeometryMatchers.shiftedSequence;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeometryMatchersTest {

    @Test
    public void itShouldCreateClockwiseRingMatcher() {
        assertThat(clockwiseRing(), is(instanceOf(IsClockwiseRing.class)));
    }

    @Test
    public void itShouldCreateShiftedSequenceMatcherForList() {
        assertThat(shiftedSequence(Collections.emptyList()),
                is(instanceOf(IsShiftedSequence.class)));
    }

    @Test
    public void itShouldCreateShiftedSequenceMatcherForVarargs() {
        assertThat(shiftedSequence(), is(instanceOf(IsShiftedSequence.class)));
    }

}
