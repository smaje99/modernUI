package org.modernUI.gui.model;

import javax.swing.table.DefaultTableModel;

public class ObjectTableModel extends DefaultTableModel {
    private Class<?> objectClass;

    public ObjectTableModel(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return objectClass;
    }
}
