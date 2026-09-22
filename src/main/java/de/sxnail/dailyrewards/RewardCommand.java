package de.sxnail.dailyrewards;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.time.LocalDate;
import java.time.ZoneId;

public class RewardCommand {

    private static final ZoneId TIMEZONE =
            ZoneId.of("Europe/Berlin");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("rewards")
                        .executes(context -> {

                            ServerPlayer player =
                                    context.getSource()
                                            .getPlayerOrException();

                            PlayerRewardData data =
                                    RewardDataManager.getPlayerData(
                                            player.getUUID()
                                    );

                            String today =
                                    LocalDate.now(TIMEZONE)
                                            .toString();

                            boolean dailyClaimed =
                                    today.equals(
                                            data.getLastDailyClaim()
                                    );

                            boolean playtimeClaimed =
                                    today.equals(
                                            data.getLastPlaytimeClaim()
                                    );

                            boolean afk =
                                    AfkTracker.isPlayerAfk(
                                            player.getUUID()
                                    );

                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            "\n§6=== Daily Rewards ===\n" +
                                                    "§fDaily Reward: " +
                                                    (dailyClaimed
                                                            ? "§cAbgeholt"
                                                            : "§aVerfügbar")
                                                    + "\n" +
                                                    "§fPlaytime Reward: " +
                                                    (playtimeClaimed
                                                            ? "§cAbgeholt"
                                                            : "§aVerfügbar")
                                                    + "\n" +
                                                    "§fAktive Spielzeit: §e" +
                                                    data.getActiveMinutesToday()
                                                    + "/" +
                                                    Config.requiredMinutes +
                                                    " Minuten\n" +
                                                    "§fStatus: " +
                                                    (afk
                                                            ? "§cAFK"
                                                            : "§aAktiv")
                                                    + "\n" +
                                                    "§fDaily Coins: §a" +
                                                    Config.dailyCoins +
                                                    "\n" +
                                                    "§fPlaytime Coins: §a" +
                                                    Config.playtimeCoins
                                    ),
                                    false
                            );

                            return 1;
                        })
        );
    }
}