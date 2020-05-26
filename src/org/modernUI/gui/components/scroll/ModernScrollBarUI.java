package org.modernUI.gui.components.scroll;

import org.modernUI.gui.tools.Colour;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ModernScrollBarUI extends BasicScrollBarUI {
    private static final int THUMB_SIZE;
    private final JScrollPane scroll;
    private Rectangle thumbBounds;

    static {
        THUMB_SIZE = 8;
    }

    /**
     * Interfaz personalizada al {@link JScrollBar}
     * @param scroll {@link JScrollPane} a personalizar
     */
    protected ModernScrollBarUI(final JScrollPane scroll) {
        this.scroll = scroll;
    }

    /**
     * Botón invisible para los ScrollbarButton
     * @return {@link JButton} invisible
     */
    private JButton invisibleScrollBarButton() {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    private boolean isVertical() {
        return scrollbar.getOrientation() == JScrollBar.VERTICAL;
    }

    private Color getColor() {
        return isDragging ? Colour.SCROLL_DRAGGING.getColor() :
                isThumbRollover() ? Colour.SCROLL_ROLLOVER.getColor() :
                        Colour.SCROLL_PRESSED.getColor();
    }

    private int getX() {
        return thumbBounds.x;
    }

    private int getY() {
        return thumbBounds.y;
    }

    private int getWidth() {
        return Math.max(
                isVertical() ? THUMB_SIZE : thumbBounds.width,
                THUMB_SIZE
        );
    }

    private int getHeight() {
        return Math.max(
                isVertical() ? thumbBounds.height : THUMB_SIZE,
                THUMB_SIZE
        );
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return invisibleScrollBarButton();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return invisibleScrollBarButton();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (c.isEnabled()) {
            this.thumbBounds = thumbBounds;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(getColor());
            g2.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
            g2.setColor(Colour.SCROLL_BORDER.getColor());
            g2.drawRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
            g2.dispose();
        }
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scroll.repaint();
    }
}
