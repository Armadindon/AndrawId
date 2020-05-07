package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RectangleShape implements DrawableShape {
    private float[] points;

    public RectangleShape(float... points){
        if(points.length != 4) throw new IllegalArgumentException("You need to pass 4 floating numbers");
        this.points = points;
    }

    @Override
    public void drawShape(ShapeProperties properties, Canvas canvas) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStrokeWidth(3f);

        System.out.println("Rect");
        float[] origin = properties.getOrigin();
        canvas.drawRect(points[0] + origin[0], points[1] + origin[1], points[2]+ origin[0], points[3] + origin[1], blackPaint);
    }
}
