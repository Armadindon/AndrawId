package fr.dut2.andrawid;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.Arrays;

import fr.dut2.andrawid.model.share.ExportDrawings;
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

        System.out.println(Arrays.toString(Utils.convertStringToFloatArray("[0.0, 145.45, 112.7, 741.54")));

        // listener to move the line
        dv.setOnClickListener(v -> {
            exportDrawings.save(sc, null);
        });

        gv.setOnItemClickListener((parent, v, position, id) -> {
            gvImg.setSelectedShape(position);
        });
    }
}
