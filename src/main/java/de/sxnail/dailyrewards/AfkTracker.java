package de.sxnail.dailyrewards;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkTracker {

    private static final Map<UUID, Double> LAST_X =
            new HashMap<>();

    private static final Map<UUID, Double> LAST_Y =
            new HashMap<>();

    private static final Map<UUID, Double> LAST_Z =
            new HashMap<>();

    private static final Map<UUID, Integer> AFK_TICKS =
            new HashMap<>();

    public static boolean isPlayerAfk(UUID uuid) {

        return AFK_TICKS.getOrDefault(
                uuid,
                0
        ) >= 6000;
    }

    public static boolean isAfk(ServerPlayer player) {

        UUID uuid = player.getUUID();

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        if (!LAST_X.containsKey(uuid)) {

            LAST_X.put(uuid, x);
            LAST_Y.put(uuid, y);
            LAST_Z.put(uuid, z);

            AFK_TICKS.put(uuid, 0);

            return false;
        }

        boolean moved =
                LAST_X.get(uuid) != x
                        || LAST_Y.get(uuid) != y
                        || LAST_Z.get(uuid) != z;

        if (moved) {

            LAST_X.put(uuid, x);
            LAST_Y.put(uuid, y);
            LAST_Z.put(uuid, z);

            AFK_TICKS.put(uuid, 0);

            return false;
        }

        int ticks =
                AFK_TICKS.getOrDefault(uuid, 0) + 1;

        AFK_TICKS.put(uuid, ticks);

        // 6000 Ticks = 5 Minuten
        return ticks >= 6000;
    }
}