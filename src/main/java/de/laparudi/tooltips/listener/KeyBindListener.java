package de.laparudi.tooltips.listener;

import com.mojang.blaze3d.platform.InputConstants;
import de.laparudi.tooltips.CytooxienTooltips;
import de.laparudi.tooltips.gui.WikiScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class KeyBindListener {

    private static KeyMapping debugToggleKey;
    private static KeyMapping openWikiKey;

    public static void register() {
        debugToggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("CXN Zeitmaschine Debug",  InputConstants.UNKNOWN.getValue(), KeyMapping.Category.MISC));
        openWikiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("Open Wiki",  InputConstants.UNKNOWN.getValue(), KeyMapping.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (debugToggleKey.consumeClick()) {
                CytooxienTooltips.toggleDebug();

                client.player.displayClientMessage(Component.literal("[CXN Tooltips] Debug-Modus: ").append(CytooxienTooltips.DEBUG
                        ? Component.literal("AN").withColor(0xFF00FF00)
                        : Component.literal("AUS").withColor(0xFFFF0000)), true);
            } else if (openWikiKey.consumeClick()) {
                client.setScreen(new WikiScreen());
            }
        });
    }
}
