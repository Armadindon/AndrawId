package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;

public interface DrawableShape {
    public void drawShape(ShapeProperties properties, Canvas canvas);
    public float[] getCenter();
}
