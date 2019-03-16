package ui.game;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.HeadlessException;

public class GameBoardUI extends JFrame {

    private int rows;
    private int columns;

    public GameBoardUI(int borderWidth, int borderHeight) throws HeadlessException {
        setLayout(new GridLayout(rows = borderWidth, columns = borderHeight));
        final int markCount = rows * columns;
        for (int i = 0; i < markCount; i++) {
            add(new Tile());
        }
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Top-Left = 0,0
     */
    public void markO(int x, int y) {
        getComponent(calculateMarkPosition(x, y)).markO();
        repaint();
        System.out.println("Marked O at " + calculateMarkPosition(x, y));
    }

    /**
     * Top-Left = 0,0
     */
    public void markX(int x, int y) {
        getComponent(calculateMarkPosition(x, y)).markX();
        repaint();
        System.out.println("Marked X at " + calculateMarkPosition(x, y));
    }

    public void removeMark(int x, int y) {
        getComponent(calculateMarkPosition(x, y)).removeMark();
        repaint();
        System.out.println("Marked X at " + calculateMarkPosition(x, y));
    }

    @Override
    public Tile getComponent(int n) {
        return (Tile) getContentPane().getComponent(n);
    }

    /**
     * Top-Left = 0,0
     */
    private int calculateMarkPosition(int x, int y) {
        return y * rows + x;
    }
}
