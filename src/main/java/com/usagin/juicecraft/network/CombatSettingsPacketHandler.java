package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class CombatSettingsPacketHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "combatsettingsjuicecraft")).simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(ToServerCombatSettingsPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerCombatSettingsPacket::encode)
                .decoder(ToServerCombatSettingsPacket::new)
                .consumerMainThread(ToServerCombatSettingsPacket::handle)
                .add();
    }


    public static void sendToServer(Object packet) {
        INSTANCE.send((PacketDistributor.PacketTarget) packet, PacketDistributor.SERVER.noArg());
    }
}
