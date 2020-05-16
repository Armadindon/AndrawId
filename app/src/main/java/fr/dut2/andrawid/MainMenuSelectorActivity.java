package fr.dut2.andrawid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainMenuSelectorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_selector);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);

        File docPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        System.out.println("External Storage exist : "+docPath.exists());
        File appPath = new File(docPath, "andrawid");
        // the directory appPath may not exist yet, we can create it
        appPath.mkdir();


        List<File> drawings = Arrays.asList(appPath.listFiles());
        ArrayAdapter<File> drawingsAdapter = new ArrayAdapter<File>(this,android.R.layout.simple_list_item_1,drawings);


        ((ListView)findViewById(R.id.drawingList)).setAdapter(drawingsAdapter);
        ((ListView)findViewById(R.id.drawingList)).setOnItemClickListener((parent, view, position, id)->{
            File drawing = drawings.get(position);
            Intent nextActivity = new Intent(this,DrawingActivity.class);
            nextActivity.putExtra("file",drawing.getAbsolutePath());
            startActivity(nextActivity);
        });

        ((Button)findViewById(R.id.createButton)).setOnClickListener((b)->{
            EditText fileName = ((EditText)findViewById(R.id.createText));
            if(fileName.getText().toString().isEmpty()){
                fileName.setError("You need to pass a value");
                return;
            }
            System.out.println();
            Intent nextActivity = new Intent(this,DrawingActivity.class);
            nextActivity.putExtra("file", new File(appPath,fileName.getText().toString()+".json").getPath());
            startActivity(nextActivity);

        });
    }
}
