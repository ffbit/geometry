package com.ffbit.geometry.convexhull;

public class IncrementalConvexHullTest extends ConvexHullAbstractTest {

    @Override
    protected ConvexHull createConvexHullAlgorithm() {
        return new IncrementalConvexHull();
    }

}
