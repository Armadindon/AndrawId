package fr.dut2.andrawid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import fr.dut2.andrawid.model.LineShape;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeKind;
import fr.dut2.andrawid.model.ShapeProperties;
import fr.dut2.andrawid.view.DrawingView;
import fr.dut2.andrawid.view.GridViewImageAdapter;
import android.widget.AdapterView;

public class DrawingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        DrawingView dv = (DrawingView) findViewById(R.id.drawingView);
        GridView gv = (GridView) findViewById(R.id.gvPalette);
        GridViewImageAdapter gvImg = new GridViewImageAdapter(this, dv);
        gv.setAdapter(gvImg);

        final ShapeContainer sc = new ShapeContainer();
        final LineShape ls = new LineShape(0.0f, 0.0f, 150.0f, 150.0f);
        sc.add(ls, new ShapeProperties(10.0f, 30.0f));
        dv.setModel(sc);


        // listener to move the line
        dv.setOnClickListener(v -> {
            sc.add(ls, new ShapeProperties(20.0f, 40.0f));
        });

        gv.setOnItemClickListener((parent, v, position, id) -> {
            gvImg.setSelectedShape(position);
        });
    }
}
