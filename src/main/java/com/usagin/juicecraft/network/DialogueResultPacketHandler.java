package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class DialogueResultPacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "dialogueresults")).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerDialogueResultPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerDialogueResultPacket::encode)
                .decoder(ToServerDialogueResultPacket::new)
                .consumerMainThread(ToServerDialogueResultPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send(packet, PacketDistributor.SERVER.noArg());
    }
}
