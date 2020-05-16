package fr.dut2.andrawid.model.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.dut2.andrawid.model.DrawingIO;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.view.DrawingView;

public class DrawingBitmapExporter implements DrawingIO {
    @Override
    public void save(ShapeContainer container, OutputStream output) {

        Bitmap bitmap = Bitmap.createBitmap(413, 577, Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        container.draw(canvas);

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
    }

    @Override
    public ShapeContainer load(InputStream input) throws IOException {
        throw new IOException("No loading from bitmap");
    }
}
