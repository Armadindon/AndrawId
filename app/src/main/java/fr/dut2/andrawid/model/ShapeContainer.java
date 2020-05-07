package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;

public class ShapeContainer {

    private HashMap<DrawableShape,ShapeProperties> container;
    private ArrayList<ShapeContainerChangeListener> changeListeners = new ArrayList<>();

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
}
