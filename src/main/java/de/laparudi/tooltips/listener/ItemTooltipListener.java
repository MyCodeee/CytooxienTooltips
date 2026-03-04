package de.laparudi.tooltips.listener;

import de.laparudi.tooltips.CytooxienTooltips;
import de.laparudi.tooltips.registry.LoreRegistry;
import de.laparudi.tooltips.util.LoreUtils;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
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
                    lines.add(Component.empty());
                    lines.add(lore);
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
                
                if (id == 1001340 && itemStorage > 5000) {
                    lines.add(6, LoreUtils.storageFormat(itemStorage, false));
                
                } else if (id == 1007012 && lines.size() > 4 && venditor > 5000) {
                    lines.add(7, LoreUtils.storageFormat(venditor, true));
                    
                } else if ( (id >= 1100979 && id <= 1100988) || (id >= 1100998 && id <= 1101002) ) {
                    final int line = LoreUtils.turnipLoreSize(bukkitCompound);
                    final List<Component> list = LoreUtils.turnipFormat(turnipTimestamp);
                    lines.addAll(line, list);

                } else if (id == 1100957 || id == 1100958 || id == 1100959) {
                    lines.addAll(LoreUtils.generatorFormat(bukkitCompound));

                } else if ("spawner".equals(specialItem)) {
                    LoreUtils.formatSpawner(lines, bukkitCompound);
                }

                if (CytooxienTooltips.DEBUG) {
                    lines.add(Component.empty());
                    String idDisplay = id == -1 ? "Keine" : String.valueOf(id);
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
                String idDisplay = id == -1 ? "Keine" : String.valueOf(id);
                lines.add(Component.literal("ModelData: ").withColor(0xFF39FF14).append(Component.literal(idDisplay).withColor(0xFFD81E5B)));
            }

            lines.addAll(vanillaTooltips);
        });
    }
}
