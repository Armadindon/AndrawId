package fr.dut2.andrawid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import fr.dut2.andrawid.model.LineShape;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeKind;
import fr.dut2.andrawid.model.ShapeProperties;
import fr.dut2.andrawid.view.DrawingView;

public class DrawingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        DrawingView dv = (DrawingView) findViewById(R.id.drawingView);
        GridView lv = (GridView) findViewById(R.id.gvPalette);

        lv.setAdapter(new ArrayAdapter<ShapeKind>(this, android.R.layout.simple_list_item_1, ShapeKind.values()));

        final ShapeContainer sc = new ShapeContainer();
        final LineShape ls = new LineShape(0.0f, 0.0f, 150.0f, 150.0f);
        sc.add(ls, new ShapeProperties(10.0f, 30.0f));
        dv.setModel(sc);


        // listener to move the line
        dv.setOnClickListener(v -> {
            sc.add(ls, new ShapeProperties(20.0f, 40.0f));
        });


        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            // selectedShapeKind = adapterView.getItemAtPosition(i);
        });

    }
}
