package fr.dut2.andrawid.model.share;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.dut2.andrawid.model.Utils;
import fr.dut2.andrawid.model.drawings.DrawableShape;
import fr.dut2.andrawid.model.shape.ShapeBuilder;
import fr.dut2.andrawid.model.shape.ShapeContainer;
import fr.dut2.andrawid.model.shape.ShapeKind;
import fr.dut2.andrawid.model.shape.ShapeProperties;
import fr.dut2.andrawid.model.share.DrawingIO;

public class ExportDrawings implements DrawingIO {
    @Override
    public void save(ShapeContainer shapeContainer, OutputStream output) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output, "UTF-8");

            JSONObject jsonObject = new JSONObject();
            JSONArray content = new JSONArray();

            jsonObject.put("type", "drawings");
            jsonObject.put("formatVersion", "9.1");
            jsonObject.put("modificationVersion", Calendar.getInstance().getTimeInMillis());

            HashMap<DrawableShape, ShapeProperties> container = shapeContainer.getContainer();

            for (Map.Entry<DrawableShape, ShapeProperties> entry : container.entrySet()) {
                DrawableShape shape = entry.getKey();
                ShapeProperties shapeProperties = entry.getValue();

                JSONObject jsonShape = new JSONObject();
                jsonShape.put("origin", Arrays.toString(shapeProperties.getOrigin()));
                jsonShape.put("points", Arrays.toString(shape.getPoints()));

                jsonShape.put("shapeKind", shape.getShapeKind().name());

                content.put(jsonShape);
            }
            jsonObject.put("content", content);

            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (JSONException | IOException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

    @Override
    public ShapeContainer load(InputStream input) {
        try {
            ShapeContainer shapeContainer = new ShapeContainer();
            ShapeBuilder shapeBuilder = new ShapeBuilder();
            StringBuilder responseStrBuilder = new StringBuilder();


            BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);


            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            JSONArray jsonArray = jsonObject.getJSONArray("content");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject shape = (JSONObject) jsonArray.get(i);

                float[] points = Utils.convertStringToFloatArray(shape.getString("points"));
                float[] origin = Utils.convertStringToFloatArray(shape.getString("origin"));
                ShapeKind shapeKind = ShapeKind.valueOf(shape.getString("shapeKind"));

                shapeBuilder.setShapeKind(shapeKind);
                shapeContainer.add(shapeBuilder.build(points), new ShapeProperties(origin));
            }

            return shapeContainer;
        } catch (JSONException | IOException e) {
            Log.e("Exception: load::" + this.toString(), e.getMessage());
        }

        return null;
    }
}
