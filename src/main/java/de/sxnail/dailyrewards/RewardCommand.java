package de.sxnail.dailyrewards;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class RewardCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("rewards")
                        .executes(context -> {

                            context.getSource().sendSuccess(
                                    () -> Component.literal("Daily Reward verfügbar!"),
                                    false
                            );

                            return 1;
                        })
        );
    }
}