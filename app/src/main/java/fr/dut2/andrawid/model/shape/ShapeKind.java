package fr.dut2.andrawid.model.shape;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import java.nio.file.Path;

import fr.dut2.andrawid.R;

public enum ShapeKind {

    SEGMENT(R.drawable.unselect_segment, R.drawable.select_segment),
    RECTANGLE(R.drawable.unselect_rect, R.drawable.select_rect),
    CURSIVE(R.drawable.unselect_cursive, R.drawable.select_cursive),
    DOTED_SEGMENT(R.drawable.unselect_doted_segment, R.drawable.select_doted_segment);

    private int unselectShape;
    private int selectShape;

    private ShapeKind(int unselectShape, int selectShape){
        this.unselectShape = unselectShape;
        this.selectShape = selectShape;
    }

    public int getUnselectShape(){
        return unselectShape;
    }

    public int getSelectShape() {
        return selectShape;
    }
}


