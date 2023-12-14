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
}
