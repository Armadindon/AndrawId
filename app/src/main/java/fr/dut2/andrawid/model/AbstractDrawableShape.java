package fr.dut2.andrawid.model;

abstract class AbstractDrawableShape implements DrawableShape {
    float[] points;

    AbstractDrawableShape(float... points) {
        if (points.length < 4)
            throw new IllegalArgumentException("You need to pass at least 4 floating numbers");

        this.points = points;

    }

    @Override
    public float[] getPoints() {
        return points;
    }

}
