package de.laparudi.tooltips;

import de.laparudi.tooltips.listener.ItemTooltipListener;
import de.laparudi.tooltips.listener.JoinListener;
import de.laparudi.tooltips.listener.KeyBindListener;
import de.laparudi.tooltips.listener.LanguageListener;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CytooxienTooltips implements ClientModInitializer {
	
	public static boolean cxn = false;
	public static boolean debug = false;
	
	public static final Logger LOGGER = LoggerFactory.getLogger("cytooxien-tooltips");
	
	@Override
	public void onInitializeClient() {
		ItemTooltipListener.register();
		JoinListener.register();
		KeyBindListener.register();
		LanguageListener.register();
	}
	
	public static void toggleDebug() {
		debug = !debug;
	}
}