package de.sxnail.dailyrewards;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Dailyrewards.MODID)
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue DAILY_COINS =
            BUILDER.comment("Coins for daily reward")
                    .defineInRange("dailyCoins", 25, 1, 100000);

    public static final ModConfigSpec.IntValue PLAYTIME_COINS =
            BUILDER.comment("Coins for playtime reward")
                    .defineInRange("playtimeCoins", 10, 1, 100000);

    public static final ModConfigSpec.IntValue REQUIRED_MINUTES =
            BUILDER.comment("Minutes required for playtime reward")
                    .defineInRange("requiredMinutes", 60, 1, 1440);

    public static final ModConfigSpec.IntValue AFK_MINUTES =
            BUILDER.comment("Minutes before AFK")
                    .defineInRange("afkMinutes", 5, 1, 120);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int dailyCoins;
    public static int playtimeCoins;
    public static int requiredMinutes;
    public static int afkMinutes;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        dailyCoins = DAILY_COINS.get();
        playtimeCoins = PLAYTIME_COINS.get();
        requiredMinutes = REQUIRED_MINUTES.get();
        afkMinutes = AFK_MINUTES.get();
    }
}