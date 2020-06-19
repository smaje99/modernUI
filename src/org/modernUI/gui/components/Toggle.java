package org.modernUI.gui.components;

import org.modernUI.gui.event.StatusEvent;
import org.modernUI.gui.event.StatusListener;
import org.modernUI.gui.tools.Colour;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Toggle extends JComponent {
    /**
     * Estado de modificación del Toggle
     */
    private boolean modificable;
    private String text;
    /**
     * Estado del Toggle
     */
    private boolean switchComponent;
    /**
     * Color del botón del Toggle
     */
    private Color buttonColor;
    /**
     * Color de fondo del Toggle
     */
    private Color backgroundColor;
    /**
     * Escucha del evento del {@link Toggle}
     */
    private StatusListener listener;
    /**
     * Color del Toggle deshabilitado
     */
    private final Color DISABLED_COMPONENT_COLOR;

    {
        DISABLED_COMPONENT_COLOR = Colour.GRAY_DISABLED.getColor();
    }

    /**
     * Componente Toggle Switch
     * @param text texto del componente
     */
    public Toggle(String text) {
        this(text, false);
    }

    /**
     * Componente Toggle Switch
     * @param text texto del componente
     * @param switchComponent estado del Toggle
     */
    public Toggle(String text, boolean switchComponent) {
        super();
        this.text = text;
        this.switchComponent = switchComponent;
        modificable = true;
        buttonColor = Color.WHITE;
        backgroundColor = Colour.GREEN_ACTIVE.getColor();
        listener = new StatusListener() {
            @Override
            public void trueState(StatusEvent e) { // None
            }

            @Override
            public void falseState(StatusEvent e) {  // None
            }
        };
        event();
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        setPreferredDimension();
        setForeground(Color.BLACK);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setVisible(true);
    }

    /**
     * Tamaño del componente
     */
    private void setPreferredDimension() {
        Dimension dim = new Dimension(35 + 1 + stringWidth(), 20);
        setSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
    }

    /**
     * Ancho del texto en el componente
     * @return ancho del texto
     */
    private int stringWidth() {
        return getFontMetrics(getFont()).stringWidth(text);
    }

    /**
     * Evento del Componente Toggle
     */
    private void event(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isModificable()) {
                    setSwitch(!isSwitch());
                    if (isSwitch()) listener.trueState((StatusEvent) e);
                    else listener.falseState((StatusEvent) e);
                }
            }
        });
    }

    /**
     * Estado de modificación del componente Toggle
     * @return Estado de modificación
     */
    public boolean isModificable() {
        return modificable;
    }

    /**
     * Modifica el estado de modificación del componente Toggle
     * @param modificable nuevo estado de modificación
     */
    public void setModificable(boolean modificable) {
        this.modificable = modificable;
        repaint();
    }

    /**
     * Estado del componente Toggle
     * @return estado del componente
     */
    public boolean isSwitch() {
        return switchComponent;
    }

    /**
     * Modifica el estado del componente Toggle
     * @param switchComponent nuevo estado del componente
     */
    public void setSwitch(boolean switchComponent) {
        this.switchComponent = switchComponent;
        repaint();
    }

    /**
     * Texto del componente Toggle
     * @return texto del componente
     */
    public String getText() {
        return text;
    }

    /**
     * Modifica el Texto del componente Toggle
     * @param text nuevo texto del componente
     */
    public void setText(String text) {
        this.text = text;
        setPreferredDimension();
        repaint();
    }

    /**
     * Color del botón del componente Toggle
     * @return color del botón del componente
     */
    public Color getButtonColor() {
        return buttonColor;
    }

    /**
     * Modifica el color del botón del componente Toggle
     * @param buttonColor nuevo color del botón del componente
     */
    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
        repaint();
    }

    /**
     * Color de fondo del componente Toggle
     * @return color de fondo del componente
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Modifica el color de fondo del componente Toggle
     * @param backgroundColor nuevo color de fondo del componente
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    /**
     * Escucha y contenedor de las acciones del evento
     *
     * @return escucha del evento
     */
    public StatusListener getListener() {
        return listener;
    }

    /**
     * Fija un nuevo escucha y acciones al {@link StatusListener} del componente {@link Toggle}
     *
     * @param listener nueva escucha del evento
     */
    public void setListener(StatusListener listener) {
        this.listener = listener;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        setPreferredDimension();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        opaque(g2);
        enable(g2);
        drawSwitchComponent(g2);
        drawText(g2);
    }

    /**
     * Aplica el Opaque si está habilitado al componente Toggle
     * @param g2 pincel
     */
    private void opaque(Graphics2D g2) {
        if (isOpaque()){
            g2.setColor(getBackground());
            g2.fill(new Rectangle2D.Double(
                    0, 0,
                    getWidth() + stringWidth(),
                    getHeight()
            ));
        }
    }

    /**
     * Aplica el estado de Enable al componente Toggle
     * @param g2 pincel
     */
    private void enable(Graphics2D g2) {
        g2.setColor(
                isEnabled() ? isSwitch() ?
                backgroundColor : Colour.WHITE_DISABLED.getColor() :
                DISABLED_COMPONENT_COLOR
        );
        RoundRectangle2D round = new RoundRectangle2D.Double(2, 2, 31, 16, 16, 16);
        if (isEnabled()) g2.fill(round);
        else g2.draw(round);
    }

    /**
     * Dibuja el botón del componente Toggle
     * @param g2 pincel
     */
    private void drawSwitchComponent(Graphics2D g2) {
        g2.setColor(isEnabled() ? buttonColor : DISABLED_COMPONENT_COLOR);
        g2.fillOval(isSwitch() ? 4 : 19, 4, 11, 11);
    }

    /**
     * Fija el texto en el componente Toggle
     * @param g2 pincel
     */
    private void drawText(Graphics2D g2) {
        g2.setFont(getFont());
        g2.setColor(getForeground());
        g2.drawString(text, 36, 15);
    }
}
