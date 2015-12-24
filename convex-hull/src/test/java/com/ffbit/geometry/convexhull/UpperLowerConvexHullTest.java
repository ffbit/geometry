package com.ffbit.geometry.convexhull;

public class UpperLowerConvexHullTest extends ConvexHullAbstractTest {

    @Override
    protected ConvexHull createConvexHullAlgorithm() {
        return new UpperLowerConvexHull();
    }

}
