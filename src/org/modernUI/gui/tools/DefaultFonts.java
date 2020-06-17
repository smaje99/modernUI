package org.modernUI.gui.tools;

public enum DefaultFonts {
    OPEN_SANS_REGULAR("OpenSans-Regular.tff"),
    PURISA_BOLD("Purisa-Bold.ttf"),
    SEGOE_UI_SYMBOL("Segoe UI Symbol.ttf"),
    UBUNTU_LIGHT("UbuntuLight.ttf"),
    UBUNTU_MONO("UbuntuMono-R.ttf");

    private final CreateFont createFont;

    DefaultFonts(String font) {
        createFont = new CreateFont(getClass().getResourceAsStream("/org/modernUI/fonts/" + font));
    }

    public CreateFont getCreateFont() {
        return createFont;
    }
}
