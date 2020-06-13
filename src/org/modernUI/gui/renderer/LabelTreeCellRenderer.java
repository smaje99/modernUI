package org.modernUI.gui.renderer;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Component;

public class LabelTreeCellRenderer extends DefaultTreeCellRenderer {
    private final JLabel label;

    public LabelTreeCellRenderer() {
        this(new JLabel("", JLabel.LEFT));
    }

    public LabelTreeCellRenderer(JLabel label) {
        this.label= label;
    }

    public JLabel getLabel() {
        return label;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        return label;
    }
}
