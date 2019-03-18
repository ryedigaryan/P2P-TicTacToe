package tictactoe.ui.game;

import javax.swing.*;
import java.awt.*;

public class GameBoardUI extends JFrame {

    private int rowsCount;
    private int columnsCount;

    public GameBoardUI(int borderWidth, int borderHeight) throws HeadlessException {
        setLayout(new GridLayout(rowsCount = borderWidth, columnsCount = borderHeight));
        final int markCount = rowsCount * columnsCount;
        for (int i = 0; i < markCount; i++) {
            add(new TileUI());
        }
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public TileUI getComponent(int n) {
        return (TileUI) getContentPane().getComponent(n);
    }

    /**
     * Top-Left = 0,0
     */
    public void markO(int row, int col) {
        getComponent(calculateMarkPosition(row, col)).markO();
        repaint();
        System.out.println("Marked O at " + calculateMarkPosition(row, col));
    }

    /**
     * Top-Left = 0,0
     */
    public void markX(int row, int col) {
        getComponent(calculateMarkPosition(row, col)).markX();
        repaint();
        System.out.println("Marked X at " + calculateMarkPosition(row, col));
    }

    public void removeMark(int row, int col) {
        getComponent(calculateMarkPosition(row, col)).removeMark();
        repaint();
        System.out.println("Marked X at " + calculateMarkPosition(row, col));
    }

    /**
     * Top-Left = 0,0
     */
    private int calculateMarkPosition(int row, int col) {
        checkDimensions(row, col);
        return col * rowsCount + row;
    }

    private void checkDimensions(int row, int col) {
        checkRow(row);
        checkColumn(col);
    }

    private void checkRow(int row) {
        if(row < 0 || row >= rowsCount)
            throw new IllegalArgumentException("Expected row number in range [1, " + rowsCount + "] but received " + row);
    }

    private void checkColumn(int col) {
        if(col < 0 || col >= columnsCount)
            throw new IllegalArgumentException("Expected column number in range [1, " + columnsCount + "] but received " + col);
    }
}
