package de.laparudi.tooltips.listener;

import de.laparudi.tooltips.CytooxienTooltips;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.network.Connection;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class JoinListener {

    public static void register() {
        ClientPlayConnectionEvents.JOIN.register( (listener, sender, minecraft) -> {
            final Connection connection = listener.getConnection();
            final SocketAddress address = connection.getRemoteAddress();
            
            if (!(address instanceof final InetSocketAddress inetAddress)) return;
            final String host = inetAddress.getHostName().toLowerCase();
            
            if (host.contains("cytooxien")) {
                CytooxienTooltips.CXN = true;
            }
        });
        
        ClientPlayConnectionEvents.DISCONNECT.register( (listener, minecraft) -> {
            CytooxienTooltips.CXN = false;
        });
    }
}
