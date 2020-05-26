package org.modernUI.gui.tools;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public final class CreateFont {
    /**
     * Fuente a ser creada
     */
    private final Font font;
    /**
     * Formato de la fuente a ser creada
     */
    private final int fontFormat;

    /**
     * Crea una fuente tipográfica TrueTypeFont
     *
     * @param path ruta del archivo de la fuente
     */
    public CreateFont(String path) {
        this(path, Font.TRUETYPE_FONT);
    }

    /**
     * Crea una fuente tipográfica
     *
     * @param path       ruta del archivo de la fuente
     * @param fontFormat formato de fuente
     */
    public CreateFont(String path, int fontFormat) {
        this.fontFormat = fontFormat;
        font = createFont(path);
    }

    /**
     * Crea la fuente<br>
     * En el caso de encontrar o no poder crear la fuente indicada, devolverá una fuente por defecto de Java
     *
     * @param path ruta del archivo de la fuente
     * @return fuente creada
     */
    private Font createFont(String path) {
        try {
            return Font.createFont(fontFormat, getClass().getResourceAsStream(path));
        } catch (FontFormatException | IOException e) {
            System.err.println("Font not found!!");
            return new Font(Font.MONOSPACED, Font.PLAIN, 10);
        }
    }

    /**
     * Registra la fuente tipográfica temporalmente
     */
    private void registerFont() {
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
    }

    /**
     * Formato de la fuente creada
     *
     * @return formato de fuente
     */
    public int getFontFormat() {
        return fontFormat;
    }

    /**
     * Obtiene la Fuente creada y la convierte a {@link Font} con el estilo y tamaño deseado
     *
     * @param style estilo deseado según los indicados en {@link Font}
     *              <ul>
     *               <li>{@link Font#PLAIN}</li>
     *               <li>{@link Font#BOLD}</li>
     *               <li>{@link Font#ITALIC}</li>
     *               <li>La suma de cualquiera de las anteriores</li>
     *              </ul>
     * @param size  tamaño deseado de la fuente
     * @return {@link Font} con el estilo y tamaño deseado
     */
    public Font getFont(int style, int size) {
        return font.deriveFont(style, size);
    }
}
