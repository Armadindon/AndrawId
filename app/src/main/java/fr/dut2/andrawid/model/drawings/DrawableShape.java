package fr.dut2.andrawid.model.drawings;

import android.graphics.Canvas;

import fr.dut2.andrawid.model.shape.ShapeKind;
import fr.dut2.andrawid.model.shape.ShapeProperties;

public interface DrawableShape {
    void drawShape(ShapeProperties properties, Canvas canvas);

    float[] getCenter(float[] origin);

    float[] getPoints();

    ShapeKind getShapeKind();
}
