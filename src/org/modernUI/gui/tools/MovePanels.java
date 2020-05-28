package org.modernUI.gui.tools;

import javax.swing.*;
import java.awt.*;

public class MovePanels {
    /**
     * Panel padre, contenedor de los múltiples paneles disponibles a visualizar
     */
    private static JPanel rootPanel;

    /**
     * Fija el panel padre, el cual contiene los múltiples paneles disponibles a visualizar
     *
     * @param rootPanel panel padre
     */
    public static void setRootPanel(JPanel rootPanel) {
        MovePanels.rootPanel = rootPanel;
    }

    /**
     * Despliega la visualización del panel hijo seleccionado,
     * el cual se encuentra disponible en el panel padre
     *
     * @param childPanel panel hijo a visualizar
     */
    public static void show(String childPanel) {
        ((CardLayout) MovePanels.rootPanel.getLayout())
                .show(MovePanels.rootPanel, childPanel);
    }
}
