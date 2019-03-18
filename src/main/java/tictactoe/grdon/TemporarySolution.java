package tictactoe.grdon;

import javax.swing.*;
import java.awt.*;

/**
 * A class containing temporary solutions for different problems.
 * This class purpose is saving time :D.
 */
public abstract class TemporarySolution {
    public static final int MAX_PLAYERS_COUNT = 2;

    public static void showWinnerNumber(Component parent, int winner) {
        JOptionPane.showMessageDialog(parent, "Tic-Tac-Toe winner is " + winner);
        System.exit(0);
    }
}
