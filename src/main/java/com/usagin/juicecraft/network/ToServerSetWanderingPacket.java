package com.usagin.juicecraft.network;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Objects;

public class ToServerSetWanderingPacket {
    private final boolean set;
    private final int id;
    private Friend friend;

    //should be same order as write apparently
    public ToServerSetWanderingPacket(FriendlyByteBuf buffer) {
        this(buffer.readBoolean(), buffer.readVarInt());
    }

    public ToServerSetWanderingPacket(boolean set, int id) {
        this.set = set;
        this.id = id;

    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.set);
        buffer.writeVarInt(this.id);
    }

    //menu should close in time in case of level change, shouldnt be any sync issues
    public void handle(CustomPayloadEvent.Context context) {
        ServerLevel level = Objects.requireNonNull(context.getSender()).serverLevel();
        this.friend = decodeBuffer(level, this.id);
        if (friend != null) {
            this.friend.setIsWandering(this.set);
            context.setPacketHandled(true);
        }
    }

    public static Friend decodeBuffer(Level level, int i) {
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }
}
