package fr.dut2.andrawid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeContainerChangeListener;

public class DrawingView extends View {

    private ShapeContainer model;

    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setModel(ShapeContainer shaperContainer){
        this.model = shaperContainer;

        //ShapeContainerChangeListener listener = this::invalidate;

        //this.model.addChangeListener(listener);

        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (model != null){
            System.out.println("aled26");
            model.draw(canvas);
        }
    }
}
