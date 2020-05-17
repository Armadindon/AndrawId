package fr.dut2.andrawid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.dut2.andrawid.R;

public class PostPictureActivity extends AppCompatActivity {

    private Uri uri;
    private String SITE_URL = "https://codfish.pythonanywhere.com/sendPicture";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_picture);
        mContext = this;

        this.uri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
        ImageView iv = findViewById(R.id.imgvPreview);
        iv.setImageURI(this.uri);
        Button button = findViewById(R.id.validationButton);
        button.setOnClickListener((v) -> {
            EditText comment = findViewById(R.id.etComment); // edit text where the comment is put
            boolean result = false;
            new SendFiles().execute(comment.getText().toString());

            Toast t = Toast.makeText(this, result ? "Success" : "Failure", Toast.LENGTH_SHORT);
            if (result)
                finish(); // quit the activity in case of success, otherwise we allow the user to resubmit with the button
        });



    }


    private class SendFiles extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean res = false;
            try {
                res = postPicture(mContext, SITE_URL, uri, strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

        }

        private boolean postPicture(Context context, String siteUrl, Uri pictureUri, String comment) throws IOException {
            String url = siteUrl;
            if (comment != null)
                url += "?comment=" + Uri.encode(comment);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            try {
                InputStream is = context.getContentResolver().openInputStream(pictureUri);

                int fileLength = is.available(); // not very clean but should work (inspired by https://stackoverflow.com/questions/6049926/get-the-size-of-an-android-file-resource)
                conn.setDoOutput(true); // we send data in the body of the request
                conn.setFixedLengthStreamingMode(fileLength);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "image/jpeg");
                OutputStream os = conn.getOutputStream();

                byte[] buffer = new byte[1024];
                for (int r = is.read(buffer); r != -1; r = is.read(buffer)) {
                    os.write(buffer, 0, r);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e)
                    {
                        break;
                    }
                }

                is.close();
                os.flush();
                os.close();
                try {
                    conn.getInputStream().close();
                } catch (Exception ignored) {
                }
                return conn.getResponseCode() == 200; // file has been posted without problem
            } finally {
                conn.disconnect();
            }
        }
    }
}
