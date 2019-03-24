package tictactoe;

import tictactoe.app.flow.TicTacToeAppFlow;

import javax.swing.*;

public class TicTacToeApplicationRunner {

    public static void main(String[] args) {
        TicTacToeAppFlow ticTacToeAppFlow = new TicTacToeAppFlow();
        ticTacToeAppFlow.run();
    }
}
