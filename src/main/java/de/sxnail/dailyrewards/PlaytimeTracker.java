package de.sxnail.dailyrewards;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = Dailyrewards.MODID)
public class PlaytimeTracker {

    private static final Map<UUID, Integer> TICKS = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        UUID uuid = player.getUUID();

        int ticks = TICKS.getOrDefault(uuid, 0) + 1;

        // 1200 Ticks = 60 Sekunden
        if (ticks >= 1200) {

            PlayerRewardData data =
                    RewardDataManager.getPlayerData(uuid);

            data.setActiveMinutesToday(
                    data.getActiveMinutesToday() + 1
            );

            ticks = 0;
        }

        TICKS.put(uuid, ticks);
    }
}