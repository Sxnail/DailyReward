package de.sxnail.dailyrewards;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Dailyrewards.MODID)
public class Config {

    private static final ModConfigSpec.Builder BUILDER =
            new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DAILY_ENABLED =
            BUILDER.comment("Enable Daily Rewards")
                    .define("dailyRewardEnabled", true);

    public static final ModConfigSpec.BooleanValue PLAYTIME_ENABLED =
            BUILDER.comment("Enable Playtime Rewards")
                    .define("playtimeRewardEnabled", true);

    public static final ModConfigSpec.IntValue DAILY_AMOUNT =
            BUILDER.comment("Daily reward amount")
                    .defineInRange("dailyAmount", 25, 1, 100000);

    public static final ModConfigSpec.IntValue PLAYTIME_AMOUNT =
            BUILDER.comment("Playtime reward amount")
                    .defineInRange("playtimeAmount", 10, 1, 100000);

    public static final ModConfigSpec.IntValue REQUIRED_MINUTES =
            BUILDER.comment("Minutes required for playtime reward")
                    .defineInRange("requiredMinutes", 60, 1, 1440);

    public static final ModConfigSpec.IntValue AFK_MINUTES =
            BUILDER.comment("Minutes before AFK")
                    .defineInRange("afkMinutes", 5, 1, 120);

    public static final ModConfigSpec.ConfigValue<String> DAILY_ITEM =
            BUILDER.comment("Daily reward item")
                    .define("dailyItem", "minecraft:emerald");

    public static final ModConfigSpec.ConfigValue<String> PLAYTIME_ITEM =
            BUILDER.comment("Playtime reward item")
                    .define("playtimeItem", "minecraft:emerald");

    public static final ModConfigSpec.ConfigValue<String> TIMEZONE =
            BUILDER.comment("Timezone")
                    .define("timezone", "Europe/Berlin");

    public static final ModConfigSpec SPEC =
            BUILDER.build();

    public static boolean dailyRewardEnabled;
    public static boolean playtimeRewardEnabled;

    public static int dailyAmount;
    public static int playtimeAmount;

    public static int requiredMinutes;
    public static int afkMinutes;

    public static String dailyItem;
    public static String playtimeItem;

    public static String timezone;

    @SubscribeEvent
    public static void onConfigLoading(ModConfigEvent.Loading event) {

        if (!event.getConfig().getModId().equals(Dailyrewards.MODID)) {
            return;
        }

        dailyRewardEnabled = DAILY_ENABLED.get();
        playtimeRewardEnabled = PLAYTIME_ENABLED.get();

        dailyAmount = DAILY_AMOUNT.get();
        playtimeAmount = PLAYTIME_AMOUNT.get();

        requiredMinutes = REQUIRED_MINUTES.get();
        afkMinutes = AFK_MINUTES.get();

        dailyItem = DAILY_ITEM.get();
        playtimeItem = PLAYTIME_ITEM.get();

        timezone = TIMEZONE.get();
    }
}