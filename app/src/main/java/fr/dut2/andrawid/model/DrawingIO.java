package fr.dut2.andrawid.model;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface DrawingIO {
    void save(ShapeContainer container, OutputStream output) throws JSONException;

    ShapeContainer load(InputStream input) throws IOException, JSONException;
}
