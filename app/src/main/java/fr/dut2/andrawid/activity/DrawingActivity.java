package fr.dut2.andrawid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.dut2.andrawid.R;
import fr.dut2.andrawid.model.shape.ShapeContainer;
import fr.dut2.andrawid.model.share.DrawingBitmapExporter;
import fr.dut2.andrawid.model.share.DrawingIO;
import fr.dut2.andrawid.model.share.ExportDrawings;
import fr.dut2.andrawid.view.DrawingView;
import fr.dut2.andrawid.view.GridViewImageAdapter;


public class DrawingActivity extends AppCompatActivity {

    private static final int SAVE_INTERVAL = 60 * 5 * 1000;

    private File file;
    private Handler actionsHandler;
    private ExportDrawings exportDrawings;
    private Runnable autoSaver;
    private DrawingView dv;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);


        autoSaver = () -> {
            try {
                exportDrawings.save(((DrawingView) findViewById(R.id.drawingView)).getModel(), new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        };


        actionsHandler = new Handler();

        actionsHandler.postDelayed(autoSaver, SAVE_INTERVAL);

        file = new File((String) getIntent().getExtras().get("file"));

        exportDrawings = new ExportDrawings();

        dv = findViewById(R.id.drawingView);

        GridView gv = findViewById(R.id.gvPalette);
        GridViewImageAdapter gvImg = new GridViewImageAdapter(this, dv);
        gv.setAdapter(gvImg);

        ShapeContainer sc = new ShapeContainer();
        if (file.exists()) { //On supprime le fichier
            try {
                sc = exportDrawings.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else { //On initialise le fichier
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

        });

        gv.setOnItemClickListener((parent, v, position, id) -> {
            gvImg.setSelectedShape(position);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.shareButton);
        // mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Je garde le switch au lieu d'un if, au cas ou je rajoute d'autre bouttons
        switch (item.getItemId()) {
            case R.id.shareButton:
                Toast toast = Toast.makeText(this, "Impossible d'importer la librairie appcombat v7 / v4 parce ???!, j'ai perdu 3h, j'en pouvais plus, presque tout le code est là mais flute. j'en peux plus.", Toast.LENGTH_LONG);
                toast.show();

                /*
                File tmpPath = new File(this.getCacheDir(), "temporaryImages");
                tmpPath.mkdir(); // create the directory if required
                File filepath = new File(tmpPath, file.getName() + ".jpg");
                DrawingIO exporter = new DrawingBitmapExporter(Bitmap.CompressFormat.JPEG);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(filepath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                exporter.save(dv.getModel(), fos); // save the picture to a jpeg file

                // create the share intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpeg"); // to specify the mime type of the file
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filepath));
                mShareActionProvider.setShareIntent(shareIntent);
                // the action provider is configured!
                return true;
                */

            case R.id.postButton:
                autoSaver.run();
                File docPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                docPath.mkdir(); // NOTE: Maxime: J'ai perdu du temps pour comprendre ce qui n'allait pas, ce dossier n'était pas créer dans mon téléphone :triste:
                File appPath = new File(docPath, "andrawidIMG");
                appPath.mkdir();
                File imgFile = new File(appPath, file.getName().replace("json", "jpg"));
                DrawingIO exporter = new DrawingBitmapExporter(Bitmap.CompressFormat.JPEG);
                try {
                    exporter.save(dv.getModel(), new FileOutputStream(imgFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imgFile.getTotalSpace();
                Intent nextActivity = new Intent(this, PostPictureActivity.class);
                nextActivity.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imgFile));
                startActivity(nextActivity);

                return true;

        }

        return super.onOptionsItemSelected(item);


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
        actionsHandler.postDelayed(autoSaver, SAVE_INTERVAL);
    }
}
