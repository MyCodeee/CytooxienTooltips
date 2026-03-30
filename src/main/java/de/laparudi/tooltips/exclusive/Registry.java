package de.laparudi.tooltips.exclusive;

import java.util.HashMap;
import java.util.Map;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Registry {
    
    private static final Map<Pair<Integer, Item>, Component> items = new HashMap<>();

    public static void load() {
        items.clear();
        
        // Collection
        registerMulti(1100685, 5, Event.COLLECTION); // Goldene Plüschis
        registerMulti(1100690, 5, Event.COLLECTION); // Adventskranz
        registerMulti(1100710, 2, Event.COLLECTION); // Ofen Lore
        register(1100775, Event.COLLECTION); // Bushaltestelle
        register(1100900, Event.COLLECTION); // Fischerboot
        register(1100712, Event.COLLECTION); // Draisine
        register(1101079, Event.COLLECTION); // Schneemann (Geschmolzen)
        
        // Weihnachten 2022
        register(1001368, Items.LEATHER_HORSE_ARMOR, Event.CHRISTMAS, 2022); // Teddybär
        register(1001390, Items.LEATHER_HORSE_ARMOR, Event.CHRISTMAS, 2022); // Werkbank vom Nikolaus
        register(1100250, Event.CHRISTMAS, 2022); // Sitzender Schneemann
        registerMulti(1001381, 9, Items.LEATHER_HORSE_ARMOR, Event.CHRISTMAS, 2022); // Schlitten
        registerMulti(1001374, 7, Items.LEATHER_HORSE_ARMOR, Event.CHRISTMAS_ROD, 2022); // Angel-Spielzeuge
        
        // Neujahr 2023
        register(1001395, Items.LEATHER_HORSE_ARMOR, Event.NEW_YEAR, 2023); // Weinflasche
        
        // Valentinstag 2023
        register(1001458, Items.LEATHER_HORSE_ARMOR, Event.VALENTINES_DAY, 2023); // Teddybär mit Herz
        register(1001460, Items.LEATHER_HORSE_ARMOR, Event.VALENTINES_DAY, 2023); // Herzballon
        register(1100258, Event.VALENTINES_DAY, 2023); // Hut
        
        // Ostern 2023
        registerMulti(1001506, 10, Items.LEATHER_HORSE_ARMOR, Event.EASTER, 2023); // Eier
        
        // Sommer 2023
        register(1100222, Event.SUMMER, 2023); // Trinkkappe
        register(1100253, Event.SUMMER, 2023); // Sonnenbrillen-Hut
        register(1100170, Items.LEATHER_HORSE_ARMOR, Event.SUMMER, 2023); // Boombox (Alternative: 1100056)
        register(1100117, Items.LEATHER_HORSE_ARMOR, Event.SUMMER, 2023); // Flamingo Badetier
        registerMulti(1100127, 10, Items.LEATHER_HORSE_ARMOR, Event.SUMMER, 2023); // Zwerge
        
        // Halloween 2023
        register(1100269, Event.HALLOWEEN, 2023); // Medusa
        register(1100204, Items.LEATHER_HORSE_ARMOR, Event.HALLOWEEN, 2023); // Besen
        
        // Weihnachten 2023
        register(1100300, Event.CHRISTMAS, 2023); // Weihnachtsbaum-Hut
        register(1100278, Event.CHRISTMAS_ROD, 2023); // Wither
        register(1100302, Event.CHRISTMAS_ROD, 2023); // Piglin-Barbar
        registerMulti(1100295, 3, Event.CHRISTMAS_ROD, 2023); // Verwüster, Plünderer, Lohe
        
        // Ostern 2024
        registerMulti(1100407, 9, Event.EASTER, 2024); // Eier
        register(1100255, Event.EASTER, 2024); // Rankenhut
        register(1100463, Event.EASTER, 2024); // Häschenfigur
        
        // Sommer 2024
        registerMulti(1100519, 10, Event.SUMMER, 2024); // Zwerge
        
        // Jubiläum 2024
        register(1100567, Event.ANNIVERSARY, 2024); // Kuchen
        register(1100569, Event.ANNIVERSARY, 2024); // Ballon
        register(1100575, Event.ANNIVERSARY, 2024); // Hut
        
        // Halloween 2024
        register(1100613, Event.HALLOWEEN, 2024); // Schattendrachenkopf
        
        // Weihnachten 2024
        register(1100649, Event.CHRISTMAS, 2024); // Schneekugel-Minion (Landschaft)
        register(1100680, Event.CHRISTMAS_ROD, 2024); // Schneefuchs
        register(1100287, Event.CHRISTMAS_ROD, 2024); // Warden
        register(1100679, Event.CHRISTMAS_ROD, 2024); // Pilzkuh
        
        // Valentinstag 2025
        register(1100816, Event.VALENTINES_DAY, 2025); // Piglin-Statue
        
        // Ostern 2025
        register(1100853, Event.EASTER, 2025); // Sonnenblumen-Hut
        registerMulti(1100859, 11, Event.EASTER, 2025); // Eier
        
        // Sommer 2025
        register(1100971, Event.SUMMER, 2025); // Aloha-Blumenkrone
        registerMulti(1100982, 10, Event.SUMMER, 2025); // Zwerge
        
        // Jubiläum 2025
        registerMulti(1101009, 3, Event.ANNIVERSARY, 2025); // Hut, Kuchen, Ballon
        
        // Halloween 2025
        register(1101042, Event.HALLOWEEN, 2025); // Kürbishexenhut
        
        // Weihnachten 2025
        register(1101076, Event.CHRISTMAS, 2025); // Schneekugel-Minion (Schneemann)
        registerMulti(1101097, 3, Event.CHRISTMAS_ROD, 2025); // Großwächter, Fahrender-Händler, Skelettpferd
        
        // Valentinstag 2026
        register(1101123, Event.VALENTINES_DAY, 2026); // Balancierender-Creeper-Statue
        
        // Ostern 2026
        registerMulti(1101154, 10, Event.EASTER, 2026); // Eier
        register(1101166, Event.EASTER, 2026); // Löwenzahnhut
    }
    
    private static void registerMulti(int startID, final int totalAmount, final Item item, final Event event, final int year) {
        for (int i = 0; i < totalAmount; i++) {
            register(startID++, item, event, year);
        }
    }

    private static void registerMulti(int startID, final int totalAmount, final Event event, final int year) {
        for (int i = 0; i < totalAmount; i++) {
            register(startID++, Items.FIREWORK_STAR, event, year);
        }
    }

    private static void registerMulti(int startID, final int totalAmount, final Event event) {
        for (int i = 0; i < totalAmount; i++) {
            register(startID++, Items.FIREWORK_STAR, event);
        }
    }
    
    private static void register(final int id, final Item item, final Event event, final int year) {
        items.put(Pair.of(id, item), event.getText(year));
    }

    private static void register(final int id, final Item item, final Event event) {
        items.put(Pair.of(id, item), event.getText());
    }

    private static void register(final int id, final Event event) {
        register(id, Items.FIREWORK_STAR, event);
    }
    
    private static void register(final int id, final Event event, final int year) {
        register(id, Items.FIREWORK_STAR, event, year);
    }
    
    public static Component getLore(final int id, final Item item) {
        return items.getOrDefault(Pair.of(id, item), null);
    }
}
