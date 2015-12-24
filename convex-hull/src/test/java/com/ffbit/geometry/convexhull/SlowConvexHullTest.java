package com.ffbit.geometry.convexhull;

public class SlowConvexHullTest extends ConvexHullAbstractTest {

    @Override
    protected ConvexHull createConvexHullAlgorithm() {
        return new SlowConvexHull();
    }

}
