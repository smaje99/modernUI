package org.modernUI.gui.components.tooltip;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;
import java.awt.Dimension;
import java.awt.Graphics;

public class MultiLineToolTipUI extends BasicToolTipUI {
    protected CellRendererPane rendererPane;
    private JToolTip toolTip;
    private JTextArea textArea;
    private static final MultiLineToolTipUI shareInstance;

    static {
        shareInstance = new MultiLineToolTipUI();
    }

    public static ComponentUI createUI(JComponent c) {
        return shareInstance;
    }

    public MultiLineToolTipUI() {
        super();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        toolTip = (JToolTip) c;
        rendererPane = new CellRendererPane();
        c.add(rendererPane);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.remove(rendererPane);
        rendererPane = null;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Dimension size = c.getSize();
        textArea.setBackground(c.getBackground());
        rendererPane.paintComponent(
                g, textArea, c,
                1, 1,
                size.width - 1,
                size.height - 1,
                true
        );
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        String tipText = ((JToolTip) c).getTipText();
        if (tipText == null) return new Dimension(0, 0);
        textArea = new JTextArea(tipText);
        rendererPane.removeAll();
        rendererPane.add(textArea);
        textArea.setWrapStyleWord(true);
        int width = ((CustomToolTip) c).getFixedWidth();
        int columns = ((CustomToolTip) c).getColumns();
        if (columns > 0) {
            textArea.setColumns(columns);
            textArea.setSize(0, 0);
            textArea.setLineWrap(true);
            textArea.setSize(textArea.getPreferredSize());
        } else if (width > 0) {
            textArea.setLineWrap(true);
            Dimension d = textArea.getPreferredSize();
            d.width = width;
            d.height++;
            textArea.setSize(d);
        } else textArea.setLineWrap(false);
        Dimension dim = textArea.getPreferredSize();
        dim.height++;
        dim.width++;
        return dim;
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        return getPreferredSize(c);
    }

    @Override
    public Dimension getMinimumSize(JComponent c) {
        return getPreferredSize(c);
    }
}
