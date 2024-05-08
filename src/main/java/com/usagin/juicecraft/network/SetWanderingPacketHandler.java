package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
public class SetWanderingPacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "setwandering")).networkProtocolVersion(() -> "1").serverAcceptedVersions(a -> true).clientAcceptedVersions(a -> true).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerSetWanderingPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerSetWanderingPacket::encode)
                .decoder(ToServerSetWanderingPacket::new)
                .consumerMainThread(ToServerSetWanderingPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), packet);
    }
}
