package com.usagin.juicecraft.network;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;

public class PacketHandler {
    public static final SimpleChannel INSTANCE = ChannelBuilder.named(
            new ResourceLocation(JuiceCraft.MODID, "main")).simpleChannel();
    public static void register(){
        INSTANCE.messageBuilder(ToServerPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(ToServerPacket::encode)
                .decoder(ToServerPacket::new)
                .consumerMainThread(ToServerPacket::handle)
                .add();
    }
    public static void sendToServer(Object packet){
        INSTANCE.send(packet, PacketDistributor.SERVER.noArg());
    }
}
