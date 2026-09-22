package de.sxnail.dailyrewards;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RewardSavedData extends SavedData {

    public static final String DATA_NAME = "dailyrewards";

    private final Map<UUID, PlayerRewardData> playerData =
            new HashMap<>();

    public RewardSavedData() {
    }

    @Override
    public CompoundTag save(
            CompoundTag tag,
            HolderLookup.Provider provider
    ) {

        CompoundTag playersTag =
                new CompoundTag();

        for (Map.Entry<UUID, PlayerRewardData> entry : playerData.entrySet()) {

            playersTag.put(
                    entry.getKey().toString(),
                    entry.getValue().save()
            );
        }

        tag.put("players", playersTag);

        return tag;
    }

    public static RewardSavedData load(
            CompoundTag tag,
            HolderLookup.Provider provider
    ) {

        RewardSavedData data =
                new RewardSavedData();

        CompoundTag playersTag =
                tag.getCompound("players");

        for (String key : playersTag.getAllKeys()) {

            UUID uuid =
                    UUID.fromString(key);

            PlayerRewardData playerData =
                    PlayerRewardData.load(
                            playersTag.getCompound(key)
                    );

            data.playerData.put(
                    uuid,
                    playerData
            );
        }

        RewardDataManager.setAllData(
                data.playerData
        );

        return data;
    }

    public static RewardSavedData get(
            MinecraftServer server
    ) {

        return server.overworld()
                .getDataStorage()
                .computeIfAbsent(
                        new SavedData.Factory<>(
                                RewardSavedData::new,
                                RewardSavedData::load
                        ),
                        DATA_NAME
                );
    }

    public PlayerRewardData getPlayerData(
            UUID uuid
    ) {

        return playerData.computeIfAbsent(
                uuid,
                u -> new PlayerRewardData()
        );
    }

    public Map<UUID, PlayerRewardData> getPlayerDataMap() {
        return playerData;
    }

    public void markDirty() {
        setDirty();
    }
}