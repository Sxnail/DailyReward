package de.sxnail.dailyrewards;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public class PlaytimeRewardCommand {

    private static final ZoneId TIMEZONE =
            ZoneId.of("Europe/Berlin");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("playreward")
                        .executes(context -> {

                            ServerPlayer player =
                                    context.getSource().getPlayerOrException();

                            UUID uuid = player.getUUID();

                            LocalDate today =
                                    LocalDate.now(TIMEZONE);

                            PlayerRewardData data =
                                    RewardDataManager.getPlayerData(uuid);

                            if (today.toString().equals(data.getLastPlaytimeClaim())) {

                                context.getSource().sendSuccess(
                                        () -> Component.literal(
                                                "§cPlaytime Reward heute bereits abgeholt!"
                                        ),
                                        false
                                );

                                return 1;
                            }

                            if (data.getActiveMinutesToday() < Config.requiredMinutes) {

                                context.getSource().sendSuccess(
                                        () -> Component.literal(
                                                "§eNoch "
                                                        + (Config.requiredMinutes - data.getActiveMinutesToday())
                                                        + " Minuten spielen."
                                        ),
                                        false
                                );

                                return 1;
                            }

                            data.setLastPlaytimeClaim(today.toString());

                            ItemStack reward =
                                    new ItemStack(
                                            Items.EMERALD,
                                            Config.playtimeCoins
                                    );

                            player.getInventory()
                                    .placeItemBackInInventory(reward);

                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            "§aPlaytime Reward abgeholt! +"
                                                    + Config.playtimeCoins
                                                    + " Emeralds"
                                    ),
                                    false
                            );

                            return 1;
                        })
        );
    }
}