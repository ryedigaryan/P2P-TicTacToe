package tictactoe.ui.game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class TileUI extends JComponent {
    private static final boolean MARKER_O = true;
    private static final boolean MARKER_X = false;
    private static final Dimension PREFERRED_SIZE = new Dimension(100, 100);

    private Boolean tileValue;

    public TileUI() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
        if(getWidth() < PREFERRED_SIZE.getWidth() || getHeight() < PREFERRED_SIZE.getHeight()) {
            return PREFERRED_SIZE;
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
