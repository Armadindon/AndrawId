package fr.dut2.andrawid.model;

public class Utils {

    /**
     * Convert a string of float[] from a JSON file
     *
     * @param s String of float[]
     * @return Array of float
     */
    public static float[] convertStringToFloatArray(String s) {
        String[] strFloat = s.replace("[", "").replace("]", "").split(",");
        float[] res = new float[strFloat.length];

        for (int i = 0; i < strFloat.length; i++) {
            res[i] = Float.parseFloat(strFloat[i]);
        }


        return res;
    }

}
