package de.laparudi.tooltips.exclusive;

import de.laparudi.tooltips.Language;
import de.laparudi.tooltips.util.LoreUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum Event {

    NEW_YEAR("event.new_year", 0xFF1E90FF, 0xFFFF7100),
    VALENTINES_DAY("event.valentines_day", 0xFFFF007B, 0xFFFF23C8),
    EASTER("event.easter", 0xFFC89F7F, 0xFF23C623),
    SUMMER("event.summer", 0xFF32C6B7, 0xFF00BFFF),
    ANNIVERSARY("event.anniversary", 0xFFC69B00, 0xFF95A59B),
    HALLOWEEN("event.halloween", 0xFFFF6600, 0xFFA569BD),
    CHRISTMAS("event.christmas", 0xFFDC143C, 0xFF3CB371),
    CHRISTMAS_ROD("event.christmas_rod", 0xFFDC143C, 0xFF3CB371),
    COLLECTION("event.collection", 0xFFB080D0, 0);
    
    private final String key;
    private final int color;
    private final int secondColor;
    
    Event(final String key, final int color, final int secondColor) {
        this.key = key;
        this.color = color;
        this.secondColor = secondColor;
    }

    public String getKey() {
        return key;
    }
    
    public int getColor() {
        return color;
    }

    public int getSecondColor() {
        return secondColor;
    }
    
    public Component getText() {
        return LoreUtils.formatCustomTag(Language.get(this.getKey()), false).withColor(this.getColor());
    }

    public Component getText(final int year) {
        final String translation = Language.get(this.getKey());
        final MutableComponent nameTag = LoreUtils.formatCustomTag(translation, false).withColor(this.getColor());
        final MutableComponent yearTag = LoreUtils.formatCustomTag(String.valueOf(year), true).withColor(this.getSecondColor());

        return nameTag.append(Component.literal(" ")).append(yearTag);
    }
}
