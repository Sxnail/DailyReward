package de.sxnail.dailyrewards;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class RewardItems {

    public static Item getDailyItem() {

        return BuiltInRegistries.ITEM.get(
                ResourceLocation.parse(
                        Config.dailyItem
                )
        );
    }

    public static Item getPlaytimeItem() {

        return BuiltInRegistries.ITEM.get(
                ResourceLocation.parse(
                        Config.playtimeItem
                )
        );
    }
}