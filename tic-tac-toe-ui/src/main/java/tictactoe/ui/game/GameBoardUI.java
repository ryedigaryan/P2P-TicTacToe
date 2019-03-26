package tictactoe.ui.game;

import lombok.Getter;
import lombok.Setter;
import tictactoe.ui.state.common.AbstractJFrameUI;
import tictactoe.connector.ui.base.IGamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter @Setter
public class GameBoardUI extends AbstractJFrameUI<GamingStateUIListener> implements IGamingStateUI {

    private final int rowsCount;
    private final int columnsCount;

    public GameBoardUI(int boardRows, int boardColumns) throws HeadlessException {
        setLayout(new GridLayout(rowsCount = boardRows, columnsCount = boardColumns));
        for (int i = 0; i < rowsCount; i++) {
            for(int j = 0; j < columnsCount; j++) {
                add(new TileUI(i, j));
            }
        }
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
    // TODO: 3/24/2019 add ability to draw more than 2 player's game
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
    public class TileUI extends JComponent {
        private final int row;
        private final int col;

        private Integer tileValue;

        public TileUI(int row, int col) {
            this.row = row;
            this.col = col;
            setBorder(TILE_BORDER);
            addMouseListener(new MouseAdapter() {
                // TODO: 3/26/2019 may be this should be changed to mouseClicked, for now for faster mouses it is convenient to use mousePressed
                @Override
                public void mousePressed(MouseEvent e) {
                    assert getListener() != null : "listener should not be null";
                    getListener().tileClicked(row, col);
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
            drawNumber((Graphics2D) g, tileValue);
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

        public void removeMark() {
            tileValue = null;
        }

        private void drawNumber(Graphics2D g, Integer number) {
//            IntStream.range(0, 5).mapToDouble(n -> n * 1000).map(n -> n * (n - 10) * (n / 10)).mapToInt(n -> (int)((long)n)).mapToObj(Color::new).toArray(Color[]::new);
            double doubleValue = number * 1000D;
            doubleValue = doubleValue * (doubleValue - 10) * (doubleValue / 10);
            number = (int)((long)doubleValue);
            g.setColor(new Color(number));
            g.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
        }
    }

}
