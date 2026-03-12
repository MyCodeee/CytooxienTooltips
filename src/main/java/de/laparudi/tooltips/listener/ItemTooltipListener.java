package de.laparudi.tooltips.listener;

import de.laparudi.tooltips.CytooxienTooltips;
import de.laparudi.tooltips.registry.LoreRegistry;
import de.laparudi.tooltips.util.LoreUtils;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemTooltipListener {
    
    public static void register() {
        ItemTooltipCallback.EVENT.register( (itemStack, context, flag, lines) -> {
            if (!CytooxienTooltips.CXN) return;
            final CustomModelData data = itemStack.get(DataComponents.CUSTOM_MODEL_DATA);

            final int id = (data != null && !data.floats().isEmpty()) ? data.floats().getFirst().intValue() : -1;
            final String idDisplay = id == -1 ? "Keine" : String.valueOf(id);
            
            final Item item = itemStack.getItem();
            final List<Component> vanillaTooltips = flag.isAdvanced() ?
                    new ArrayList<>(lines.subList(lines.size() - 2, lines.size())) : Collections.emptyList();
            
            if (flag.isAdvanced()) {
                lines.removeLast();
                lines.removeLast();
            }

            if (id != -1) {
                final Component lore = LoreRegistry.getLore(id, item);
                if (lore != null) {
                    final int exclusiveLine = isRegistered(itemStack) ? 2 : 1;
                    final MutableComponent current = lines.get(exclusiveLine).copy();
                    lines.set(exclusiveLine, current.append(Component.literal(" ")).append(lore));
                }
            }

            final CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
            
            customData:
            if (customData != null) {
                final CompoundTag tag = customData.copyTag();
                final Tag bukkitTag = tag.get("PublicBukkitValues");
                if (bukkitTag == null) break customData;
                
                final CompoundTag bukkitCompound = bukkitTag.asCompound().orElse(null);
                if (bukkitCompound == null) break customData;
                
                final long itemStorage = bukkitCompound.getLong("treasurechestitems:skyblockx.item_storage_storage").orElse(0L);
                final long venditor = bukkitCompound.getLong("treasurechestitems:skyblockx.venditorplus_storage").orElse(0L);
                final long turnipTimestamp = bukkitCompound.getLong("treasurechestitems:turnip_4_harvesttime").orElse(0L);
                final String specialItem = bukkitCompound.getString("treasurechestitems:special_item").orElse("");
                
                final int emptyStorageLine = LoreUtils.findEmptyLine(lines, 3);
                final int emptyGeneratorLine = LoreUtils.findEmptyLine(lines, 2);
                final int emptySpawnerLine = LoreUtils.findEmptyLine(lines, 2) +1;
                
                if (id == 1001340 && itemStorage > 5000) {
                    lines.add(emptyStorageLine, LoreUtils.storageFormat(itemStorage, false));
                
                } else if (id == 1007012 && lines.size() > 4 && venditor > 5000) {
                    lines.add(emptyStorageLine, LoreUtils.storageFormat(venditor, true));
                    
                } else if ( (id >= 1100979 && id <= 1100988) || (id >= 1100998 && id <= 1101002) ) {
                    final int line = LoreUtils.turnipLoreSize(bukkitCompound);
                    final List<Component> list = LoreUtils.turnipFormat(turnipTimestamp);
                    
                    try {
                        lines.addAll(line, list);
                    } catch (final IndexOutOfBoundsException exception) {
                        lines.addAll(list);
                    }

                } else if (id == 1100957 || id == 1100958 || id == 1100959) {
                    lines.addAll(emptyGeneratorLine, LoreUtils.generatorFormat(bukkitCompound));

                } else if (specialItem.equals("spawner")) {
                    lines.set(emptySpawnerLine, LoreUtils.formatSpawner(bukkitCompound));
                }

                if (CytooxienTooltips.DEBUG) {
                    lines.add(Component.empty());
                    lines.add(Component.literal("ModelData: ").withColor(0xFF39FF14)
                            .append(Component.literal(idDisplay).withColor(0xFFD81E5B)));
                    lines.add(Component.empty());

                    Arrays.stream(bukkitTag.toString().split(",")).forEach(tagPart -> lines.add(Component.literal(tagPart)));
                    lines.addAll(vanillaTooltips);
                    return;
                }
            }

            if (CytooxienTooltips.DEBUG) {
                lines.add(Component.empty());
                lines.add(Component.literal("ModelData: ").withColor(0xFF39FF14).append(Component.literal(idDisplay).withColor(0xFFD81E5B)));
            }

            lines.addAll(vanillaTooltips);
        });
    }
    
    private static boolean isRegistered(final ItemStack item) {
        final CustomData data = item.get(DataComponents.CUSTOM_DATA);
        if (data == null) return false;

        final CompoundTag tag = data.copyTag();
        final Tag bukkitTag = tag.get("PublicBukkitValues");
        if (bukkitTag == null) return false;

        final CompoundTag bukkitCompound = bukkitTag.asCompound().orElse(null);
        if (bukkitCompound == null) return false;
        
        return bukkitCompound.getBoolean("treasurechestitems:modelblock_registered").orElse(false);
    }
}
