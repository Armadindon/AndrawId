package fr.dut2.andrawid.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class ExportDrawings implements DrawingIO {
    @Override
    public void save(ShapeContainer shapeContainer, OutputStream output) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray content = new JSONArray();

        jsonObject.put("type", "drawings");
        jsonObject.put("formatVersion", "9.1");
        jsonObject.put("modificationVersion", Calendar.getInstance().getTimeInMillis());

        HashMap<DrawableShape, ShapeProperties> container = shapeContainer.getContainer() ;

        for (Map.Entry<DrawableShape, ShapeProperties> entry: container.entrySet()) {
            DrawableShape shape = entry.getKey();
            ShapeProperties shapeProperties = entry.getValue();

            JSONObject jsonShape = new JSONObject();
            jsonShape.put("origin", Arrays.toString(shapeProperties.getOrigin()));
            jsonShape.put("points", Arrays.toString(shape.getPoints()));

            jsonShape.put("shapeKind", shape.getShapeKind().name());

            content.put(jsonShape);
        }
        jsonObject.put("content", content);


        System.out.println(jsonObject.toString());
    }

    @Override
    public ShapeContainer load(InputStream input) {
        return null;
    }
}
