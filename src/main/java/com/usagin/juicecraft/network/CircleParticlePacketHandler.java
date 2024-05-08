package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class CircleParticlePacketHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "circleparticle")).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToClientCircleParticlePacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
                .encoder(ToClientCircleParticlePacket::write)
                .decoder(ToClientCircleParticlePacket::new)
                .consumerMainThread(ToClientCircleParticlePacket::handle)
                .add();
    }

    public static void sendToClient(Object packet, Entity ent) {
        INSTANCE.send((PacketDistributor.PacketTarget) packet, PacketDistributor.TRACKING_ENTITY.with(() -> ent));
    }
}
