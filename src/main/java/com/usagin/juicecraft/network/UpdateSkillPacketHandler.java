package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkRegistry.*;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class UpdateSkillPacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "updateskill")).networkProtocolVersion(() -> "1").serverAcceptedVersions(a -> true).clientAcceptedVersions(a -> true).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerUpdateSkillPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerUpdateSkillPacket::encode)
                .decoder(ToServerUpdateSkillPacket::new)
                .consumerMainThread(ToServerUpdateSkillPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), packet);
    }
}
