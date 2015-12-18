package com.ffbit.geometry.convexhull;

public class GiftWrappingConvexHullTest extends ConvexHullAbstractTest {

    @Override
    protected ConvexHull createConvexHullAlgorithm() {
        return new GiftWrappingConvexHull();
    }

}
