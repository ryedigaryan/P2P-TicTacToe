package tictactoe;

import tictactoe.app.flow.TicTacToeAppFlow;

public class TicTacToeApplicationRunner {

    public static void main(String[] args) {
        TicTacToeAppFlow ticTacToeAppFlow = new TicTacToeAppFlow();
        ticTacToeAppFlow.run();
    }
}
