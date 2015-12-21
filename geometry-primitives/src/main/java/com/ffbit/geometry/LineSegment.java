package com.ffbit.geometry;

import java.util.Objects;

/**
 * <p>
 * A directed line segment from point a to point b.
 * </p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Line_segment">Line segment</a>
 * @see <a href="https://en.wikipedia.org/wiki/Line_(geometry)">Line</a>.
 */
public class LineSegment {
    private final Point a;
    private final Point b;

    public LineSegment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public int length() {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();

        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "(" + a.getX() + " " + a.getY()
                + ", " + b.getX() + " " + b.getY() + ")";
    }

    public boolean pointIsToTheRightSide(Point point) {
        return twiceTheSignedArea(point) < 0;
    }

    public boolean pointIsToTheLeftSide(Point point) {
        return twiceTheSignedArea(point) > 0;
    }

    private int twiceTheSignedArea(Point point) {
        return (b.getX() - a.getX()) * (point.getY() - a.getY())
                - (b.getY() - a.getY()) * (point.getX() - a.getX());
    }

    public boolean pointLiesOnSegment(Point point) {
        return twiceTheSignedArea(point) == 0
                && within(a.getX(), point.getX(), b.getX())
                && within(a.getY(), point.getY(), b.getY());
    }

    private boolean within(int firstBoundary, int value, int secondBoundary) {
        return Math.min(firstBoundary, secondBoundary) <= value
                && Math.max(firstBoundary, secondBoundary) >= value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        LineSegment other = (LineSegment) o;
        return Objects.equals(a, other.a)
                && Objects.equals(b, other.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

}
