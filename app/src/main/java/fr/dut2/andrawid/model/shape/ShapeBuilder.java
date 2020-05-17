package fr.dut2.andrawid.model.shape;

import fr.dut2.andrawid.model.drawings.CursiveShape;
import fr.dut2.andrawid.model.drawings.DrawableShape;
import fr.dut2.andrawid.model.drawings.LineShape;
import fr.dut2.andrawid.model.drawings.RectangleShape;

public class ShapeBuilder {
    private ShapeKind shape;

    /**
     * to set the current kind of shape to create
     */
    public void setShapeKind(ShapeKind shapeKind){
        this.shape = shapeKind;
    }

    /**
     * to build a shape
     *
     * @param coords coordinates of the shape:
     *               coords[0] and coords[1] are the x and y of the start of the shape
     *               coords[coords.length-2] and coords[coords.length-1] are the x and y of the end of the shape
     *               some shape kinds (like CURSIVE) may admit intermediate points in the array
     */
    public DrawableShape build(float... coords){
        DrawableShape buildShape = null;

        switch (shape){
            case SEGMENT:
                buildShape = new LineShape(coords);
                break;

            case RECTANGLE:
                buildShape = new RectangleShape(coords);
                break;

            case CURSIVE:
                buildShape = new CursiveShape(coords);
                break;

        }
        return buildShape;
    }
}
