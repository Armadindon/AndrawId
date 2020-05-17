package fr.dut2.andrawid.model.share;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.dut2.andrawid.model.shape.ShapeContainer;

public class DrawingBitmapExporter implements DrawingIO {

    private final Bitmap.CompressFormat format;

    public DrawingBitmapExporter(Bitmap.CompressFormat format){
        this.format = format;
    }

    private Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(realImage, width,
                height, filter);
    }

    @Override
    public void save(ShapeContainer container, OutputStream output) {
        try(FileOutputStream outputStream = (FileOutputStream) output) {

            Bitmap bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);

            container.draw(canvas);

            scaleDown(bitmap, 1024, true).compress(this.format, 100, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ShapeContainer load(InputStream input) throws IOException {
        throw new IOException("No loading from bitmap");
    }
}
