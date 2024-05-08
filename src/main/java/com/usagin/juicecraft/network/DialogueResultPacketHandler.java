package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class DialogueResultPacketHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "dialogueresults")).networkProtocolVersion(() -> "1").serverAcceptedVersions(a -> true).clientAcceptedVersions(a -> true).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerDialogueResultPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerDialogueResultPacket::encode)
                .decoder(ToServerDialogueResultPacket::new)
                .consumerMainThread(ToServerDialogueResultPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), packet);
    }
}
