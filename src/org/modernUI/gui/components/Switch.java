package org.modernUI.gui.components;

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
import java.util.function.Consumer;

public class Switch extends JComponent {
    /**
     * Estado de modificación del Switch
     */
    private boolean modificable;
    private String text;
    /**
     * Estado del Switch
     */
    private boolean switchComponent;
    /**
     * Color del botón del Switch
     */
    private Color buttonColor;
    /**
     * Color de fondo del Switch
     */
    private Color backgroundColor;
    /**
     * Evento del Switch cuando se habilita
     */
    private Consumer<MouseEvent> eventSwitchTrue;
    /**
     * Evento del Switch cuando se deshabilita
     */
    private Consumer<MouseEvent> eventSwitchFalse;
    /**
     * Color del Switch deshabilitado
     */
    private final Color DISABLED_COMPONENT_COLOR;

    {
        DISABLED_COMPONENT_COLOR = Colour.GRAY_DISABLED.getColor();
    }

    /**
     * Componente Switch
     * @param text texto del componente
     */
    public Switch(String text) {
        this(text, false);
    }

    /**
     * Componente Switch
     * @param text texto del componente
     * @param switchComponent estado del Switch
     */
    public Switch(String text, boolean switchComponent) {
        super();
        this.text = text;
        this.switchComponent = switchComponent;
        modificable = true;
        buttonColor = Color.WHITE;
        backgroundColor = Colour.GREEN_ACTIVE.getColor();
        eventSwitchTrue = e -> {};
        eventSwitchFalse = e -> {};
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
     * Evento del Componente Switch
     */
    private void event(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isModificable()) {
                    if (isSwitch()) eventSwitchTrue.accept(e);
                    else eventSwitchFalse.accept(e);
                    setSwitch(!isSwitch());
                    repaint();
                }
            }
        });
    }

    /**
     * Estado de modificación del componente Switch
     * @return Estado de modificación
     */
    public boolean isModificable() {
        return modificable;
    }

    /**
     * Modifica el estado de modificación del componente Switch
     * @param modificable nuevo estado de modificación
     */
    public void setModificable(boolean modificable) {
        this.modificable = modificable;
        repaint();
    }

    /**
     * Estado del componente Switch
     * @return estado del componente
     */
    public boolean isSwitch() {
        return switchComponent;
    }

    /**
     * Modifica el estado del componente Switch
     * @param switchComponent nuevo estado del componente
     */
    public void setSwitch(boolean switchComponent) {
        this.switchComponent = switchComponent;
        repaint();
    }

    /**
     * Texto del componente Switch
     * @return texto del componente
     */
    public String getText() {
        return text;
    }

    /**
     * Modifica el Texto del componente Switch
     * @param text nuevo texto del componente
     */
    public void setText(String text) {
        this.text = text;
        setPreferredDimension();
        repaint();
    }

    /**
     * Color del botón del componente Switch
     * @return color del botón del componente
     */
    public Color getButtonColor() {
        return buttonColor;
    }

    /**
     * Modifica el color del botón del componente Switch
     * @param buttonColor nuevo color del botón del componente
     */
    public void setButtonColor(Color buttonColor) {
        this.buttonColor = buttonColor;
        repaint();
    }

    /**
     * Color de fondo del componente Switch
     * @return color de fondo del componente
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Modifica el color de fondo del componente Switch
     * @param backgroundColor nuevo color de fondo del componente
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    /**
     * Evento del componente Switch cuando se habilita
     * @return evento del componente cuando se habilita
     */
    public Consumer<MouseEvent> getEventSwitchTrue() {
        return eventSwitchTrue;
    }

    /**
     * Modifica el evento del componente Switch cuando se habilita
     * @param eventSwitchTrue nuevo evento
     */
    public void setEventSwitchTrue(Consumer<MouseEvent> eventSwitchTrue) {
        this.eventSwitchTrue = eventSwitchTrue;
    }

    /**
     * Evento del componente Switch cuando de deshabilita
     * @return evento del componente cuando se deshabilita
     */
    public Consumer<MouseEvent> getEventSwitchFalse() {
        return eventSwitchFalse;
    }

    /**
     * Modifica el evento del componente Switch cuando se deshabilita
     * @param eventSwitchFalse nuevo evento
     */
    public void setEventSwitchFalse(Consumer<MouseEvent> eventSwitchFalse) {
        this.eventSwitchFalse = eventSwitchFalse;
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
     * Aplica el Opaque si está habilitado al componente Switch
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
     * Aplica el estado de Enable al componente Switch
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
     * Dibuja el botón del componente Switch
     * @param g2 pincel
     */
    private void drawSwitchComponent(Graphics2D g2) {
        g2.setColor(isEnabled() ? buttonColor : DISABLED_COMPONENT_COLOR);
        g2.fillOval(isSwitch() ? 4 : 19, 4, 11, 11);
    }

    /**
     * Fija el texto en el componente Switch
     * @param g2 pincel
     */
    private void drawText(Graphics2D g2) {
        g2.setFont(getFont());
        g2.setColor(getForeground());
        g2.drawString(text, 36, 15);
    }
}
