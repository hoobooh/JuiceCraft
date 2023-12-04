package com.usagin.juicecraft;

import com.usagin.juicecraft.Init.EntityInit;
import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.client.models.sora.SoraEntityModel;
import com.usagin.juicecraft.client.renderer.SoraEntityRenderer;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.usagin.juicecraft.particles.SuguriverseParticleLarge;

@Mod.EventBusSubscriber(modid = JuiceCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.SORA.get(), Sora.getSoraAttributes().build());
    }
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.SORA.get(),SoraEntityRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(SoraEntityModel.LAYER_LOCATION, SoraEntityModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(ParticleInit.SUGURIVERSE_LARGE.get(), SuguriverseParticleLarge.Provider::new);
    }
}
