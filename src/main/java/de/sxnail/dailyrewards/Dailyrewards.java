package de.sxnail.dailyrewards;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Dailyrewards.MODID)
public class Dailyrewards {

    public static final String MODID = "dailyrewards";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Dailyrewards(IEventBus modEventBus, ModContainer modContainer) {

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(
                ModConfig.Type.COMMON,
                Config.SPEC
        );
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

        RewardSavedData data =
                RewardSavedData.get(event.getServer());

        data.increaseCounter();

        LOGGER.info(
                "DailyRewards gestartet. Counter: "
                        + data.getSaveCounter()
        );
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {

        RewardCommand.register(event.getDispatcher());

        DailyClaimCommand.register(event.getDispatcher());

        PlaytimeRewardCommand.register(event.getDispatcher());
    }
}