package org.modernUI.gui.components.tooltip;

import javax.swing.*;

public class JMultiLineToolTip extends JToolTip {
    protected int columns;
    protected int fixedWidth;

    public JMultiLineToolTip() {
        columns = 0;
        fixedWidth = 0;
        updateUI();
    }

    @Override
    public void updateUI() {
        setUI(MultiLineToolTipUI.createUI(this));
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
}
