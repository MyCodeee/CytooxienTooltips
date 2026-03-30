package de.laparudi.tooltips;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.laparudi.tooltips.exclusive.Registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Language {
    
    private static String current = "en_us";
    private static final Map<String, Map<String, String>> translations = new HashMap<>();

    private static final Gson gson = new Gson();
    private static final Set<String> languages = Set.of("en_us", "de_de", "de_cx", "de_at", "de_ch", "fr_fr", "ru_ru", "nl_nl", "da_dk", "es_es");
    
    static {
        languages.forEach(lang -> {
            try (final InputStream stream = Language.class.getClassLoader()
                    .getResourceAsStream("assets/cytooxien-tooltips/lang/" + lang + ".json")) {
                if (stream == null) return;

                final Map<String, String> map = gson.fromJson(
                        new InputStreamReader(stream, StandardCharsets.UTF_8),
                        new TypeToken<Map<String, String>>(){}.getType()
                );

                translations.put(lang, map != null ? map : new HashMap<>());
                
            } catch (final IOException exception) {
                CytooxienTooltips.LOGGER.info("Error while loading translations.", exception);
            }
        });
    }
    
    public static String get(final String key) {
        final String defaultTranslation = translations.get("en_us").get(key);
        final String translation = translations.get(current).get(key);
        return (translation == null || translation.isBlank()) ? defaultTranslation : translation;
    }
    
    public static void setCurrent(final String lang) {
        if (lang == null || lang.isBlank()) return;
        current = lang;
        Registry.load();
    }

    public static String getCurrent() {
        return current;
    }
    
    public static Locale getLocale() {
        return switch (current) {
            case "en_us" -> Locale.US;
            case "fr_fr" -> Locale.FRANCE;
            default -> Locale.GERMANY;
        };
    }
}
