package Utils;

import java.util.Random;

/**
 * Created by dmytro.bezzubikov on 8/10/2015.
 */
public class Utils {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
