package tictactoe.app.grdon;

import java.util.function.Consumer;

public interface TemporarySolution {

    static void printOnErr(Object o) {
        System.err.println(o);
    }

    static void printOnOut(Object o) {
        System.out.println(o);
    }
}
