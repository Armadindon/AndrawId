package fr.dut2.andrawid.model;

import android.graphics.Color;

public class ShapeProperties {
    private float[] origin;
    private int color;

    public ShapeProperties(int color, float... origin) {
        if (origin.length != 2)
            throw new IllegalArgumentException("You need to pass 2 floating numbers");
        this.origin = origin;
        this.color = color;
    }

    public ShapeProperties(float... origin) {
        this(Color.BLACK, origin);
    }

    public float[] getOrigin() {
        return origin;
    }

    public int getColor() {
        return color;
    }
}
