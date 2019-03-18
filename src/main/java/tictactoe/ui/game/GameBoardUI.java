package tictactoe.ui.game;

import lombok.Getter;
import lombok.Setter;
import tictactoe.ui.game.listener.TileClickListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

@Getter @Setter
public class GameBoardUI extends JFrame {

    private final int rowsCount;
    private final int columnsCount;

    TileClickListener tileClickListener;

    public GameBoardUI(int boardRows, int boardColumns) throws HeadlessException {
        setLayout(new GridLayout(rowsCount = boardRows, columnsCount = boardColumns));
        final int markCount = rowsCount * columnsCount;
        for (int i = 0; i < rowsCount; i++) {
            for(int j = 0; j < columnsCount; j++) {
                add(new TileUI(i, j));
            }
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
    }

    /**
     * Top-Left = 0,0
     */
    public void markX(int row, int col) {
        getComponent(calculateMarkPosition(row, col)).markX();
        repaint();
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
        return row * rowsCount + col;
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

    private static final Dimension PREFERRED_TILE_SIZE = new Dimension(100, 100);
    private static final Border TILE_BORDER = BorderFactory.createLineBorder(Color.BLACK);

    @Getter
    public class TileUI extends JComponent {
        private static final boolean MARKER_O = true;
        private static final boolean MARKER_X = false;

        private final int row;
        private final int col;

        private Boolean tileValue;

        public TileUI(int row, int col) {
            this.row = row;
            this.col = col;
            setBorder(TILE_BORDER);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    assert getTileClickListener() != null : "tileClickListener should not be null";
                    getTileClickListener().tileClicked(row, col);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // null check should be done first
            if(tileValue == null) {
                removeMark();
                return;
            }
            if(tileValue == MARKER_O) {
                drawO((Graphics2D) g);
                return;
            }
            drawX((Graphics2D) g);
        }



        @Override
        public Dimension getPreferredSize() {
            if(getWidth() < PREFERRED_TILE_SIZE.getWidth() || getHeight() < PREFERRED_TILE_SIZE.getHeight()) {
                return PREFERRED_TILE_SIZE;
            }
            if(getWidth() < getHeight()) {
                return new Dimension(getWidth(), getWidth());
            }
            return new Dimension(getHeight(), getHeight());
        }

        public void markO() {
            tileValue = MARKER_O;
        }

        public void markX() {
            tileValue = MARKER_X;
        }

        public void removeMark() {
            tileValue = null;
        }

        private void drawO(Graphics2D g) {
            g.draw(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        }

        private void drawX(Graphics2D g) {
            g.drawLine(0, 0, getWidth(), getHeight());
            g.drawLine(getWidth(), 0, 0, getHeight());
        }
    }

}
