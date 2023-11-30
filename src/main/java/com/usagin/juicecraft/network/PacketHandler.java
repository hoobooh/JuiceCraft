package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;

public class PacketHandler {
    /*public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "main")).simpleChannel();
    public static void register(){
        INSTANCE.messageBuilder(ToClientPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
                .encoder(ToClientPacket::encode)
                .decoder(ToClientPacket::new)
                .add();
    }
    public static void sendToServer(PacketDistributor.PacketTarget packet){
        INSTANCE.send(packet, PacketDistributor.SERVER.noArg());
    }
    public static void sendToPlayer(PacketDistributor.PacketTarget packet, ServerPlayer pPlayer){
        INSTANCE.send(packet, PacketDistributor.PLAYER.with(()->pPlayer));
    }
    public static void sentToAllClients(PacketDistributor.PacketTarget packet){
        INSTANCE.send(packet, PacketDistributor.ALL.noArg());
    }*/
}
