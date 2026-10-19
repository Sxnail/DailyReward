package de.sxnail.dailyrewards;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DailyMenuProvider {

    public static MenuProvider create() {

        return new SimpleMenuProvider(
                (id, inventory, player) -> {

                    SimpleContainer container =
                            new SimpleContainer(27);

                    ItemStack filler =
                            new ItemStack(
                                    Items.GRAY_STAINED_GLASS_PANE
                            );

                    filler.set(
                            DataComponents.CUSTOM_NAME,
                            Component.literal(" ")
                    );

                    for (int i = 0; i < 27; i++) {
                        container.setItem(
                                i,
                                filler.copy()
                        );
                    }

                    ItemStack daily =
                            new ItemStack(
                                    Items.WRITABLE_BOOK
                            );

                    daily.set(
                            DataComponents.CUSTOM_NAME,
                            Component.literal("§aClaim Daily Reward")
                    );

                    container.setItem(
                            11,
                            daily
                    );

                    ItemStack playtime =
                            new ItemStack(
                                    Items.CLOCK
                            );

                    playtime.set(
                            DataComponents.CUSTOM_NAME,
                            Component.literal("§61 Hour Reward")
                    );

                    container.setItem(
                            15,
                            playtime
                    );

                    return new DailyMenu(
                            id,
                            inventory,
                            container
                    );
                },
                Component.literal(
                        "Daily Rewards"
                )
        );
    }
}