package org.modernUI.gui.event;

import java.util.EventListener;

public interface StatusListener extends EventListener {

    /**
     * Es invocado cuando el estado del componente pasa a ser <code>true</code>
     *
     * @param e evento
     */
    void trueState(StatusEvent e);

    /**
     * Es invocado cuando el estado del componente pasa a ser <code>false</code>
     *
     * @param e evento
     */
    void falseState(StatusEvent e);
}
