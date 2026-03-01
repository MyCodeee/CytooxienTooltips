package de.laparudi.tooltips.registry;

import net.minecraft.network.chat.Component;

public enum Event {

    NEW_YEAR("Neujahr", 0xFF1E90FF, 0xFFFFD700),
    VALENTINES_DAY("Valentinstag", 0xFFFF1493, 0xFFFFC0CB),
    EASTER("Ostern", 0xFFFFB6C1, 0xFF98FB98),
    SUMMER("Sommer", 0xFF7FFFD4, 0xFF00BFFF),
    ANNIVERSARY("Jubiläum", 0xFFFFFF00, 0xFFF2F2F2),
    HALLOWEEN("Halloween", 0xFFFF6600, 0xFFA569BD),
    CHRISTMAS("Weihnachten", 0xFFDC143C, 0xFF3CB371),
    CHRISTMAS_ROD("Weihnachtsangel", 0xFFDC143C, 0xFF3CB371),
    COLLECTION("Collection", 0xFFB080D0, 0);
    
    private final String name;
    private final int color;
    private final int secondColor;
    
    Event(final String name, final int color, final int secondColor) {
        this.name = name;
        this.color = color;
        this.secondColor = secondColor;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getSecondColor() {
        return secondColor;
    }

    public Component getText() {
        return Component.literal(this.getName()).withColor(this.getColor());
    }
    
    public Component getText(final int year) {
        return Component.literal(this.getName()).withColor(this.getColor())
                .append(Component.literal(" " + year).withColor(this.getSecondColor()));
    }
}
