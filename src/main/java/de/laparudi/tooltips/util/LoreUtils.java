package de.laparudi.tooltips.util;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LoreUtils {

    private static String formatTimestamp(final long timestamp) {
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(new Date(timestamp));
    }

    private static String formatTimeDifference(final long timestamp) {
        final long difference = Instant.now().toEpochMilli() - timestamp;
        final long oneHour = Duration.ofHours(1).toMillis();
        if (difference < oneHour) return "< 1 Stunde";

        final long totalHours = difference / oneHour;
        final long days = totalHours / 24;
        final long hours = totalHours % 24;

        final String dayDisplay = days == 0 ? "" : (days == 1 ? "1 Tag " : days + " Tage ");
        final String hourDisplay = hours == 0 ? "" : (hours == 1 ? "1 Stunde" : hours + " Stunden");

        return (dayDisplay + hourDisplay).trim();
    }
    
    private static String formatDouble(final double input) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(input);
    }
    
    private static String formatLong(final long input) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY);
        return format.format(input);
    }
    
    private static final Component split = Component.literal(" | ").withStyle(ChatFormatting.DARK_GRAY);
    
    public static List<Component> generatorFormat(final CompoundTag tag) {
        final List<Component> lore = new ArrayList<>();
        
        if (Generators.generatorType(tag).equals("nether")) {
            final int soulSoilLevel = Generators.blockLevel(tag, "soul_soil");
            final int soulSandLevel = Generators.blockLevel(tag, "soul_sand");
            final int glowstoneLevel = Generators.blockLevel(tag, "glowstone");
            
            lore.add(Component.literal(" ")
                    .append(blockLevelComponent("soul_soil", soulSoilLevel)).append(split)
                    .append(blockLevelComponent("soul_sand", soulSandLevel)).append(split)
                    .append(blockLevelComponent("glowstone", glowstoneLevel)));
        }
        
        final double price = Generators.generatorPrice(tag);
        final long blocks = Generators.generatorBlocks(tag);
        if (blocks == 0) return lore;
        
        final Component priceComponent = Component.literal(formatDouble(price)).withColor(0xFFFBECAB);
        final Component blocksComponent = Component.literal(formatLong(blocks)).withColor(0xFFFBECAB);
        
        lore.addAll(List.of(Component.empty(), Component.literal("Upgrade-Kosten: ").append(priceComponent),
                Component.literal("Abgebaute Blöcke: ").append(blocksComponent)));
        
        return lore;
    }
    
    public static int turnipLoreSize(final CompoundTag tag) {
        int size = 6;
        
        if (tag.getDouble("treasurechestitems:turnip_4_weight").orElse(0.0) > 1) size += 3;
        if (tag.getBoolean("treasurechestitems:turnip_4_shiny").orElse(false)) size += 3;
        if (tag.contains("treasurechestitems:turnip_4_appearance")) size += 3;
        return size;
    }
    
    public static List<Component> turnipFormat(final long timestamp) {
        final Component date = Component.literal(formatTimestamp(timestamp)).withColor(0xFFFBECAB);
        final Component age = Component.literal(formatTimeDifference(timestamp)).withColor(0xFFFBECAB);
        return List.of(Component.empty(), Component.literal("Geerntet: ").append(date), Component.literal("Alter: ").append(age));
    }
    
    public static Component storageFormat(final long space, final boolean venditor) {
        final double price = venditor ? venditor(space) : itemStorage(space);
        final Component component = Component.literal("~" + formatDouble(price)).withColor(0xFFFBECAB);
        return Component.literal("Kosten: ").append(component);
    }
    
    public static MutableComponent spriteTexture(final String blockOrItem) {
        return ComponentSerialization.CODEC.parse(JsonOps.INSTANCE,
                JsonParser.parseString("{\"sprite\":\"" + blockOrItem + "\"}"))
                .getOrThrow().copy();
    }
    
    private static MutableComponent blockLevelComponent(final String block, final int level) {
        return spriteTexture("block/" + block).append(Component.literal(" " + (level + 1)));
    }
    
    public static double itemStorage(final long space) {
        final long upgrades = (space - 640) / 64;
        if (upgrades <= 0) return 0.0;
        
        final double sqrtUpgrades = Math.sqrt(upgrades);
        final double multiply = upgrades * sqrtUpgrades;
        final double log = Math.log(upgrades + 1.0);

        return 43.912 * upgrades
                + 3.3579 * multiply
                - 0.000362 * upgrades * upgrades
                - 3.184 * log
                + 12.45
                - 0.00012 * upgrades;
    }

    public static double venditor(final long space) {
        final long upgrades = (space - 640) / 64;
        if (upgrades <= 0) return 0.0;

        final double sqrtUpgrades = Math.sqrt(upgrades);
        final double multiply = upgrades * sqrtUpgrades;
        final double log = Math.log(upgrades + 1.0);

        return 172.18 * upgrades
                + 16.408 * multiply
                - 0.04157 * upgrades * upgrades
                - 256.41 * log
                + 152.8
                - 0.00092 * upgrades;
    }
}
