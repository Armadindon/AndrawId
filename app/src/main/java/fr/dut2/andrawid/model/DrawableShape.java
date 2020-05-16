package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;

public interface DrawableShape {
    void drawShape(ShapeProperties properties, Canvas canvas);

    float[] getCenter(float[] origin);

    float[] getPoints();

    ShapeKind getShapeKind();
}
