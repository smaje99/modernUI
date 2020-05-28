package org.modernUI.gui.components.editor;

import org.modernUI.gui.components.TextPane;
import org.modernUI.gui.tools.Colour;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class View extends TextPane {
    private boolean line;
    private int linePixelSelected;

    /**
     * Vista del Editor, con la funcionalidad de
     * seleccionar una fila del componente
     */
    public View() {
        super(false);
        setBackground(Colour.EDITOR.getColor());
        line = false;
    }

    /**
     * Dibujo de la línea
     *
     * @return true: Se dibuja false: No se dibuja
     */
    public boolean isLine() {
        return line;
    }

    /**
     * Fija si la línea se dibuja
     *
     * @param line true: Se dibuja false: No se dibuja
     */
    public void setDrawLine(boolean line) {
        this.line = line;
        repaint();
    }

    /**
     * Selecciona una línea determinada a dibujar
     *
     * @param linePixelSelected línea a seleccionar
     */
    protected void drawLineIn(int linePixelSelected) {
        this.linePixelSelected = linePixelSelected;
        line = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (line) drawLine((Graphics2D) g);
    }

    /**
     * Según el estado de {@link View#isLine()} dibuja la línea en el píxel seleccionado
     *
     * @param g2 pincel
     */
    private void drawLine(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Colour.LINE_SELECTED.getColor());
        g2.fill(new RoundRectangle2D.Double(
                getInsets().left - 3,
                pixelOfLinePixelSelected(g2),
                widthLine(),
                heightLine(g2),
                16, 16
        ));
    }

    /**
     * Ubicación en Y de la línea seleccionada a dibujar
     *
     * @param g2 pincel
     * @return ubicación exacta en Y de la línea
     */
    private int pixelOfLinePixelSelected(Graphics2D g2) {
        return linePixelSelected - getMetrics(g2).getHeight() + 3;
    }

    /**
     * Ancho de la línea seleccionada
     *
     * @return ancho exacto de línea
     */
    private int widthLine() {
        return getWidth() - getInsets().left - 3;
    }

    /**
     * Altura de línea seleccionada
     *
     * @param g2 pincel
     * @return altura exacta de línea
     */
    private int heightLine(Graphics2D g2) {
        return getMetrics(g2).getHeight() + 1;
    }

    /**
     * {@link FontMetrics} del pincel del componente {@link View}
     *
     * @param g2 pincel
     * @return {@link FontMetrics}
     */
    private FontMetrics getMetrics(Graphics2D g2) {
        return g2.getFontMetrics(getFont());
    }
}
