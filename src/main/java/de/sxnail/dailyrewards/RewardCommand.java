package de.sxnail.dailyrewards;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

                            ZonedDateTime now =
                                    ZonedDateTime.now(TIMEZONE);

                            ZonedDateTime nextMidnight =
                                    now.toLocalDate()
                                            .plusDays(1)
                                            .atStartOfDay(TIMEZONE);

                            Duration duration =
                                    Duration.between(
                                            now,
                                            nextMidnight
                                    );

                            long hours =
                                    duration.toHours();

                            long minutes =
                                    duration.toMinutesPart();

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

                            int remainingPlaytime =
                                    Math.max(
                                            0,
                                            Config.requiredMinutes
                                                    - data.getActiveMinutesToday()
                                    );

                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            "\n§6=== Daily Rewards ===\n" +
                                                    "§fDaily Reward: " +
                                                    (dailyClaimed
                                                            ? "§cAbgeholt"
                                                            : "§aVerfügbar") +
                                                    "\n" +
                                                    "§fNächster Daily in: §e" +
                                                    hours +
                                                    "h " +
                                                    minutes +
                                                    "m\n\n" +
                                                    "§fPlaytime Reward: " +
                                                    (playtimeClaimed
                                                            ? "§cAbgeholt"
                                                            : "§aVerfügbar") +
                                                    "\n" +
                                                    "§fAktive Spielzeit: §e" +
                                                    data.getActiveMinutesToday() +
                                                    "/" +
                                                    Config.requiredMinutes +
                                                    " Minuten\n" +
                                                    "§fVerbleibend: §e" +
                                                    remainingPlaytime +
                                                    " Minuten\n" +
                                                    "§fStatus: " +
                                                    (afk
                                                            ? "§cAFK"
                                                            : "§aAktiv") +
                                                    "\n\n" +
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