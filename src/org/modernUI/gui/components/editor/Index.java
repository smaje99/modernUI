package org.modernUI.gui.components.editor;

import org.modernUI.gui.tools.Colour;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Index extends JPanel {
    private int minimumDisplayDigits;
    private int lineForeground;
    private int lastDigits;
    private int lastHeight;
    private final Insets insets;
    private final JTextComponent component;
    private final HashMap<Integer, Integer> lineNumber;
    private final HashMap<String, FontMetrics> fonts;
    private static final int HEIGHT;

    {
        fonts = new HashMap<>();
        insets = new Insets(0, 15, 0, 7);
        lineNumber = new HashMap<>();
    }

    static {
        HEIGHT = Integer.MAX_VALUE - 1000000;
    }

    /**
     * Componente Index
     * Al combinarlo a un componente de Texto mostrará el
     * número respectivo de las líneas de ese componente
     *
     * @param component componente de Texto a combinar
     */
    public Index(JTextComponent component) {
        this(component, 1);
    }

    /**
     * Componente Index
     * Al combinarlo a un componente de Texto mostrará el
     * número respectivo de las líneas de ese componente
     *
     * @param component            componente de Texto a combinar
     * @param minimumDisplayDigits número mínimo de dígitos de las
     *                             líneas del componente de Texto
     */
    private Index(JTextComponent component, int minimumDisplayDigits) {
        this.component = component;
        lastDigits = 0;
        lineForeground = -1;
        setFont(component.getFont());
        setBackground(Colour.INDEX.getColor());
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 0));
        setPreferredWidth();
        setMinimumDisplayDigits(minimumDisplayDigits);
        eventDocument();
    }

    /**
     * Fija el ancho del componente según el número de
     * dígitos en la última línea del {@link Index#component}
     */
    private void setPreferredWidth() {
        int digits = Math.max(
                numberOfDigits(getRoot().getElementCount()),
                minimumDisplayDigits
        );
        if (lastDigits != digits) {
            lastDigits = digits;
            Dimension dim = getPreferredSize();
            dim.setSize(
                    insets.left + insets.right + getMetrics().charWidth('0') * digits,
                    HEIGHT
            );
            setPreferredSize(dim);
            setMinimumSize(dim);
            setSize(dim);
        }
    }

    /**
     * Determina el número de dígitos que hay en un número respectivo
     *
     * @param number número a evaluar
     * @return cantidad de dígitos del número evaluado
     */
    private int numberOfDigits(int number) {
        return (int) (Math.log10(number) + 1);
    }

    /**
     * {@link FontMetrics} de {@link Index#component}
     *
     * @return {@link FontMetrics} con el parámetro del {@link Font} de {@link Index#component}
     */
    private FontMetrics getMetrics() {
        return component.getFontMetrics(getFont());
    }

    /**
     * Fija el mínimo número de dígitos y re-dimensiona el componente
     *
     * @param minimumDisplayDigits nuevo mínimo de dígitos
     */
    private void setMinimumDisplayDigits(int minimumDisplayDigits) {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    /**
     * {@link DocumentEvent} del {@link Index#component}
     */
    private void eventDocument() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentChanged();
            }
        });
    }

    /**
     * Acción a ejecutar en {@link DocumentEvent} del {@link Index#component}
     */
    private void documentChanged() {
        SwingUtilities.invokeLater(() -> {
            try {
                Rectangle rectangle = (Rectangle) component
                        .modelToView2D(getDocument().getLength());
                if (Optional.ofNullable(rectangle).isPresent() && rectangle.y != lastHeight) {
                    setPreferredWidth();
                    repaint();
                    lastHeight = rectangle.y;
                }
            } catch (BadLocationException e) {  // None
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Colour.SCROLL_ROLLOVER.getColor());
        graphics.drawLine(getWidth() - 1, getY(), getWidth() - 1, getHeight());
        Rectangle clip = graphics.getClipBounds();
        paintNumbers(
                component.viewToModel2D(new Point(0, clip.y)),
                component.viewToModel2D(new Point(0, clip.y + clip.height)),
                graphics
        );
    }

    /**
     * Dibuja los indices respectivos de las líneas disponibles en {@link Index#component}
     *
     * @param rowStartOffset posición inicial de las líneas del {@link Index#component}
     * @param endOffset      posición final de las líneas del {@link Index#component}
     * @param graphics       pincel
     */
    private void paintNumbers(int rowStartOffset, int endOffset, Graphics graphics) {
        AtomicInteger rowStart = new AtomicInteger(rowStartOffset);
        Integer number;
        String numberStr;
        int y;
        while (rowStart.get() <= endOffset) {
            try {
                number = formatNumber(getTextLineNumber(rowStart.get()));
                numberStr = number.toString();
                y = getOffsetY(rowStartOffset);
                lineNumber.put(number, y);
                graphics.setColor(number == lineForeground ? Colour.LINE_FOREGROUND.getColor() : Color.WHITE);
                graphics.drawString(numberStr, getOffsetX(getMetrics().stringWidth(numberStr)), y);
                rowStart.set(Utilities.getRowEnd(component, rowStart.get() + 1));
            } catch (BadLocationException e) {
                break;
            }
        }
    }

    /**
     * Obtiene el número de la línea consecutiva a ser dibujada
     *
     * @param rowStartOffset posición actual de la línea del
     *                       {@link Index#component} que se está dibujando
     * @return número de línea a dibujar
     */
    private String getTextLineNumber(int rowStartOffset) {
        Element root = getRoot();
        int index = root.getElementIndex(rowStartOffset);
        return root.getElement(index).getStartOffset() == rowStartOffset ?
                String.valueOf(index + 1) : "";
    }

    /**
     * Se aplica el formato al número para que pueda ser dibujado limpiamente
     *
     * @param number número de fila obtenido
     * @return número con formato a ser dibujado
     */
    private Integer formatNumber(String number) {
        return number.isBlank() ? -1 : Integer.parseInt(number);
    }

    /**
     * Obtiene la posición de una línea respectiva en el {@link Index#component}
     *
     * @param rowStartOffset posición de la línea respectiva en el {@link Index#component}
     * @return posición en Y para dibujar el número de línea respectivo
     * @throws BadLocationException posición fuera de rango
     */
    private int getOffsetY(int rowStartOffset) throws BadLocationException {
        Rectangle rectangle = (Rectangle) component.modelToView2D(rowStartOffset);
        int lineHeight = getMetrics().getHeight();
        int y = rectangle.y + rectangle.height;
        int descent = 0;
        if (rectangle.height == lineHeight) descent = getMetrics().getDescent();
        else {
            Element root = getRoot();
            Element line = root.getElement(root.getElementIndex(rowStartOffset));
            for (int i = 0; i < line.getElementCount(); i++)
                descent = Math.max(
                        descent,
                        getDescentChild(line.getElement(i))
                );
        }
        return y - descent;
    }

    /**
     * Posición en X para dibujar un número de línea respectivo
     *
     * @param strWidth ancho del número a ser dibujado
     * @return posición en X para dibujar el número de línea respectivo
     */
    private int getOffsetX(int strWidth) {
        return getSize().width - insets.right - strWidth;
    }

    /**
     * {@link Element} por defecto del {@link Document} del {@link Index#component}
     *
     * @return {@link Element} por defecto del {@link Document}
     */
    private Element getRoot() {
        return getDocument().getDefaultRootElement();
    }

    /**
     * {@link Document} del {@link Index#component}
     *
     * @return {@link Document} del {@link Index#component}
     */
    private Document getDocument() {
        return component.getDocument();
    }

    /**
     * {@link FontMetrics#getDescent()} del {@link Font} de una línea
     * respectiva encontrada en un {@link Element} hijo
     *
     * @param child {@link Element} hijo
     * @return {@link FontMetrics#getDescent()}
     */
    private int getDescentChild(Element child) {
        return getFontMetrics(child.getAttributes()).getDescent();
    }

    /**
     * {@link FontMetrics} de un {@link Font} encontrada en un {@link AttributeSet}
     *
     * @param as {@link AttributeSet} que contiene el {@link Font} necesitado
     * @return {@link FontMetrics}
     */
    private FontMetrics getFontMetrics(AttributeSet as) {
        String key = getKey(as);
        return Optional.ofNullable(fonts.get(key))
                .orElseGet(() -> registerFontMetrics(as, key));
    }

    /**
     * Key de un {@link FontMetrics} guardado en {@link Index#fonts}
     *
     * @param as {@link AttributeSet} que contiene el {@link Font} necesitado
     *           para generar el {@link FontMetrics}
     * @return Key de un {@link FontMetrics} en {@link Index#fonts}
     */
    private String getKey(AttributeSet as) {
        return StyleConstants.getFontFamily(as) + StyleConstants.getFontSize(as);
    }

    /**
     * Registra un nuevo {@link FontMetrics} en {@link Index#fonts}
     *
     * @param as  {@link AttributeSet} que contiene el {@link Font} necesitado
     *            para generar el {@link FontMetrics}
     * @param key key con la que se va a guardar el nuevo {@link FontMetrics}
     *            en {@link Index#fonts}
     * @return {@link FontMetrics} registrado en {@link Index#fonts}
     */
    private FontMetrics registerFontMetrics(AttributeSet as, String key) {
        FontMetrics aux = component.getFontMetrics(new Font(
                StyleConstants.getFontFamily(as),
                Font.PLAIN,
                StyleConstants.getFontSize(as))
        );
        fonts.put(key, aux);
        return aux;
    }

    /**
     * Fija la línea actual en la que se encuentra el
     * componente de Texto combinado con este componente
     *
     * @param lineSelected línea a resaltar en el componente
     */
    public void lineForegroundIn(int lineSelected) {
        lineForeground = lineSelected;
        repaint();
    }

    /**
     * Contiene las ubicaciones exactas en pixeles de las posiciones en Y
     * de cada uno de los números de línea existentes
     *
     * @return ubicaciones de los números de línea en el componente
     */
    public HashMap<Integer, Integer> getLineNumber() {
        return lineNumber;
    }
}
