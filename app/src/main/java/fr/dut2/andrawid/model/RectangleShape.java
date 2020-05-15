package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RectangleShape extends AbstractDrawableShape {

    public RectangleShape(float... points) {
        super(points);
    }

    @Override
    public void drawShape(ShapeProperties properties, Canvas canvas) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(properties.getColor());
        blackPaint.setStrokeWidth(3f);

        float[] origin = properties.getOrigin();
        canvas.drawRect(points[0] + origin[0], points[1] + origin[1], points[2] + origin[0], points[3] + origin[1], blackPaint);
    }

    @Override
    public ShapeKind getShapeKind() {
        return ShapeKind.RECTANGLE;
    }

    @Override
    public float[] getCenter() {
        return new float[]{(points[0] + points[2]) / 2, (points[1] + points[3]) / 2};
    }
}
