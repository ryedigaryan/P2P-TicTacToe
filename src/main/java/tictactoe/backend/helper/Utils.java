package tictactoe.backend.helper;

import java.util.Random;

public class Utils {
    public static Random random = new Random();

    public static int randomInt(int bound) {
        return random.nextInt(bound);
    }
}
