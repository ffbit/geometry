package com.ffbit.geometry;

import java.util.Objects;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Point_(geometry)">Point</a>.
 */
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point other = (Point) o;

        return x == other.x
                && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
