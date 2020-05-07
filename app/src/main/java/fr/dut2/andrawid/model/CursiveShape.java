package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CursiveShape implements DrawableShape {
    private float[] points;

    public CursiveShape(float... points){
        this.points = points;
    }

    @Override
    public void drawShape(ShapeProperties properties, Canvas canvas) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3f);

        float[] origin = properties.getOrigin();
        canvas.drawLines(points, blackPaint);
    }
}
