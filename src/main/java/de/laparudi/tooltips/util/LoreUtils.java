package de.laparudi.tooltips.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import de.laparudi.tooltips.Language;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class LoreUtils {
    
    private static String formatTimestamp(long timestamp) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT);
        final ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault());
        
        String formatted = dateTime.format(formatter.withLocale(Language.getLocale()));
        if (Language.getCurrent().equals("de_cx")) formatted += " n. Chr.";
        return formatted;
    }
    
    private static String formatTimeDifference(final long timestamp) {
        final long difference = Instant.now().toEpochMilli() - timestamp;
        final long oneHour = Duration.ofHours(1).toMillis();
        if (difference < oneHour) return "< 1 " + Language.get("unit.hour");

        final long totalHours = difference / oneHour;
        final long days = totalHours / 24;
        final long hours = totalHours % 24;

        final String dayDisplay = days == 0 ? "" : (days == 1 ? "1 " + Language.get("unit.day") + " " : days + " " + Language.get("unit.days") + " ");
        final String hourDisplay = hours == 0 ? "" : (hours == 1 ? "1 " + Language.get("unit.hour") : hours + " " + Language.get("unit.hours"));

        return (dayDisplay + hourDisplay).trim();
    }

    private static String formatDouble(final double input) {
        final NumberFormat format = NumberFormat.getNumberInstance(Language.getLocale());
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(input);
    }

    private static String formatLong(final long input) {
        final NumberFormat format = NumberFormat.getNumberInstance(Language.getLocale());
        return format.format(input);
    }

    public static MutableComponent formatCustomTag(final String input, final boolean isNumeric) {
        if (input == null || input.isEmpty()) return Component.empty();
        final String upperInput = input.toUpperCase(Language.getLocale());

        final ResourceLocation textLoc = ResourceLocation.parse(isNumeric ? "cytooxien-tooltips:small_numbers" : "minecraft:small");
        final ResourceLocation bgLoc = ResourceLocation.parse("minecraft:tag");
        final FontDescription textFont = new FontDescription.Resource(textLoc);
        final FontDescription bgFont = new FontDescription.Resource(bgLoc);

        final StringBuilder bgBuilder = new StringBuilder();
        final int length = upperInput.length();
        
        final char lengthChar = (char) (0xE200 + (length - 1));
        bgBuilder.append(lengthChar);
        bgBuilder.append("\uE211");
        bgBuilder.append("\uE212".repeat(length));

        final StringBuilder fgBuilder = new StringBuilder();
        for (char c : upperInput.toCharArray()) {
            fgBuilder.append(c).append("\uE210");
        }
        
        return Component.literal(bgBuilder.toString())
                .withStyle(style -> style.withFont(bgFont).withShadowColor(0))
                .append(Component.literal(fgBuilder.toString())
                        .withStyle(style -> style
                                .withFont(textFont)
                                .withColor(0xFFFFFF)
                                .withShadowColor(0)
                        ));
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

        lore.addAll(List.of(
                Component.empty(),
                Component.literal(Language.get("upgrade.costs") + ": ").append(priceComponent),
                Component.literal(Language.get("generator.broken_blocks") + ": ").append(blocksComponent))
        );

        return lore;
    }

    public static int turnipLoreSize(final CompoundTag tag) {
        if (tag.getLong("treasurechestitems:turnip_4_harvesttime").orElse(0L) == 0L) return -1; 
        int size = 6;

        if (tag.getDouble("treasurechestitems:turnip_4_weight").orElse(0.0) > 1) size += 3;
        if (tag.getBoolean("treasurechestitems:turnip_4_shiny").orElse(false)) size += 3;
        if (tag.contains("treasurechestitems:turnip_4_appearance")) size += 3;
        return size;
    }

    public static List<Component> turnipFormat(final long timestamp) {
        final Component date = Component.literal(formatTimestamp(timestamp)).withColor(0xFFFBECAB);
        final Component age = Component.literal(formatTimeDifference(timestamp)).withColor(0xFFFBECAB);
        
        return List.of(
                Component.empty(),
                Component.literal(Language.get("turnip.harvested") + ": ").append(date),
                Component.literal(Language.get("turnip.age") + ": ").append(age)
        );
    }

    public static Component storageFormat(final long space, final boolean venditor) {
        final double price = venditor ? venditor(space) : itemStorage(space);
        final Component component = Component.literal("~" + formatDouble(price)).withColor(0xFFFBECAB);
        return Component.literal(Language.get("upgrade.costs") + ": ").append(component);
    }

    public static Component formatSpawner(final CompoundTag tag) {
        final int remainingSpawns = tag.getInt("treasurechestitems:spawner_spawns").orElse(0);
        final int originalSpawns = tag.getInt("treasurechestitems:spawner_original_spawns").orElse(0);

        return Component.literal(Language.get("spawner.spawns") + ": ")
                .append(Component.literal(remainingSpawns + "/" + originalSpawns).withColor(0xFEEFAD));
    }

    public static Component formatWateringCan(final int durability) {
        return Component.literal(Language.get("turnip.can.durability") + ": ")
                .append(Component.literal(Integer.toString(durability)).withColor(0xFEEFAD));
    }
    
    public static List<Component> formatFishingTrophy(final CompoundTag tag) {
        final long date = tag.getLong("treasurechestitems:fishing.fishing_cup_big_fish_contest_date").orElse(0L);
        final String fish = tag.getString("treasurechestitems:fishing.fishing_cup_big_fish_contest_fish").orElse("");
        final int points = tag.getInt("treasurechestitems:fishing.fishing_cup_big_fish_points_earned").orElse(0);
        final String winnerJson = tag.getString("treasurechestitems:fishing.fishing_cup_big_fish_winner").orElse("");

        String player = "Unbekannt";
        int playerColor = 0xFF9BA3AC;

        if (!winnerJson.isEmpty()) {
            final JsonObject obj = JsonParser.parseString(winnerJson).getAsJsonObject();
            player = obj.get("text").getAsString();
            
            if (obj.has("color")) {
                playerColor = Integer.parseInt(obj.get("color").getAsString().replace("#", ""), 16);
            }
        }
        
        return List.of(formatCustomTag(Language.get("tag.details"), false).withColor(0xFF3272D3),
                Component.literal(" ● " + Language.get("fishing_trophy.winner") + ": ").append(Component.literal(player).withColor(playerColor)),
                Component.literal(" ● " + Language.get("fishing_trophy.date") + ": ").append(Component.literal(formatTimestamp(date)).withColor(0xFFFBECAB)),
                Component.literal(" ● " + Language.get("fishing_trophy.fish") + ": ").append(Component.literal(Language.get("fish." + fish)).withColor(0xFFFBECAB)),
                Component.literal(" ● " + Language.get("fishing_trophy.points") + ": ").append(Component.literal(String.valueOf(points)).withColor(0xFFFBECAB)));
    }
    
    public static int findEmptyLine(final List<Component> lore, final int emptyLines) {
        final int search = IntStream.range(0, lore.size())
                .filter(line -> lore.get(line).getString().isBlank())
                .skip(emptyLines -1)
                .findFirst()
                .orElse(-1);
        
        return search != -1 ? search : lore.size();
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
