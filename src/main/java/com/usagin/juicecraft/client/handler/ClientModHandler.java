package com.usagin.juicecraft.client.handler;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.Init.MenuInit;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.renderer.FriendMenuScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = JuiceCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuInit.FRIEND_MENU.get(), FriendMenuScreen::new);
        });
    }

}
