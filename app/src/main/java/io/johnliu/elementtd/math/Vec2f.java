package io.johnliu.elementtd.math;

public class Vec2f {

    public float x;
    public float y;

    public Vec2f() {
        x = 0.0f;
        y = 0.0f;
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(Vec2f vec) {
        x = vec.x;
        y = vec.y;
    }

    // returns a new vector representing
    // the sum of this and the given vector
    public Vec2f add(Vec2f addend) {
        return new Vec2f(x + addend.x, y + addend.y);
    }

    // subtracts the given vector from this vector
    // and returns the difference as a new vector
    public Vec2f subtract(Vec2f subtrahend) {
        return new Vec2f(x - subtrahend.x, y - subtrahend.y);
    }

    // multiples this vector by a scalar and returns the result
    public Vec2f mult(float scalar) {
        return new Vec2f(x * scalar, y * scalar);
    }

    // returns the dot product of the given vector and this
    public float dot(Vec2f vec) {
        return x * vec.x + y * vec.y;
    }

    // returns the length of this vector
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // returns the squared length of this vector
    // used for optimization purposes
    public float lengthSqr() {
        return x * x + y * y;
    }

}
