package de.sxnail.dailyrewards;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RewardDataManager {

    private static final Map<UUID, PlayerRewardData> PLAYER_DATA =
            new HashMap<>();

    public static Map<UUID, PlayerRewardData> getAllData() {
        return PLAYER_DATA;
    }

    public static void setAllData(
            Map<UUID, PlayerRewardData> newData
    ) {

        PLAYER_DATA.clear();
        PLAYER_DATA.putAll(newData);
    }

    public static PlayerRewardData getPlayerData(
            UUID uuid
    ) {

        return PLAYER_DATA.computeIfAbsent(
                uuid,
                id -> new PlayerRewardData()
        );
    }

    public static void saveToWorld(
            RewardSavedData saveData
    ) {

        saveData.getPlayerDataMap().clear();

        saveData.getPlayerDataMap().putAll(
                PLAYER_DATA
        );

        saveData.markDirty();
    }
}