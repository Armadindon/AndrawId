package fr.dut2.andrawid;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;

import fr.dut2.andrawid.model.ExportDrawings;
import fr.dut2.andrawid.model.LineShape;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeProperties;
import fr.dut2.andrawid.view.DrawingView;
import fr.dut2.andrawid.view.GridViewImageAdapter;

public class DrawingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        ExportDrawings exportDrawings = new ExportDrawings();
        DrawingView dv =  findViewById(R.id.drawingView);
        GridView gv = findViewById(R.id.gvPalette);
        GridViewImageAdapter gvImg = new GridViewImageAdapter(this, dv);
        gv.setAdapter(gvImg);

        final ShapeContainer sc = new ShapeContainer();
        final LineShape ls = new LineShape(0.0f, 0.0f, 150.0f, 150.0f);
        sc.add(ls, new ShapeProperties(10.0f, 30.0f));
        dv.setModel(sc);



        // listener to move the line
        dv.setOnClickListener(v -> {
            try {
                exportDrawings.save(sc, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        gv.setOnItemClickListener((parent, v, position, id) -> {
            gvImg.setSelectedShape(position);
        });
    }
}
