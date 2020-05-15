package fr.dut2.andrawid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Arrays;

import fr.dut2.andrawid.model.DrawableShape;
import fr.dut2.andrawid.model.ExportDrawings;
import fr.dut2.andrawid.model.ShapeBuilder;
import fr.dut2.andrawid.model.ShapeContainer;
import fr.dut2.andrawid.model.ShapeContainerChangeListener;
import fr.dut2.andrawid.model.ShapeKind;
import fr.dut2.andrawid.model.ShapeProperties;

public class DrawingView extends View {

    private static final int MAX_MOVE = 50;

    private ShapeContainer model;
    private ShapeKind selected = ShapeKind.SEGMENT;

    private float[] points;
    private float[] eventStartPosition;
    private Handler selectHandler = new Handler();
    private Runnable selectionRunnable;

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

    public void setShapeKind(ShapeKind shapeKind){
        this.selected = shapeKind;
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

    public void setPoints(float[] points) {
        this.points = points;
    }

    public void addpoint(float x, float y){
        points = Arrays.copyOf(points, points.length + 2);
        points[points.length-1] = y;
        points[points.length-2] = x;
    }

    public float[] getPoints() {
        return points;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.performClick();

        System.out.println(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            setPoints(new float[]{event.getX(), event.getY()});
            if(model.getSelectShape() == null){
                eventStartPosition = new float[]{event.getX(), event.getY()};
                selectionRunnable = ()->{
                    System.out.println("Pop");
                    System.out.println(model.getShapeFromCenter(eventStartPosition[0],eventStartPosition[1]));
                    model.setSelectShape(model.getShapeFromCenter(eventStartPosition[0],eventStartPosition[1]));
                };
                selectHandler.postDelayed(selectionRunnable,1000);
            }else{
                //on déplace la forme
                DrawableShape tempShape = model.getSelectShape();
                model.setSelectShape(null);
                model.add(tempShape,new ShapeProperties(Color.BLACK,event.getX(),event.getY()));
            }
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(selected == ShapeKind.CURSIVE) {
                addpoint(event.getX(), event.getY());
                addpoint(event.getX(), event.getY());
            }

            if ((event.getX() > eventStartPosition[0] + MAX_MOVE || event.getX() < eventStartPosition[0] - MAX_MOVE) ||
                    (event.getY() > eventStartPosition[1] + MAX_MOVE || event.getY() < eventStartPosition[1] - MAX_MOVE)){
                selectHandler.removeCallbacks(selectionRunnable);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            //si il reste un handler, on le vire
            selectHandler.removeCallbacks(selectionRunnable);

            addpoint(event.getX(),event.getY());
            ShapeBuilder sB = new ShapeBuilder();
            sB.setShapeKind(selected);

            model.add(sB.build(getPoints()), new ShapeProperties(0.0f, 0.0f));

            model.fireListeners();
        }


        return super.onTouchEvent(event);
    }


}
