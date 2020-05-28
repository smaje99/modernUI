package org.modernUI.gui.components.editor;

import org.modernUI.gui.components.scroll.ModernScrollPane;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class Editor extends ModernScrollPane {
    /**
     * Código a implementar en el {@link Editor}
     */
    private final Code code;

    /**
     * Editor estático para código
     *
     * @param view  {@link View} del código
     * @param index {@link Index} del número de líneas
     * @param code  código a implementar en el {@link Editor}
     */
    private Editor(View view, Index index, Code code) {
        super(view, index);
        this.code = code;
        defragment();
    }

    /**
     * Desfragmenta cada una de las listas de la lista principal de {@link Code}
     */
    private void defragment() {
        try {
            code.getCode().forEach(this::split);
        } catch (NullPointerException e) {
            setText("No Code!!");
        }
    }

    /**
     * Aplica las especificaciones del fragmento respectivamente especificado
     *
     * @param fragment sublista
     */
    private void split(ArrayList<Object> fragment) {
        String auxFragment = fragment.get(0).toString().replaceAll("\t", "   ");
        Color auxColor = (Color) fragment.get(1);
        if (fragment.size() == 2) append(auxFragment, auxColor);
        else if (fragment.size() == 4) append(auxFragment, auxColor, true, true);
        else {
            boolean isBold = fragment.get(3).toString().equals(Code.BOLD);
            append(auxFragment, auxColor, isBold, !isBold);
        }
    }

    /**
     * Añade texto con sus especificaciones respectivas
     *
     * @param fragment fragmento del código
     * @param color    {@link Color} de estilo especificado
     */
    private void append(String fragment, Color color) {
        append(fragment, color, false, false);
    }

    /**
     * Añade texto con sus especificaciones respectivas
     *
     * @param fragment fragmento del código
     * @param color    {@link Color} de estilo especificado
     * @param isBold   confirma si la especificación del {@link Font} incluye <b>negrita</b>
     * @param isItalic confirma si la especificación del {@link Font} incluye <i>italica</i>
     */
    private void append(String fragment, Color color, boolean isBold, boolean isItalic) {
        View view = getView();
        Font auxFont = view.getFontText();
        Font newFont = auxFont.deriveFont(getFontStyle(isBold, isItalic));
        if (auxFont.getStyle() != newFont.getStyle()) view.setFontText(newFont);
        view.setForeground(color);
        view.append(fragment);
    }

    /**
     * Fija un texto en {@link View} con un foreground blanco
     *
     * @param text texto a fijar
     */
    public void setText(String text) {
        append(text, Color.WHITE);
    }

    /**
     * Determina el estilo de un {@link Font} según sus parametros
     *
     * @param isBold   posibilidad de ser <b>negrita</b>
     * @param isItalic posibilidad de set <i>italica</i>
     * @return estilo de {@link Font}
     */
    private int getFontStyle(boolean isBold, boolean isItalic) {
        return isBold && isItalic ? Font.BOLD + Font.ITALIC :
                isBold ? Font.BOLD : isItalic ?
                        Font.ITALIC : Font.PLAIN;
    }

    /**
     * Selecciona una línea especifica en el {@link Editor}
     *
     * @param numberOfLine número de línea a seleccionar
     */
    public void selectLine(int numberOfLine) {
        Index index = getIndex();
        index.lineForegroundIn(numberOfLine);
        getView().drawLineIn(index.getLineNumber().get(numberOfLine));
    }

    /**
     * Fija un estado de dibujo a la línea seleccionada
     *
     * @param draw estado de dibujo
     */
    public void drawLineSelected(boolean draw) {
        getView().setDrawLine(draw);
        getIndex().lineForegroundIn(-1);
    }

    /**
     * {@link View} del código
     *
     * @return Vista el código
     */
    @Override
    public View getView() {
        return (View) super.getView();
    }

    /**
     * {@link Index} del número de línea
     *
     * @return índice del número de línea
     */
    public Index getIndex() {
        return (Index) getRowHeaderView();
    }

    /**
     * Código implementado en el {@link Editor}
     *
     * @return código implementado
     */
    public Code getCode() {
        return code;
    }

    /**
     * Editor estático para código
     *
     * @param code código a implementar en el {@link Editor}
     * @return {@link Editor} configurado por defecto y listo para ser usado
     */
    public static Editor editor(Code code) {
        View view = new View();
        return new Editor(view, new Index(view), code);
    }
}
