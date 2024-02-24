package com.usagin.juicecraft.data;

import com.usagin.juicecraft.data.loot.ChestLootProvider;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.usagin.juicecraft.JuiceCraft.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(),new ChestLootProvider(event.getGenerator().getPackOutput()));
    }
}
