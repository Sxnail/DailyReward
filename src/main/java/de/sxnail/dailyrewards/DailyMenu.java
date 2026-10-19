package de.sxnail.dailyrewards;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import java.time.LocalDate;
import java.time.ZoneId;

public class DailyMenu extends ChestMenu {

    private static final ZoneId TIMEZONE =
            ZoneId.of("Europe/Berlin");

    public DailyMenu(
            int id,
            Inventory inventory,
            SimpleContainer container
    ) {
        super(
                MenuType.GENERIC_9x3,
                id,
                inventory,
                container,
                3
        );
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int index
    ) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clicked(
            int slotId,
            int button,
            ClickType clickType,
            Player player
    ) {

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        RewardSavedData saveData =
                RewardSavedData.get(
                        serverPlayer.server
                );

        PlayerRewardData data =
                saveData.getPlayerData(
                        serverPlayer.getUUID()
                );

        String today =
                LocalDate.now(TIMEZONE)
                        .toString();

        /*
         * DAILY REWARD
         */
        if (slotId == 11) {

            if (today.equals(
                    data.getLastDailyClaim()
            )) {

                serverPlayer.sendSystemMessage(
                        Component.literal(
                                "§cDu hast dein Daily heute bereits abgeholt!"
                        )
                );

                serverPlayer.closeContainer();
                return;
            }

            data.setLastDailyClaim(
                    today
            );

            saveData.markDirty();

            ItemStack reward =
                    new ItemStack(
                            RewardItems.getDailyItem(),
                            Config.dailyAmount
                    );

            serverPlayer.getInventory()
                    .placeItemBackInInventory(
                            reward
                    );

            serverPlayer.sendSystemMessage(
                    Component.literal(
                            "§aDaily Reward abgeholt!"
                    )
            );

            serverPlayer.closeContainer();
            return;
        }

        /*
         * PLAYTIME REWARD
         */
        if (slotId == 15) {

            if (today.equals(
                    data.getLastPlaytimeClaim()
            )) {

                serverPlayer.sendSystemMessage(
                        Component.literal(
                                "§cPlaytime Reward heute bereits abgeholt!"
                        )
                );

                serverPlayer.closeContainer();
                return;
            }

            if (AfkTracker.isPlayerAfk(
                    serverPlayer.getUUID()
            )) {

                serverPlayer.sendSystemMessage(
                        Component.literal(
                                "§cDu bist aktuell AFK!"
                        )
                );

                serverPlayer.closeContainer();
                return;
            }

            if (data.getActiveMinutesToday()
                    < Config.requiredMinutes) {

                int remaining =
                        Config.requiredMinutes
                                - data.getActiveMinutesToday();

                serverPlayer.sendSystemMessage(
                        Component.literal(
                                "§eNoch "
                                        + remaining
                                        + " Minuten spielen."
                        )
                );

                serverPlayer.closeContainer();
                return;
            }

            data.setLastPlaytimeClaim(
                    today
            );

            saveData.markDirty();

            ItemStack reward =
                    new ItemStack(
                            RewardItems.getPlaytimeItem(),
                            Config.playtimeAmount
                    );

            serverPlayer.getInventory()
                    .placeItemBackInInventory(
                            reward
                    );

            serverPlayer.sendSystemMessage(
                    Component.literal(
                            "§aPlaytime Reward abgeholt!"
                    )
            );

            serverPlayer.closeContainer();
            return;
        }

        /*
         * GUI SLOTS BLOCKIEREN
         */
        if (slotId >= 0 && slotId < 27) {
            return;
        }

        super.clicked(
                slotId,
                button,
                clickType,
                player
        );
    }
}