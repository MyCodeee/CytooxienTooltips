package de.laparudi.tooltips.listener;

import de.laparudi.tooltips.Language;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.List;

public class LanguageListener {
    
    private static final List<Pair<String, String>> languageTitles = List.of(
            Pair.of("Select Language", "en_us"),
            Pair.of("Sprache auswählen", "de_de"),
            Pair.of("Choisir la langue", "fr_fr"),
            Pair.of("Seleccionar idioma", "es_es"),
            Pair.of("Sproch auswähln", "de_at"),
            Pair.of("Sprach uswähle", "de_ch"),
            Pair.of("Selecteer taal", "nl_nl"),
            Pair.of("Vælg sprog", "da_dk"),
            Pair.of("Выбрать язык", "ru_ru")
    );
    
    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof AbstractContainerScreen<?>) || client.player == null) return;

            ScreenEvents.remove(screen).register((closedScreen) -> {
                final String title = closedScreen.getTitle().getString();

                for (final Pair<String, String> pair : languageTitles) {
                    if (!title.contains(pair.key())) continue;
                    
                    if (pair.value().equals("de_de") && title.length() == 116) {
                        Language.setCurrent("de_cx");
                        return;
                    }
                    
                    Language.setCurrent(pair.value());
                    return;
                }
            });
        });
    }
}
