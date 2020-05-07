package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShapeContainer {

    private static final int CENTER_PRECISION = 50; //pixel autour du centre pour la séléction et la suppresion

    private HashMap<DrawableShape,ShapeProperties> container;
    private ArrayList<ShapeContainerChangeListener> changeListeners = new ArrayList<>();

    private DrawableShape selectShape =null;

    public ShapeContainer(){
        container = new HashMap<>();
    }

    public void draw(Canvas canvas){
        for(DrawableShape dS : container.keySet()){
            dS.drawShape(container.get(dS),canvas);
        }
    }

    public boolean add(DrawableShape shape, ShapeProperties properties){
        return container.put(shape,properties) == null;
    }

    public void addChangeListener(ShapeContainerChangeListener listener){
        changeListeners.add(listener);
    }

    public void removeChangeListener(ShapeContainerChangeListener listener){
        changeListeners.remove(listener);
    }

    public void fireListeners(){
        for (ShapeContainerChangeListener listener: changeListeners){
            listener.onShapeContainerChange();
        }
    }

    public void setSelectShape(DrawableShape selectShape) {

        if(this.selectShape != null) add(this.selectShape,new ShapeProperties(Color.BLACK,0.0f, 0.0f));
        this.selectShape = selectShape;
        if(selectShape != null)add(selectShape,new ShapeProperties(Color.BLUE,0.0f, 0.0f));

    }

    public DrawableShape getSelectShape() {
        return selectShape;
    }

    public DrawableShape getShapeFromCenter(float x, float y) {
        Set<DrawableShape> set = new HashSet<>(container.keySet());
        Set<DrawableShape> toRemove = new HashSet<>();

        //On enlève les formes qui sont trop éloignés du point
        for (DrawableShape shape : set) {
            float[] center = shape.getCenter();
            if (!(x > center[0] - CENTER_PRECISION && x < center[0] + CENTER_PRECISION) || !(y > center[1] - CENTER_PRECISION && y < center[1] + CENTER_PRECISION))
                toRemove.add(shape);
        }
        set.removeAll(toRemove);
        if(set.size() == 0) return null;
        float minDistance = Float.MAX_VALUE;
        DrawableShape minShape = null;
        //On cherche la forme la plus proche
        for (DrawableShape shape : set) {
            float[] center = shape.getCenter();
            float distance = (float) Math.sqrt(Math.pow((center[0]-x),2)+Math.pow((center[1]-y),2));
            if(distance < minDistance){
                minDistance = distance;
                minShape = shape;
            }
        }
        return minShape;
    }
}
