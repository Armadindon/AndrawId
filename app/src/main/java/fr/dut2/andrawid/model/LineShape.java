package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineShape implements DrawableShape {
    private float[] points;

    public LineShape(float[] points){
        if(points.length != 4) throw new IllegalArgumentException("You need to pass 4 floating numbers");
        this.points = points;
    }

    @Override
    public void drawShape(ShapeProperties properties, Canvas canvas) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        float[] origin = properties.getOrigin();
        canvas.drawLine(points[0] + origin[0], points[1], points[2] + origin[1], points[3], blackPaint);
    }
}
