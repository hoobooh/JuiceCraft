package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class SpecialDialoguePacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "specialsenabled")).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerSpecialDialogueUpdatePacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerSpecialDialogueUpdatePacket::encode)
                .decoder(ToServerSpecialDialogueUpdatePacket::new)
                .consumerMainThread(ToServerSpecialDialogueUpdatePacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send((PacketDistributor.PacketTarget) packet, PacketDistributor.SERVER.noArg());
    }
}
