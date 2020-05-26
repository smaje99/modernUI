package org.modernUI.gui.border;

import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public final class RoundBorder implements Border {
    /**
     * Radio del Borde
     */
    private final int radius;
    /**
     * Color del Borde
     */
    private Color color;

    /**
     * RoundBorder con radio 6 y color negro
     */
    public RoundBorder() {
        this(6);
    }

    /**
     * RoundBorder con color negro
     * @param radius radio del Borde
     */
    public RoundBorder(int radius) {
        this(radius, Color.BLACK);
    }

    /**
     * RoundBorder
     * @param radius radio del Borde
     * @param color color del Borde
     */
    public RoundBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(color);
        g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius + 1, radius + 2, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
