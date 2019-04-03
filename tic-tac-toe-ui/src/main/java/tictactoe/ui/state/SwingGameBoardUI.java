package tictactoe.ui.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.ui.base.GamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter @Setter
public class SwingGameBoardUI extends AbstractJFrameUI<GamingStateUIListener> implements GamingStateUI {

    private final int rowsCount;
    private final int columnsCount;

    public SwingGameBoardUI(int boardRows, int boardColumns) throws HeadlessException {
        super("Playing Tic-Tac-Toe");
        setLayout(new GridLayout(rowsCount = boardRows, columnsCount = boardColumns));
        for (int i = 0; i < rowsCount; i++) {
            for(int j = 0; j < columnsCount; j++) {
                add(new TileUI(i, j));
            }
        }
        pack();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == 0) {
                    getListener().pauseGame();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    @Override
    public TileUI getComponent(int n) {
        return (TileUI) getContentPane().getComponent(n);
    }

    /**
     * Top-Left = 0,0
     */
    @Override
    public void mark(int playerNumber, int row, int col) {
        getComponent(calculateMarkPosition(row, col)).setTileValue(playerNumber);
        repaint();
    }

    /**
     * Top-Left = 0,0
     */
    @Override
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
        return row * columnsCount + col;
    }

    /**
     * acceptable row: [0, {@link #rowsCount}]
     * acceptable column: [0, {@link #columnsCount}]
     */
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

    @Getter @Setter
    class TileUI extends JComponent {
        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private MouseAdapter passClickToBackend;

        private final int row;
        private final int col;

        private Integer tileValue;

        void setTileValue(Integer tileValue) {
            this.tileValue = tileValue;
            // disable tile when it's value have been changed
            // as I do not want to make any additional calls to backend
            removeMouseListener(passClickToBackend);
            passClickToBackend = null;
        }

        TileUI(int row, int col) {
            this.row = row;
            this.col = col;
            setBorder(TILE_BORDER);
            addMouseListener(passClickToBackend = new MouseAdapter() {
                boolean isMouseInsideTile;
                boolean isMousePressed;

                @Override
                public void mouseEntered(MouseEvent e) {
                    isMouseInsideTile = true;
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isMouseInsideTile = false;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isMousePressed = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if(isMouseInsideTile && isMousePressed) {
                        assert getListener() != null : "listener should not be null";
                        getListener().tileClicked(row, col);
                    }
                    isMousePressed = false;
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (tileValue == null) {
                return;
            }

            drawNumber((Graphics2D) g, tileValue);
        }

        @Override
        public Dimension getPreferredSize() {
            if (getWidth() < PREFERRED_TILE_SIZE.getWidth() || getHeight() < PREFERRED_TILE_SIZE.getHeight()) {
                return PREFERRED_TILE_SIZE;
            }
            if (getWidth() < getHeight()) {
                return new Dimension(getWidth(), getWidth());
            }
            return new Dimension(getHeight(), getHeight());
        }

        void removeMark() {
            tileValue = null;
        }

        private void drawNumber(Graphics2D g, Integer number) {
            double doubleValue = number * 1000D;
            doubleValue = doubleValue * (doubleValue - 10) * (doubleValue / 10);
            number = (int) ((long) doubleValue);
            g.setColor(new Color(number));
            g.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
        }
    }

}
