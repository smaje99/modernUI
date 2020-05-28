package org.modernUI.gui.components.editor;

import java.util.ArrayList;

public final class Code {
    private final StringBuilder codeStr;
    private final ArrayList<ArrayList<Object>> code;
    /**
     * Estilo <b>negrita</b> para el texto respectivo
     */
    public static final String BOLD;
    /**
     * Estilo <i>cursiva</i> para el texto respectivo
     */
    public static final String ITALIC;

    {
        codeStr = new StringBuilder();
    }

    static {
        BOLD = "bold";
        ITALIC = "italic";
    }

    /**
     * Construye el código contenido en ls listas
     * y especifica sus estilos respectivamente fijados
     *
     * @param code lista de lista con fragmentos de texto
     *             y estilos respectivamente fijados. El
     *             contenido de las listas internas debe de
     *             ser el siguiente:
     *             <ul>
     *                  <li>
     *                      Obligatorias:
     *                      <ol start=0>
     *                          <li>Fragmento de texto respectivo del código</li>
     *                          <li>Foreground {@link java.awt.Color} para el fragmento de texto</li>
     *                      </ol>
     *                  </li>
     *                  <li>
     *                      Opcionales:
     *                      <ul>
     *                          <li>Estilo {@link Code#BOLD}</li>
     *                          <li>Estilo {@link Code#ITALIC}</li>
     *                      </ul>
     *                  </li>
     *             </ul>
     */
    public Code(ArrayList<ArrayList<Object>> code) {
        this.code = code;
        buildCodeStr();
    }

    /**
     * Construye un {@link String} a partir de los fragmentos de texto del código
     */
    private void buildCodeStr() {
        code.forEach(e -> codeStr.append(e.get(0).toString()));
    }

    /**
     * {@link String} de los fragmentos de texto del código
     *
     * @return código
     */
    public String getCodeStr() {
        return codeStr.toString();
    }

    /**
     * lista de lista con fragmentos de texto
     * y estilos respectivamente fijados
     *
     * @return lista de código y especificaciones
     */
    public ArrayList<ArrayList<Object>> getCode() {
        return code;
    }
}
