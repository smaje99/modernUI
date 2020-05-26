package org.modernUI.gui.components.scroll;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ModernScrollPane extends JScrollPane {
    private static final int SB_SIZE;

    static {
        SB_SIZE = 10;
    }

    /**
     * {@link JScrollPane} modernizado
     * @param view componente a integrar al {@link JViewport}
     */
    public ModernScrollPane(Component view) {
        this(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * {@link JScrollPane} modernizado
     * @param view componente a integrar al {@link JViewport}
     * @param rowHeaderView componente a integrar al Row Header
     */
    public ModernScrollPane(Component view, Component rowHeaderView) {
        this(view);
        setRowHeaderView(rowHeaderView);
    }

    /**
     * {@link JScrollPane} modernizado
     * @param view componente a integrar al {@link JViewport}
     * @param vsb acción del scroll vertical
     * @param hsb acción del scroll horizontal
     */
    public ModernScrollPane(Component view, int vsb, int hsb) {
        super(vsb, hsb);
        setBorder(BorderFactory.createEmptyBorder());
        setScrollBarUI(getVerticalScrollBar());
        setScrollBarUI(getHorizontalScrollBar());
        setLayout(modernLayout());
        setComponentZOrder(getVerticalScrollBar(), 0);
        setComponentZOrder(getHorizontalScrollBar(), 1);
        setComponentZOrder(getViewport(), 2);
        viewport.setView(view);
    }

    private void setScrollBarUI(JScrollBar bar) {
        bar.setOpaque(false);
        bar.setUI(new ModernScrollBarUI(this));
    }

    private ScrollPaneLayout modernLayout() {
        return new ScrollPaneLayout() {
            @Override
            public void layoutContainer(Container parent) {
                super.layoutContainer(parent);
                Rectangle availR = availR(parent);
                if (Optional.ofNullable(vsb).isPresent())
                    vsb.setBounds(scrollBar(availR, isVerticalScrollBarNecessary(), false));
                if (Optional.ofNullable(hsb).isPresent())
                    hsb.setBounds(scrollBar(availR, isHorizontalScrollBarNecessary(), true));
            }
        };
    }

    private Rectangle availR(Container parent) {
        Rectangle availR = parent.getBounds();
        Insets insets = parent.getInsets();
        availR.x = insets.left;
        availR.y = insets.top;
        availR.width -= insets.left + insets.right;
        availR.height -= insets.top + insets.bottom;
        return availR;
    }

    private Rectangle scrollBar(Rectangle availR, boolean needed, boolean horizontal) {
        Rectangle rectangle = new Rectangle();
        if (horizontal) {
            rectangle.height = SB_SIZE;
            rectangle.width = availR.width - (needed ? rectangle.height : 0);
            rectangle.x = availR.x;
            rectangle.y = availR.y + availR.height - rectangle.height;
        } else {
            rectangle.width = SB_SIZE;
            rectangle.height = availR.height - (needed ? rectangle.width : 0);
            rectangle.x = availR.x + availR.width - rectangle.width;
            rectangle.y = availR.y;
        }
        return rectangle;
    }

    /**
     * Da a saber si el scroll vertical es necesario
     * @return necesidad del scroll vertical
     */
    private boolean isVerticalScrollBarNecessary() {
        return viewport.getViewRect().getHeight() > viewport.getViewSize().getHeight();
    }

    /**
     * Da a saber si el scroll horizontal es necesario
     * @return necesidad del scroll horizontal
     */
    private boolean isHorizontalScrollBarNecessary() {
        return viewport.getViewRect().getWidth() > viewport.getViewSize().getWidth();
    }

    /**
     * Retorna el componente añadido al {@link JScrollPane}
     * @return componente en el {@link JScrollPane}
     */
    public Component getView() {
        return viewport.getView();
    }

    /**
     * Retorna el componente añadido al Row Header
     * @return componente en el Row Header
     */
    public Component getRowHeaderView() {
        return rowHeader.getView();
    }

    /**
     * Modifica el valor de posición del {@link javax.swing.JScrollBar} horizontal
     * @param value valor de posición horizontal
     */
    public void setHorizontalValue(int value) {
        getHorizontalScrollBar().setValue(value);
    }

    /**
     * Modifica el valor de posición del {@link javax.swing.JScrollBar} vertical
     * @param value valor de posición vertical
     */
    public void setVerticalValue(int value) {
        getVerticalScrollBar().setValue(value);
    }
}
