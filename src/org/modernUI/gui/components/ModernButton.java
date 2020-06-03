package org.modernUI.gui.components;

import org.modernUI.gui.border.RoundBorder;
import org.modernUI.gui.contrains.Constrains;
import org.modernUI.gui.contrains.View;
import org.modernUI.gui.contrains.Weight;
import org.modernUI.gui.tools.Colour;
import org.modernUI.gui.tools.CreateFont;
import org.modernUI.tools.Level;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class ModernButton extends JPanel {
    private final JLabel icon;
    private final JLabel text;
    private final JProgressBar difficulty;
    private Consumer<MouseEvent> event;

    {
        icon = new JLabel();
        text = new JLabel();
        difficulty = new JProgressBar();
    }

    public ModernButton(ImageIcon icon, String text, Level difficulty) {
        this(' ', text, difficulty);
        this.icon.setIcon(icon);
    }

    public ModernButton(char icon, String text, Level difficulty) {
        super(new GridBagLayout());
        event = e -> {};
        setBackground(Colour.LAVENDER.getColor());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new RoundBorder(6, Colour.LAVENDER.getColor()));
        addMouseListener(event());
        init(String.valueOf(icon).trim(), text, difficulty);
    }

    private void init(String icon, String text, Level difficulty) {
        CreateFont ubuntuLight = new CreateFont(getClass().getResourceAsStream("/org/modernUI/lib/UbuntuLight.ttf"));
        CreateFont ubuntuMono = new CreateFont(getClass().getResourceAsStream("/org/modernUI/lib/UbuntuMono-R.ttf"));
        this.icon.setText(icon);
        this.icon.setHorizontalAlignment(SwingConstants.CENTER);
        this.icon.setFont(ubuntuMono.getFont(Font.BOLD, 96));
        this.icon.setCursor(getCursor());
        this.icon.addMouseListener(getMouseListeners()[0]);
        this.text.setText(text);
        this.text.setHorizontalAlignment(SwingConstants.CENTER);
        this.text.setFont(ubuntuLight.getFont(Font.PLAIN, 20));
        this.text.setCursor(getCursor());
        this.text.addMouseListener(getMouseListeners()[0]);
        this.difficulty.setValue(0);
        this.difficulty.setBackground(Colour.valueOf(difficulty.toString()).getColor());
        this.difficulty.setBorderPainted(false);
        this.difficulty.setCursor(getCursor());
        this.difficulty.addMouseListener(getMouseListeners()[0]);
        addComponents();
    }

    private void addComponents() {
        Constrains.addComp(
                new View(icon, this),
                new Rectangle(0, 0, 1, 1),
                new Weight(1, 1),
                new Insets(15, 5, 5, 5),
                new Point(GridBagConstraints.CENTER, GridBagConstraints.BOTH)
        );
        Constrains.addCompX(
                new View(text, this),
                new Rectangle(0, 1, 1, 1),
                0.5,
                new Insets(2, 5, 5, 5),
                new Point(GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL)
        );
        Constrains.addCompX(
                new View(difficulty, this),
                new Rectangle(0, 2, 1, 1),
                1,
                new Insets(8, 0, 0, 0),
                new Point(GridBagConstraints.SOUTH, GridBagConstraints.BOTH)
        );
    }

    private MouseListener event() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                event.accept(e);
            }
        };
    }

    public JLabel getIcon() {
        return icon;
    }

    public JLabel getText() {
        return text;
    }

    public JProgressBar getDifficulty() {
        return difficulty;
    }

    public Consumer<MouseEvent> getEvent() {
        return event;
    }

    public void setEvent(Consumer<MouseEvent> event) {
        this.event = event;
    }

    public void setIcon(char icon) {
        this.icon.setText(String.valueOf(icon).trim());
        updateUI(this.icon);
    }

    public void setIcon(ImageIcon icon) {
        this.icon.setText("");
        this.icon.setIcon(icon);
        updateUI(this.icon);
    }

    public void setText(String text) {
        this.text.setText(text);
        updateUI(this.text);
    }

    public void setDifficulty(Level difficulty) {
        this.difficulty.setBackground(Colour.valueOf(difficulty.toString()).getColor());
        updateUI(this.difficulty);
    }

    public void updateUI(JComponent component) {
        component.updateUI();
        updateUI();
    }

    @Override
    public void setToolTipText(String text) {
        super.setToolTipText(text);
        icon.setToolTipText(text);
        this.text.setToolTipText(text);
        difficulty.setToolTipText(text);
    }

    @Override
    public void setComponentPopupMenu(JPopupMenu popup) {
        super.setComponentPopupMenu(popup);
        icon.setComponentPopupMenu(popup);
        text.setComponentPopupMenu(popup);
        difficulty.setComponentPopupMenu(popup);
    }
}
