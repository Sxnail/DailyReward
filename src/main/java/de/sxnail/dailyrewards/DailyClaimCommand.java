package de.sxnail.dailyrewards;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public class DailyClaimCommand {

    private static final ZoneId TIMEZONE =
            ZoneId.of("Europe/Berlin");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("daily")
                        .executes(context -> {

                            ServerPlayer player =
                                    context.getSource().getPlayerOrException();

                            UUID uuid =
                                    player.getUUID();

                            RewardSavedData saveData =
                                    RewardSavedData.get(
                                            player.server
                                    );

                            PlayerRewardData data =
                                    saveData.getPlayerData(
                                            uuid
                                    );

                            String today =
                                    LocalDate.now(TIMEZONE)
                                            .toString();

                            if (today.equals(
                                    data.getLastDailyClaim()
                            )) {

                                context.getSource().sendSuccess(
                                        () -> Component.literal(
                                                "§cDu hast dein Daily heute bereits abgeholt!"
                                        ),
                                        false
                                );

                                return 1;
                            }

                            data.setLastDailyClaim(today);

                            saveData.markDirty();

                            ItemStack reward =
                                    new ItemStack(
                                            RewardItems.getDailyItem(),
                                            Config.dailyAmount
                                    );

                            player.getInventory()
                                    .placeItemBackInInventory(
                                            reward
                                    );

                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            "§aDaily Reward abgeholt!"
                                    ),
                                    false
                            );

                            return 1;
                        })
        );
    }
}