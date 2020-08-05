package org.modernUI;

import org.modernUI.gui.border.RoundBorder;
import org.modernUI.gui.components.App;
import org.modernUI.gui.components.ModernButton;
import org.modernUI.gui.components.tooltip.CustomToolTip;
import org.modernUI.gui.components.tooltip.MultiLineToolTipUI;
import org.modernUI.tools.Level;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ModernButton button = new ModernButton('f', "Joder", Level.LOW);

        CustomToolTip toolTip = new CustomToolTip();
        toolTip.setComponentUI(MultiLineToolTipUI.createUI(toolTip));
        toolTip.setBorder(new RoundBorder());

        button.setToolTip(toolTip);
        button.setToolTipText("Joder\nJoder\nJoder");


        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(button);
        new App("Joder", panel).setVisible(true);
    }
}
