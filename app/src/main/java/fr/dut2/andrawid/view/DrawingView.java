package fr.dut2.andrawid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import fr.dut2.andrawid.model.LineShape;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeContainerChangeListener;
import fr.dut2.andrawid.model.ShapeProperties;

public class DrawingView extends View {

    private ShapeContainer model;

    private float down_x;
    private float down_y;

    private float up_x;
    private float up_y;



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

        ShapeContainerChangeListener listener = this::invalidate;

        this.model.addChangeListener(listener);

        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (model != null){
            model.draw(canvas);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


    // Cette série de setter est simplement utile pour le debugging
    private void setActionDown(MotionEvent event){
        this.down_x = event.getX();
        this.down_y = event.getY();
    }

    private void setActionUp(MotionEvent event){
        this.up_x = event.getX();
        this.up_y = event.getY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.performClick();

        System.out.println(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN){ setActionDown(event); }
        if (event.getAction() == MotionEvent.ACTION_UP){
            setActionUp(event);

            LineShape line = new LineShape(down_x, down_y, up_x, up_y);
            System.out.println(down_x + ", " + down_y + " :: " +  up_x + ", " +  up_y);
            model.add(line, new ShapeProperties(0.0f, 0.0f));

        }


        return super.onTouchEvent(event);
    }

}
