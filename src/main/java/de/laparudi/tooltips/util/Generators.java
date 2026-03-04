package de.laparudi.tooltips.util;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generators {
    
    private static final Map<String, List<Double>> cobblePrices = new HashMap<>();
    private static final Map<String, List<Double>> deepslatePrices = new HashMap<>();
    private static final Map<String, List<Double>> netherPrices = new HashMap<>();

    private static final List<Double> cobbleDelay = List.of(100.0, 350.0, 1200.0, 5500.0, 11000.0, 19600.0, 32000.0, 49000.0, 71400.0, 100000.0);
    private static final List<Double> netherDeepslateDelay = List.of(200.0, 700.0, 2300.0, 5500.0, 11000.0, 19600.0, 32000.0, 49000.0, 71400.0, 100000.0);
    
    private static final List<Double> cobbleDefault = List.of(400.0, 800.0, 1200.0, 3085.0, 3825.0, 4565.0, 5300.0, 6035.0, 6765.0, 7500.0);
    private static final List<Double> coal = List.of(200.0, 250.0, 350.0, 945.0, 1110.0, 1275.0, 1435.0, 1595.0, 1750.0, 1905.0, 2060.0, 2215.0, 2365.0, 2515.0, 2665.0, 2810.0, 2960.0, 3105.0, 3250.0, 3395.0, 3540.0, 3680.0, 3825.0, 3965.0, 4105.0, 4250.0, 4390.0, 4530.0, 4665.0, 4805.0, 4945.0, 5080.0, 5220.0, 5355.0, 5495.0, 5630.0, 5765.0, 5900.0, 6035.0, 6170.0, 6305.0, 6440.0, 6570.0, 6705.0, 6840.0, 6970.0, 7105.0, 7235.0, 7370.0, 7500.0);
    private static final List<Double> iron = List.of(250.0, 400.0, 500.0, 1315.0, 1555.0, 1780.0, 2005.0, 2215.0, 2425.0, 2630.0, 2830.0, 3030.0, 3220.0, 3415.0, 3600.0, 3785.0, 3970.0, 4150.0, 4330.0, 4505.0, 4685.0, 4855.0, 5030.0, 5200.0, 5370.0, 5535.0, 5705.0, 5870.0, 6035.0, 6195.0, 6360.0, 6520.0, 6680.0, 6840.0, 6995.0, 7155.0, 7310.0, 7465.0, 7620.0, 7775.0, 7925.0, 8080.0, 8230.0, 8380.0, 8530.0, 8680.0, 8830.0, 8980.0, 9125.0, 9275.0, 9420.0, 9565.0, 9710.0, 9855.0, 10000.0);
    private static final List<Double> lapis = List.of(600.0, 800.0, 1000.0, 2390.0, 2700.0, 2980.0, 3245.0, 3495.0, 3735.0, 3960.0, 4180.0, 4390.0, 4595.0, 4795.0, 4985.0, 5170.0, 5355.0, 5530.0, 5705.0, 5875.0, 6045.0, 6210.0, 6370.0, 6525.0, 6685.0, 6835.0, 6985.0, 7135.0, 7280.0, 7425.0, 7570.0, 7710.0, 7850.0, 7985.0, 8120.0, 8255.0, 8390.0, 8520.0, 8650.0, 8780.0, 8905.0, 9030.0, 9155.0, 9280.0, 9405.0, 9525.0, 9645.0, 9765.0, 9885.0, 10000.0);
    private static final List<Double> gold = List.of(700.0, 1000.0, 1200.0, 2940.0, 3325.0, 3685.0, 4015.0, 4330.0, 4630.0, 4915.0, 5190.0, 5455.0, 5710.0, 5955.0, 6200.0, 6435.0, 6660.0, 6885.0, 7105.0, 7320.0, 7530.0, 7735.0, 7940.0, 8135.0, 8330.0, 8525.0, 8715.0, 8900.0, 9085.0, 9265.0, 9445.0, 9620.0, 9795.0, 9970.0, 10140.0, 10310.0, 10475.0, 10640.0, 10805.0, 10965.0, 11125.0, 11285.0, 11440.0, 11595.0, 11750.0, 11900.0, 12055.0, 12205.0, 12350.0, 12500.0);
    private static final List<Double> copper = List.of(200.0, 300.0, 400.0, 1130.0, 1335.0, 1525.0, 1715.0, 1895.0, 2075.0, 2250.0, 2420.0, 2585.0, 2750.0, 2910.0, 3070.0, 3230.0, 3385.0, 3535.0, 3690.0, 3840.0, 3990.0, 4135.0, 4280.0, 4425.0, 4570.0, 4715.0, 4855.0, 4995.0, 5135.0, 5270.0, 5410.0, 5545.0, 5680.0, 5815.0, 5950.0, 6085.0, 6215.0, 6350.0, 6480.0, 6610.0, 6740.0, 6870.0, 7000.0, 7125.0, 7255.0, 7380.0, 7505.0, 7635.0, 7760.0, 7885.0, 8010.0, 8130.0, 8255.0, 8380.0, 8500.0);
    private static final List<Double> diamond = List.of(1300.0, 2000.0, 2600.0, 6550.0, 7780.0, 8990.0, 10180.0, 11355.0, 12510.0, 13655.0, 14790.0, 15915.0, 17025.0, 18130.0, 19230.0, 20320.0, 21405.0, 22480.0, 23550.0, 24615.0, 25680.0, 26735.0, 27785.0, 28830.0, 29870.0, 30910.0, 31940.0, 32970.0, 33995.0, 35020.0, 36035.0, 37055.0, 38065.0, 39075.0, 40080.0, 41085.0, 42085.0, 43085.0, 44080.0, 45070.0, 46060.0, 47050.0, 48035.0, 49020.0, 50000.0);
    private static final List<Double> redstone = List.of(2700.0, 4000.0, 5000.0, 12085.0, 13745.0, 15275.0, 16710.0, 18060.0, 19345.0, 20575.0, 21760.0, 22895.0, 24000.0, 25065.0, 26105.0, 27115.0, 28100.0, 29065.0, 30005.0, 30930.0, 31835.0, 32720.0, 33590.0, 34445.0, 35290.0, 36115.0, 36935.0, 37735.0, 38530.0, 39310.0, 40080.0, 40845.0, 41595.0, 42335.0, 43070.0, 43795.0, 44515.0, 45225.0, 45925.0, 46625.0, 47310.0, 47995.0, 48670.0, 49335.0, 50000.0);
    private static final List<Double> emerald = List.of(1100.0, 1800.0, 2300.0, 5835.0, 6880.0, 7880.0, 8850.0, 9790.0, 10710.0, 11610.0, 12490.0, 13350.0, 14200.0, 15040.0, 15865.0, 16680.0, 17480.0, 18275.0, 19060.0, 19840.0, 20610.0, 21370.0, 22130.0, 22875.0, 23620.0, 24355.0, 25090.0, 25815.0, 26535.0, 27250.0, 27960.0, 28670.0, 29370.0, 30070.0, 30760.0, 31450.0, 32135.0, 32820.0, 33500.0, 34175.0, 34845.0, 35515.0, 36180.0, 36840.0, 37500.0);
    
    private static final List<Double> deepslateDefault = List.of(855.0, 1605.0, 2345.0, 3085.0, 3825.0, 4565.0, 5300.0, 6035.0, 6765.0, 7500.0);
    private static final List<Double> deepslateCoal = List.of(405.0, 595.0, 770.0, 945.0, 1110.0, 1275.0, 1435.0, 1595.0, 1750.0, 1905.0, 2060.0, 2215.0, 2365.0, 2515.0, 2665.0, 2810.0, 2960.0, 3105.0, 3250.0, 3395.0, 3540.0, 3680.0, 3825.0, 3965.0, 4105.0, 4250.0, 4390.0, 4530.0, 4665.0, 4805.0, 4945.0, 5080.0, 5220.0, 5355.0, 5495.0, 5630.0, 5765.0, 5900.0, 6035.0, 6170.0, 6305.0, 6440.0, 6570.0, 6705.0, 6840.0, 6970.0, 7105.0, 7235.0, 7370.0, 7500.0);
    private static final List<Double> deepslateIron = List.of(500.0, 800.0, 1065.0, 1315.0, 1555.0, 1780.0, 2005.0, 2215.0, 2425.0, 2630.0, 2830.0, 3030.0, 3220.0, 3415.0, 3600.0, 3785.0, 3970.0, 4150.0, 4330.0, 4505.0, 4685.0, 4855.0, 5030.0, 5200.0, 5370.0, 5535.0, 5705.0, 5870.0, 6035.0, 6195.0, 6360.0, 6520.0, 6680.0, 6840.0, 6995.0, 7155.0, 7310.0, 7465.0, 7620.0, 7775.0, 7925.0, 8080.0, 8230.0, 8380.0, 8530.0, 8680.0, 8830.0, 8980.0, 9125.0, 9275.0, 9420.0, 9565.0, 9710.0, 9855.0, 10000.0);
    private static final List<Double> deepslateLapis = List.of(1180.0, 1665.0, 2055.0, 2390.0, 2700.0, 2980.0, 3245.0, 3495.0, 3735.0, 3960.0, 4180.0, 4390.0, 4595.0, 4795.0, 4985.0, 5170.0, 5355.0, 5530.0, 5705.0, 5875.0, 6045.0, 6210.0, 6370.0, 6525.0, 6685.0, 6835.0, 6985.0, 7135.0, 7280.0, 7425.0, 7570.0, 7710.0, 7850.0, 7985.0, 8120.0, 8255.0, 8390.0, 8520.0, 8650.0, 8780.0, 8905.0, 9030.0, 9155.0, 9280.0, 9405.0, 9525.0, 9645.0, 9765.0, 9885.0, 10000.0);
    private static final List<Double> deepslateGold = List.of(1420.0, 2025.0, 2515.0, 2940.0, 3325.0, 3685.0, 4015.0, 4330.0, 4630.0, 4915.0, 5190.0, 5455.0, 5710.0, 5955.0, 6200.0, 6435.0, 6660.0, 6885.0, 7105.0, 7320.0, 7530.0, 7735.0, 7940.0, 8135.0, 8330.0, 8525.0, 8715.0, 8900.0, 9085.0, 9265.0, 9445.0, 9620.0, 9795.0, 9970.0, 10140.0, 10310.0, 10475.0, 10640.0, 10805.0, 10965.0, 11125.0, 11285.0, 11440.0, 11595.0, 11750.0, 11900.0, 12055.0, 12205.0, 12350.0, 12500.0);
    private static final List<Double> deepslateCopper = List.of(440.0, 695.0, 920.0, 1130.0, 1335.0, 1525.0, 1715.0, 1895.0, 2075.0, 2250.0, 2420.0, 2585.0, 2750.0, 2910.0, 3070.0, 3230.0, 3385.0, 3535.0, 3690.0, 3840.0, 3990.0, 4135.0, 4280.0, 4425.0, 4570.0, 4715.0, 4855.0, 4995.0, 5135.0, 5270.0, 5410.0, 5545.0, 5680.0, 5815.0, 5950.0, 6085.0, 6215.0, 6350.0, 6480.0, 6610.0, 6740.0, 6870.0, 7000.0, 7125.0, 7255.0, 7380.0, 7505.0, 7635.0, 7760.0, 7885.0, 8010.0, 8130.0, 8255.0, 8380.0, 8500.0);
    private static final List<Double> deepslateDiamond = List.of(2595.0, 3975.0, 5285.0, 6550.0, 7780.0, 8990.0, 10180.0, 11355.0, 12510.0, 13655.0, 14790.0, 15915.0, 17025.0, 18130.0, 19230.0, 20320.0, 21405.0, 22480.0, 23550.0, 24615.0, 25680.0, 26735.0, 27785.0, 28830.0, 29870.0, 30910.0, 31940.0, 32970.0, 33995.0, 35020.0, 36035.0, 37055.0, 38065.0, 39075.0, 40080.0, 41085.0, 42085.0, 43085.0, 44080.0, 45070.0, 46060.0, 47050.0, 48035.0, 49020.0, 50000.0);
    private static final List<Double> deepslateRedstone = List.of(5545.0, 8145.0, 10250.0, 12085.0, 13745.0, 15275.0, 16710.0, 18060.0, 19345.0, 20575.0, 21760.0, 22895.0, 24000.0, 25065.0, 26105.0, 27115.0, 28100.0, 29065.0, 30005.0, 30930.0, 31835.0, 32720.0, 33590.0, 34445.0, 35290.0, 36115.0, 36935.0, 37735.0, 38530.0, 39310.0, 40080.0, 40845.0, 41595.0, 42335.0, 43070.0, 43795.0, 44515.0, 45225.0, 45925.0, 46625.0, 47310.0, 47995.0, 48670.0, 49335.0, 50000.0);
    private static final List<Double> deepslateEmerald = List.of(2260.0, 3565.0, 4740.0, 5835.0, 6880.0, 7880.0, 8850.0, 9790.0, 10710.0, 11610.0, 12490.0, 13350.0, 14200.0, 15040.0, 15865.0, 16680.0, 17480.0, 18275.0, 19060.0, 19840.0, 20610.0, 21370.0, 22130.0, 22875.0, 23620.0, 24355.0, 25090.0, 25815.0, 26535.0, 27250.0, 27960.0, 28670.0, 29370.0, 30070.0, 30760.0, 31450.0, 32135.0, 32820.0, 33500.0, 34175.0, 34845.0, 35515.0, 36180.0, 36840.0, 37500.0);
    
    private static final List<Double> netherDefault = List.of(6765.0, 9525.0, 11640.0, 13425.0, 15000.0);
    private static final List<Double> quartz = List.of(1685.0, 3105.0, 4450.0, 5750.0, 7020.0, 8260.0, 9480.0, 10685.0, 11875.0, 13055.0, 14215.0, 15370.0, 16515.0, 17650.0, 18780.0, 19900.0, 21010.0, 22120.0, 23220.0, 24315.0, 25405.0, 26485.0, 27565.0, 28640.0, 29710.0, 30775.0, 31840.0, 32895.0, 33950.0, 35000.0);
    private static final List<Double> netherGold = List.of(1220.0, 2230.0, 3190.0, 4120.0, 5025.0, 5910.0, 6785.0, 7645.0, 8495.0, 9330.0, 10165.0, 10990.0, 11805.0, 12615.0, 13420.0, 14220.0, 15015.0, 15805.0, 16590.0, 17370.0, 18150.0, 18925.0, 19695.0, 20460.0, 21225.0, 21985.0, 22745.0, 23500.0, 24250.0, 25000.0);
    private static final List<Double> gildedBlackstone = List.of(885.0, 1485.0, 2055.0, 2605.0, 3145.0, 3670.0, 4185.0, 4695.0, 5200.0, 5700.0, 6195.0, 6680.0, 7165.0, 7650.0, 8125.0, 8600.0, 9075.0, 9540.0, 10010.0, 10470.0, 10935.0, 11395.0, 11850.0, 12305.0, 12760.0, 13210.0, 13660.0, 14110.0, 14555.0, 15000.0);
    private static final List<Double> ancientDebris = List.of(83450.0, 141595.0, 193930.0, 242825.0, 289305.0, 333945.0, 377120.0, 419075.0, 459985.0, 500000.0);

    static {
        cobblePrices.put("COAL_ORE", coal);
        cobblePrices.put("IRON_ORE", iron);
        cobblePrices.put("LAPIS_ORE", lapis);
        cobblePrices.put("GOLD_ORE", gold);
        cobblePrices.put("COPPER_ORE", copper);
        cobblePrices.put("DIAMOND_ORE", diamond);
        cobblePrices.put("REDSTONE_ORE", redstone);
        cobblePrices.put("EMERALD_ORE", emerald);
        cobblePrices.put("ANDESITE", cobbleDefault);
        cobblePrices.put("DIORITE", cobbleDefault);
        cobblePrices.put("GRANITE", cobbleDefault);

        deepslatePrices.put("DEEPSLATE_COAL_ORE", deepslateCoal);
        deepslatePrices.put("DEEPSLATE_IRON_ORE", deepslateIron);
        deepslatePrices.put("DEEPSLATE_LAPIS_ORE", deepslateLapis);
        deepslatePrices.put("DEEPSLATE_GOLD_ORE", deepslateGold);
        deepslatePrices.put("DEEPSLATE_COPPER_ORE", deepslateCopper);
        deepslatePrices.put("DEEPSLATE_DIAMOND_ORE", deepslateDiamond);
        deepslatePrices.put("DEEPSLATE_REDSTONE_ORE", deepslateRedstone);
        deepslatePrices.put("DEEPSLATE_EMERALD_ORE", deepslateEmerald);
        deepslatePrices.put("CALCITE", deepslateDefault);
        deepslatePrices.put("TUFF", deepslateDefault);
        
        netherPrices.put("BASALT", netherDefault);
        netherPrices.put("BLACKSTONE", netherDefault);
        netherPrices.put("NETHER_QUARTZ_ORE", quartz);
        netherPrices.put("NETHER_GOLD_ORE", netherGold);
        netherPrices.put("GILDED_BLACKSTONE", gildedBlackstone);
        netherPrices.put("ANCIENT_DEBRIS", ancientDebris);
        netherPrices.put("SOUL_SOIL", netherDefault);
        netherPrices.put("SOUL_SAND", netherDefault);
        netherPrices.put("GLOWSTONE", netherDefault);
    }
    
    private static double getBlockPrice(final String type, final String block, final int level) {
        final Map<String, List<Double>> map = switch (type) {
            case "default" -> cobblePrices;
            case "deepslate" -> deepslatePrices;
            case "nether" -> netherPrices;
            default -> null;
        };
        
        if (map == null) return 0.0;
        return map.get(block).stream().limit(level).mapToDouble(Double::doubleValue).sum();
    }
    
    private static double getDelayPrice(final boolean cobble, final int level) {
        final List<Double> list = cobble ? cobbleDelay : netherDeepslateDelay;
        return list.stream().limit(level).mapToDouble(Double::doubleValue).sum();
    }
    
    public static String generatorType(final CompoundTag tag) {
        return tag.getString("treasurechestitems:cobblegenerator_type").orElse(null);
    }
    
    public static int blockLevel(final CompoundTag tag, final String block) {
        final String type = generatorType(tag);
        final CompoundTag levelsTag = tag.getCompound("treasurechestitems:cobblegenerator_levels").orElse(null);
        if (type == null || levelsTag == null) return 0;
        
        return levelsTag.getInt(block.toUpperCase()).orElse(0);
    }
    
    public static double generatorPrice(final CompoundTag tag) {
        final String type = generatorType(tag);
        final CompoundTag levelsTag = tag.getCompound("treasurechestitems:cobblegenerator_levels").orElse(null);
        if (type == null || levelsTag == null) return 0.0;
        
        final int delayLevel = tag.getInt("treasurechestitems:cobblegenerator_baselevel").orElse(0);
        final double delayPrice = getDelayPrice(type.equals("default"), delayLevel);
        final List<Pair<String, Integer>> levels = levelsTag.keySet().stream().map(key -> Pair.of(key, levelsTag.getIntOr(key, 0))).toList();
        return delayPrice + levels.stream().mapToDouble(pair -> getBlockPrice(type, pair.key(), pair.value())).sum();
    }
    
    public static long generatorBlocks(final CompoundTag tag) {
        return tag.getLong("treasurechestitems:cobblegenerator_count").orElse(0L);
    }
}