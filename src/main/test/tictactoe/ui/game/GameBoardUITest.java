package tictactoe.ui.game;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoardUITest {
    // A testing function
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameBoardUI b = new GameBoardUI(3, 3);
            System.out.println("Border created");

            b.addMouseListener(new MouseListener() {
                boolean turn = true;
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getPoint().x / (b.getWidth() / 3);
                    int y = e.getPoint().y / (b.getHeight() / 3);
                    if(e.getClickCount() > 1)
                        b.removeMark(x, y);
                    else if(turn = !turn)
                        b.markO(x, y);
                    else
                        b.markX(x, y);
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
