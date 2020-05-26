package org.modernUI.contrains;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;

public final class View {
    private final Component component;
    private final Container container;

    /**
     * Vista del {@link Component} en el {@link Container}
     * @param component {@link Component} a añadir al {@link Container}
     * @param container {@link Container} al que se añadirá el {@link Component}
     */
    public View(final Component component, final Container container) {
        this.component = component;
        this.container = container;
    }

    /**
     * Añade el {@link Component} al {@link Container} aplicando un {@link GridBagConstraints}
     * @param grid diseño a aplicar al {@link Component} en el {@link Container}
     */
    protected void addComponent(GridBagConstraints grid) {
        container.add(component);
    }
}
