package org.modernUI.gui.components;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Insets;

public class TextPane extends JTextPane {
    /**
     * {@link Font} del texto que se está añadiendo al componente {@link TextPane}
     */
    private Font fontText;
    /**
     * {@link Color} del texto que se está añadiendo al componente {@link TextPane}
     */
    private Color foreText;
    /**
     * Sabe si {@link TextPane#getFontText()} ha cambiado de estado
     */
    private boolean setFontText;
    /**
     * Sabe si {@link TextPane#getForeText()} ha cambiado de estado
     */
    private boolean setForeText;
    /**
     * Estado de trabajo del componente {@link TextPane}
     * <ul>
     *     <li>true: un simple {@link JTextPane} de texto plano</li>
     *     <li>false: un {@link JTextPane} con estilos de {@link Font}s, {@link Color}s y margenes</li>
     * </ul>
     */
    private final boolean normal;

    /**
     * Componente {@link TextPane}
     *
     * @param normal estado de trabajo del componente {@link TextPane}
     *                <ul>
     *                    <li>true: un simple {@link JTextPane} de texto plano</li>
     *                    <li>false: un {@link JTextPane} con estilos de {@link Font}s, {@link Color}s y margenes</li>
     *               </ul>
     * @see JTextPane
     */
    public TextPane(boolean normal) {
        this.normal = normal;
        setContentType("text/plain");
        setEditable(false);
        if (!normal) {
            setMargin(new Insets(0, 10, 5, 39));
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
        fontText = getFont();
        foreText = getForeground();
        setTextFalse();
    }

    /**
     * Cambia los estados de modificación de {@link TextPane#getFontText()} y {@link TextPane#getForeText()} a false
     */
    private void setTextFalse() {
        setFontText = setForeText = false;
    }

    /**
     * {@link Color} predeterminado del texto que se añade al componente {@link TextPane}
     *
     * @return {@link Color} del texto
     */
    public Color getForeText() {
        return foreText;
    }

    /**
     * Modifica el {@link Color} predeterminado del texto que se añade al componente {@link TextPane}
     *
     * @param foreText nuevo {@link Color} del texto
     */
    public void setForeText(Color foreText) {
        this.foreText = foreText;
        setForeText = true;
    }

    /**
     * {@link Font} predeterminado del texto que se añade al componente {@link TextPane}
     *
     * @return {@link Font} del texto
     */
    public Font getFontText() {
        return fontText;
    }

    /**
     * Modifica el {@link Font} predeterminado del texto que se añade al componente {@link TextPane}
     *
     * @param fontText nuevo {@link Font} del texto
     */
    public void setFontText(Font fontText) {
        this.fontText = fontText;
        setFontText = true;
    }

    /**
     * Estado de trabajo del componente {@link TextPane}
     * <ul>
     *     <li>true: un simple {@link JTextPane} de texto plano</li>
     *     <li>false: un {@link JTextPane} con estilos de {@link Font}s, {@link Color}s y margenes</li>
     * </ul>
     */
    public boolean isNormal() {
        return normal;
    }

    /**
     * Añade texto al componente {@link TextPane} con los estilos
     * por defecto de {@link TextPane#getFontText()} y {@link TextPane#getForeText()}
     *
     * @param text texto a añadir
     */
    public void append(String text) {
        if (!normal) {
            Style set = getStyle();
            styleFore(set);
            styleFont(set);
            append(set, text);
            setTextFalse();
        }
    }

    /**
     * {@link Style} actual del componente {@link TextPane}
     *
     * @return {@link Style} del componente
     */
    private Style getStyle() {
        StyledDocument document = getStyledDocument();
        Style setDefault = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        document.addStyle("", setDefault);
        return setDefault;
    }

    /**
     * Añade el {@link Style} de {@link Font} predeterminado en {@link TextPane#getFontText()}
     *
     * @param style {@link Style} del componente
     */
    private void styleFont(Style style) {
        if (setFontText) {
            StyleConstants.setFontFamily(style, fontText.getFamily());
            StyleConstants.setFontSize(style, fontText.getSize());
            StyleConstants.setBold(style, isStyleFontText(Font.BOLD));
            StyleConstants.setItalic(style, isStyleFontText(Font.ITALIC));
        }
    }

    /**
     * Añade el {@link Style} de {@link Color} predeterminado en {@link TextPane#getForeText()}
     *
     * @param style {@link Style} del componente
     */
    private void styleFore(Style style) {
        if (setForeText) StyleConstants.setForeground(style, foreText);
    }

    /**
     * Retorna <code>true</code> si el estilo de {@link Font} es <b>BOLD</b>,
     * <i>ITALIC</i> o la suma de las dos
     *
     * @param style estilo de {@link Font} a comparar
     * @return si el estilo pertenece a algunas de a categorías anteriores o no
     */
    private boolean isStyleFontText(int style) {
        return style == fontText.getStyle() || Font.BOLD + Font.ITALIC == fontText.getStyle();
    }

    /**
     * Añade texto al componente {@link TextPane} con {@link Style} personalizado
     *
     * @param set  {@link Style} personalizado
     * @param text texto a añadir
     */
    private void append(AttributeSet set, String text) {
        setEditable(true);
        setCharacterAttributes(set, false);
        setCaretPosition(getStyledDocument().getLength());
        replaceSelection(text);
        setCaretPosition(getStyledDocument().getLength());
        setEditable(false);
    }
}
