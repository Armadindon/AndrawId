package fr.dut2.andrawid.model.share;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.dut2.andrawid.model.shape.ShapeContainer;

public interface DrawingIO {
    void save(ShapeContainer container, OutputStream output);

    ShapeContainer load(InputStream input) throws IOException;
}
