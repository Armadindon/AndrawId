package fr.dut2.andrawid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Arrays;

import fr.dut2.andrawid.model.drawings.DrawableShape;
import fr.dut2.andrawid.model.shape.ShapeBuilder;
import fr.dut2.andrawid.model.shape.ShapeContainer;
import fr.dut2.andrawid.model.shape.ShapeContainerChangeListener;
import fr.dut2.andrawid.model.shape.ShapeKind;
import fr.dut2.andrawid.model.shape.ShapeProperties;

public class DrawingView extends View {

    private static final int CLICK_TIME = 200; //au dessus de combien de temps on considère un évènement par autre chose qu'un clic
    private static final int MAX_MOVE = 50; //Mouvement Maximal du point d'origine lorsque l'on fait une selection
    private static final int MAX_MOVE_DELETE = 150; //Mouvement maximal entre deux clics lorsque l'on fait un delete
    private static final int SELECT_TIME = 1000; //Au bout de combien de temps appuyé on séléctionne la forme
    private static final int DELETE_TIME = 1500; //Combien de temps max pour faire DELETE_CLICK_NUMBER
    private static final int DELETE_CLICK_NUMBER = 3; //Nombre de clics pour supprimer une forme


    private ShapeContainer model;
    private ShapeKind selected = ShapeKind.SEGMENT;

    private float[] points;
    private float[] origin = new float[]{0f,0f};
    private int nbClick=0;
    private Handler actionsHandler = new Handler();
    private Runnable selectionRunnable;
    private Runnable deleteRunnable = null;
    private long startClick; //Permet de différencier un click d'une action prolongée comme la création d'une forme

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

    public ShapeContainer getModel() {
        return model;
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

    public void setOrigin(float[] points) {
        this.origin = points;
    }

    public void resetPoints(){
        points = new float[]{0f,0f};
    }

    public void addpoint(float x, float y){
        points = Arrays.copyOf(points, points.length + 2);
        points[points.length-1] = y-origin[1];
        points[points.length-2] = x-origin[0];
    }

    public float[] getPoints() {
        return points;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.performClick();

        //System.out.println(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            startClick = SystemClock.elapsedRealtime();
            if (deleteRunnable != null &&
                    ((event.getX() < origin[0] + MAX_MOVE_DELETE || event.getX() > origin[0] - MAX_MOVE_DELETE) ||
                            (event.getY() < origin[1] + MAX_MOVE_DELETE || event.getY() > origin[1] - MAX_MOVE_DELETE))){
                nbClick++;
                if(nbClick >= DELETE_CLICK_NUMBER){
                    System.out.println("On supprime la forme !");
                    System.out.println(model.remove(model.getShapeFromCenter(origin[0],origin[1])));
                    actionsHandler.removeCallbacks(deleteRunnable);
                    deleteRunnable = null;
                    nbClick = 0;
                    invalidate();
                }
            }else if(deleteRunnable !=null){ //On reset
                deleteRunnable = null;
                nbClick = 0;
            }else{
                deleteRunnable= ()->{
                    this.nbClick = 0;
                    this.deleteRunnable = null;
                };
                actionsHandler.postDelayed(deleteRunnable,DELETE_TIME);
            }

            setOrigin(new float[]{event.getX(), event.getY()});
            resetPoints();

            if(model.getSelectShape() == null){
                selectionRunnable = ()->{
                    model.setSelectShape(model.getShapeFromCenter(origin[0], origin[1]));
                    invalidate();
                };
                actionsHandler.postDelayed(selectionRunnable,SELECT_TIME);
            }else{
                //on déplace la forme

                DrawableShape tempShape = model.getSelectShape();
                model.setSelectShape(null);
                model.add(tempShape,new ShapeProperties(Color.WHITE,event.getX(),event.getY()));
                invalidate();//On redéssine
            }
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(selected == ShapeKind.CURSIVE) {
                addpoint(event.getX(), event.getY());
                addpoint(event.getX(), event.getY());
            }

            if ((event.getX() > origin[0] + MAX_MOVE || event.getX() < origin[0] - MAX_MOVE) ||
                    (event.getY() > origin[1] + MAX_MOVE || event.getY() < origin[1] - MAX_MOVE)){
                actionsHandler.removeCallbacks(selectionRunnable);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            //si il reste un handler, on le vire
            actionsHandler.removeCallbacks(selectionRunnable);

            if(SystemClock.elapsedRealtime()>startClick+CLICK_TIME){
                addpoint(event.getX(),event.getY());
                ShapeBuilder sB = new ShapeBuilder();
                sB.setShapeKind(selected);
                model.add(sB.build(getPoints()), new ShapeProperties(origin[0], origin[1]));
            }

            model.fireListeners();
        }


        return super.onTouchEvent(event);
    }


}
