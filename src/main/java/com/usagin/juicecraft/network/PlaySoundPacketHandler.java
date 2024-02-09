package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class PlaySoundPacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "playsound")).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerPlaySoundPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerPlaySoundPacket::encode)
                .decoder(ToServerPlaySoundPacket::new)
                .consumerMainThread(ToServerPlaySoundPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send(packet, PacketDistributor.SERVER.noArg());
    }
}
