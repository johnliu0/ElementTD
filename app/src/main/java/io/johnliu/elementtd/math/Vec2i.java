package io.johnliu.elementtd.math;

public class Vec2i {

    public int x;
    public int y;

    public Vec2i() {
        x = 0;
        y = 0;
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // returns a new vector representing
    // the sum of this and the given vector
    public Vec2i add(Vec2i addend) {
        return new Vec2i(x + addend.x, y + addend.y);
    }

    // subtracts the given vector from this vector
    // and returns the difference as a new vector
    public Vec2i subtract(Vec2i subtrahend) {
        return new Vec2i(x - subtrahend.x, y - subtrahend.y);
    }

    // multiples this vector by a scalar and returns the result
    public Vec2i mult(int scalar) {
        return new Vec2i(x * scalar, y * scalar);
    }

    // returns the dot product of the given vector and this
    public int dot(Vec2i vec) {
        return x * vec.x + y * vec.y;
    }

    // returns the length of this vector
    public int length() {
        return (int) Math.sqrt(x * x + y * y);
    }

    // returns the squared length of this vector
    // used for optimization purposes
    public int lengthSqr() {
        return x * x + y * y;
    }

}
