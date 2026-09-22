package de.sxnail.dailyrewards;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class RewardSavedData extends SavedData {

    public static final String DATA_NAME = "dailyrewards";

    private int saveCounter = 0;

    public RewardSavedData() {
    }

    @Override
    public CompoundTag save(
            CompoundTag tag,
            HolderLookup.Provider provider
    ) {

        tag.putInt(
                "saveCounter",
                saveCounter
        );

        return tag;
    }

    public static RewardSavedData load(
            CompoundTag tag,
            HolderLookup.Provider provider
    ) {

        RewardSavedData data =
                new RewardSavedData();

        data.saveCounter =
                tag.getInt("saveCounter");

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

    public void increaseCounter() {

        saveCounter++;

        setDirty();
    }

    public int getSaveCounter() {
        return saveCounter;
    }
}