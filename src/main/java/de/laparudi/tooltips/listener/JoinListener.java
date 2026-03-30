package de.laparudi.tooltips.listener;

import de.laparudi.tooltips.CytooxienTooltips;
import de.laparudi.tooltips.Language;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.world.entity.player.Inventory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public class JoinListener {

    private static boolean waiting = false;
    private static int count = 0;
    
    public static void register() {
        ClientPlayConnectionEvents.JOIN.register( (listener, sender, minecraft) -> {
            final Connection connection = listener.getConnection();
            final SocketAddress address = connection.getRemoteAddress();
            
            if (!(address instanceof final InetSocketAddress inetAddress)) return;
            final String host = inetAddress.getHostName().toLowerCase();
            
            if (host.contains("cytooxien")) {
                CytooxienTooltips.cxn = true;
                waiting = true;
            }
        });
        
        ClientPlayConnectionEvents.DISCONNECT.register( (listener, minecraft) -> {
            CytooxienTooltips.cxn = false;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!waiting) return;
            if (count < 20) {
                count++;
                return;
            }

            Language.setCurrent(identifyLanguage(client));
            waiting = false;
            count = 0;
        });
    }
    
    private record LanguageIdentifier(int slot, String itemName, String lang) {}
    
    private static final List<LanguageIdentifier> identifiers = List.of(
            new LanguageIdentifier(7, "Visitenkarten", "de_cx"),
            new LanguageIdentifier(7, "Contactos", "es_es"),
            new LanguageIdentifier(7, "Kontakter", "da_dk"),
            new LanguageIdentifier(7, "Contacten", "nl_nl"),
            new LanguageIdentifier(7, "Kontäkt", "de_ch"),
            new LanguageIdentifier(7, "Контакты", "ru_ru"),
            new LanguageIdentifier(4, "Rucksock", "de_at"),
            new LanguageIdentifier(4, "Kosmetika", "de_de"),
            new LanguageIdentifier(4, "Cosmétiques", "fr_fr"),
            new LanguageIdentifier(4, "Cosmetics", "en_us")
    );
    
    private static String identifyLanguage(final Minecraft client) {
        if (client.player == null) return null;
        final Inventory inventory = client.player.getInventory();
        
        for (final LanguageIdentifier identifier : identifiers) {
            if (inventory.getItem(identifier.slot()).getDisplayName().getString().contains(identifier.itemName())) {
                return identifier.lang();
            }
        }
        return "";
    }
}
