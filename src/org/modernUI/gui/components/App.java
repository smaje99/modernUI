package org.modernUI.gui.components;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.GridBagLayout;

public class App extends JFrame {
    public App(String title, Container contentPane) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
    }
}
