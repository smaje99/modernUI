package org.modernUI.gui.components.tooltip;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

public class CustomToolTip extends JToolTip {
    protected int columns;
    protected int fixedWidth;
    private ComponentUI componentUI;

    public CustomToolTip() {
        columns = 0;
        fixedWidth = 0;
    }

    @Override
    public void updateUI() {
        setUI(componentUI);
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
        this.fixedWidth = 0;
    }

    public int getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
        this.columns = 0;
    }

    /**
     * Establece la UI a utilizar en el {@link JToolTip}
     * y la implementa dentro del componente
     *
     * @param componentUI UI del {@link JToolTip}
     */
    public void setComponentUI(ComponentUI componentUI) {
        this.componentUI = componentUI;
        updateUI();
    }
}
