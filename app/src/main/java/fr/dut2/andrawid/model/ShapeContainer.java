package fr.dut2.andrawid.model;

import android.graphics.Canvas;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class ShapeContainer {
    private HashMap<DrawableShape,ShapeProperties> container;

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
}
