package utils;

import java.awt.*;

public class ArrayUtils {

    public static boolean isInBounds(Object[][] array, Point p) {
        return p.x >= 0 && p.x < array.length && p.y >= 0 && p.y < array[p.y].length;
    }

    public static void print(char[][] array){
        for (var x : array){
            for (var y : x){
                System.out.print(y);
            }
            System.out.println();
        }
    }

    public static String toString(char[][] rocks) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < rocks.length; i++) {
            for (int j = 0; j < rocks[i].length; j++) {
                sb.append(rocks[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
