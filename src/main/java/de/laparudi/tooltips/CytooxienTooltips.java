package de.laparudi.tooltips;

import de.laparudi.tooltips.listener.ItemTooltipListener;
import de.laparudi.tooltips.listener.JoinListener;
import de.laparudi.tooltips.listener.KeyBindListener;
import net.fabricmc.api.ClientModInitializer;

public class CytooxienTooltips implements ClientModInitializer {
	
	public static boolean CXN = false;
	public static boolean DEBUG = false;
	
	@Override
	public void onInitializeClient() {
		ItemTooltipListener.register();
		JoinListener.register();
		KeyBindListener.register();
	}
	
	public static void toggleDebug() {
		DEBUG = !DEBUG;
	}
}