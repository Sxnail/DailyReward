package de.sxnail.dailyrewards;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

@EventBusSubscriber(modid = Dailyrewards.MODID)
public class MenuClickHandler {

    @SubscribeEvent
    public static void onOpen(
            PlayerContainerEvent.Open event
    ) {

    }
}