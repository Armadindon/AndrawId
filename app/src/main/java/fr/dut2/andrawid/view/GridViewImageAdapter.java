package fr.dut2.andrawid.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import fr.dut2.andrawid.model.shape.ShapeKind;

public class GridViewImageAdapter extends BaseAdapter {

    private Activity context;
    private DrawingView drawingView;
    private ShapeKind[] shapeKinds = {ShapeKind.SEGMENT, ShapeKind.RECTANGLE, ShapeKind.CURSIVE};
    private int selected = 0;

    public GridViewImageAdapter (Activity context, DrawingView drawingView){
        this.context = context;
        this.drawingView = drawingView;
        drawingView.setShapeKind(shapeKinds[selected]);
    }


    @Override
    public int getCount() {
        return shapeKinds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        if(position == selected){
            imageView.setImageResource(shapeKinds[position].getSelectShape());

        } else {
            imageView.setImageResource(shapeKinds[position].getUnselectShape());

        }

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(200, 200));

        return imageView;
    }

    public void setSelectedShape(int position){
        selected = position;
        drawingView.setShapeKind(shapeKinds[position]);
        notifyDataSetChanged();
    }
}
