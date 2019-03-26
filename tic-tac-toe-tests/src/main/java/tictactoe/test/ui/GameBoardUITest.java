package tictactoe.test.ui;

import tictactoe.connector.ui.listener.GamingStateUIListener;
import tictactoe.ui.game.GameBoardUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoardUITest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameBoardUI b = new GameBoardUI(3, 3);
            System.out.println("Border created");

            b.setListener(new GamingStateUIListener() {
                @Override
                public void tileClicked(int row, int col) {
                    System.out.println(String.format("Tile (%d,%d) was clicked", row, col));
                }

                @Override
                public void pauseGame() {
                    System.out.println("PAUSED :D");
                }
            });

            b.addMouseListener(new MouseListener() {
                boolean turn = true;
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getPoint().x / (b.getWidth() / 3);
                    int y = e.getPoint().y / (b.getHeight() / 3);
                    if(e.getClickCount() > 1)
                        b.removeMark(x, y);
                    else if(turn = !turn)
                        b.mark(0, x, y);
                    else
                        b.mark(1, x, y);
                    b.pack();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        });
    }
}
