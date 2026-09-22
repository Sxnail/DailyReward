package de.sxnail.dailyrewards;

import java.time.LocalDate;

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
}