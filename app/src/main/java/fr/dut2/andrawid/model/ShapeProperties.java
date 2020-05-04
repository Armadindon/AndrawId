package fr.dut2.andrawid.model;

public class ShapeProperties {
    private float[] origin;

    public ShapeProperties(float... origin){
        if(origin.length != 2) throw new IllegalArgumentException("You need to pass 2 floating numbers");
        this.origin = origin;
    }

    public float[] getOrigin() {
        return origin;
    }
}
