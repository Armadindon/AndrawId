package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CursiveShape extends AbstractDrawableShape {


    public CursiveShape(float... points){
        super(points);
    }

    @Override
    public void drawShape(ShapeProperties properties, Canvas canvas) {
        Paint blackPaint = new Paint();
        blackPaint.setColor(properties.getColor());
        blackPaint.setStrokeWidth(3f);

        float[] origin = properties.getOrigin();
        float[] tmpPoints = Arrays.copyOf(getPoints(),getPoints().length);
        for (int i= 0; i<tmpPoints.length;i++) tmpPoints[i]=origin[i%2]+tmpPoints[i];
        canvas.drawLines(tmpPoints, blackPaint);
    }

    @Override
    public ShapeKind getShapeKind() {
        return ShapeKind.CURSIVE;
    }

    @Override
    public float[] getCenter(float[] origin) {
        //on calcule les 4 points d'un carré "virtuel"
        float minX = Integer.MAX_VALUE;
        float maxX = Integer.MIN_VALUE;
        float minY = Integer.MAX_VALUE;
        float maxY = Integer.MIN_VALUE;

        for (int i = 0;i < points.length;i+=2){
            float[] point = new float[]{points[i],points[i+1]};
            if(point[0] > maxX) maxX = point[0];
            if(point[0] < minX) minX = point[0];
            if(point[1] > maxY) maxY = point[1];
            if(point[1] < minY) minY = point[1];
        }

        return new float[]{((minX+origin[0])+(maxX+origin[0]))/2,((maxY+origin[1])+(minY+origin[1]))/2};
    }

}
