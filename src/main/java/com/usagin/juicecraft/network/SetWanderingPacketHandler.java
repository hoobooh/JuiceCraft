package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class SetWanderingPacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "setwandering")).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerSetWanderingPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerSetWanderingPacket::encode)
                .decoder(ToServerSetWanderingPacket::new)
                .consumerMainThread(ToServerSetWanderingPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send(packet, PacketDistributor.SERVER.noArg());
    }
}
