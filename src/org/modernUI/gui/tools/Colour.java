package org.modernUI.gui.tools;

import java.awt.*;

public enum Colour {
    SCROLL_BORDER(144, 144, 144),
    SCROLL_DRAGGING(145, 151, 151, 200),
    SCROLL_ROLLOVER(85, 100, 120, 200),
    SCROLL_PRESSED(220, 220, 200, 200);

    private final Color color;

    Colour(int r, int g, int b) {
        this(new Color(r, g, b));
    }

    Colour(int r, int g, int b, int a) {
        this(new Color(r, g, b, a));
    }

    Colour(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
