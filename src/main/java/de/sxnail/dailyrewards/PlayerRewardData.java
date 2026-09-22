package de.sxnail.dailyrewards;

import net.minecraft.nbt.CompoundTag;

public class PlayerRewardData {

    private String lastDailyClaim = "";
    private String lastPlaytimeClaim = "";

    private int activeMinutesToday = 0;

    public String getLastDailyClaim() {
        return lastDailyClaim;
    }

    public void setLastDailyClaim(String lastDailyClaim) {
        this.lastDailyClaim = lastDailyClaim;
    }

    public String getLastPlaytimeClaim() {
        return lastPlaytimeClaim;
    }

    public void setLastPlaytimeClaim(String lastPlaytimeClaim) {
        this.lastPlaytimeClaim = lastPlaytimeClaim;
    }

    public int getActiveMinutesToday() {
        return activeMinutesToday;
    }

    public void setActiveMinutesToday(int activeMinutesToday) {
        this.activeMinutesToday = activeMinutesToday;
    }

    public CompoundTag save() {

        CompoundTag tag = new CompoundTag();

        tag.putString("lastDailyClaim", lastDailyClaim);
        tag.putString("lastPlaytimeClaim", lastPlaytimeClaim);
        tag.putInt("activeMinutesToday", activeMinutesToday);

        return tag;
    }

    public static PlayerRewardData load(
            CompoundTag tag
    ) {

        PlayerRewardData data =
                new PlayerRewardData();

        data.lastDailyClaim =
                tag.getString("lastDailyClaim");

        data.lastPlaytimeClaim =
                tag.getString("lastPlaytimeClaim");

        data.activeMinutesToday =
                tag.getInt("activeMinutesToday");

        return data;
    }
}