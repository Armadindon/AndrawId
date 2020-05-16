package fr.dut2.andrawid;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import fr.dut2.andrawid.model.share.ExportDrawings;
import fr.dut2.andrawid.model.LineShape;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeProperties;
import fr.dut2.andrawid.view.DrawingView;
import fr.dut2.andrawid.view.GridViewImageAdapter;

public class DrawingActivity extends AppCompatActivity {

    private static final int SAVE_INTERVAL = 60*5*1000;

    private File file;
    private Handler actionsHandler;
    private ExportDrawings exportDrawings;
    private Runnable autoSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        autoSaver = ()->{
            try {
                exportDrawings.save(((DrawingView) findViewById(R.id.drawingView)).getModel(),new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };

        actionsHandler = new Handler();

        actionsHandler.postDelayed(autoSaver,SAVE_INTERVAL);

        file = new File((String) getIntent().getExtras().get("file"));

        exportDrawings = new ExportDrawings();
        DrawingView dv =  findViewById(R.id.drawingView);
        GridView gv = findViewById(R.id.gvPalette);
        GridViewImageAdapter gvImg = new GridViewImageAdapter(this, dv);
        gv.setAdapter(gvImg);

        ShapeContainer sc = new ShapeContainer();
        if(file.exists()) { //On supprime le fichier
            try {
                sc = exportDrawings.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{ //On initialise le fichier
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                exportDrawings.save(sc, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        //final LineShape ls = new LineShape(0.0f, 0.0f, 150.0f, 150.0f);
        //sc.add(ls, new ShapeProperties(10.0f, 30.0f));
        dv.setModel(sc);

        // listener to move the line
        ShapeContainer finalSc = sc;
        dv.setOnClickListener(v -> {
            exportDrawings.save(finalSc, null);
        });

        gv.setOnItemClickListener((parent, v, position, id) -> {
            gvImg.setSelectedShape(position);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoSaver.run();
        actionsHandler.removeCallbacks(autoSaver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionsHandler.postDelayed(autoSaver,SAVE_INTERVAL);
    }
}
