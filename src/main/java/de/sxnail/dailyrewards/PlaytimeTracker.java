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

    private static final Map<UUID, Integer> TICKS =
            new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (AfkTracker.isAfk(player)) {
            return;
        }

        UUID uuid =
                player.getUUID();

        int ticks =
                TICKS.getOrDefault(uuid, 0) + 1;

        if (ticks >= 1200) {

            RewardSavedData saveData =
                    RewardSavedData.get(
                            player.server
                    );

            PlayerRewardData data =
                    saveData.getPlayerData(
                            uuid
                    );

            data.setActiveMinutesToday(
                    data.getActiveMinutesToday() + 1
            );

            saveData.markDirty();

            ticks = 0;
        }

        TICKS.put(uuid, ticks);
    }
}