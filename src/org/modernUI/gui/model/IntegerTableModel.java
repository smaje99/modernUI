package org.modernUI.gui.model;

import javax.swing.table.DefaultTableModel;

public class IntegerTableModel extends DefaultTableModel {
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }
}
