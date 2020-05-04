package fr.dut2.andrawid.model;

import android.graphics.Canvas;

public class LineShape implements DrawableShape {
    private float[] points;

    public LineShape(float[] points){
        if(points.length != 4) throw new IllegalArgumentException("You need to pass 4 floating numbers");
        this.points = points;
    }

    @Override
    public void drawShape(ShapeProperties properties, Canvas canvas) {

    }
}
