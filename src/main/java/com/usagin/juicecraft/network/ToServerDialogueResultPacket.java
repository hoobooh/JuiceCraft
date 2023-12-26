package com.usagin.juicecraft.network;

import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Objects;

public class ToServerDialogueResultPacket {
    private final int normachange;
    private Friend friend;
    private final int id;
    public ToServerDialogueResultPacket(int normachange, int id){
        this.normachange=normachange;
        this.id=id;

    }
    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.normachange);
        buffer.writeVarInt(this.id);
    }

    //should be same order as write apparently
    public ToServerDialogueResultPacket(FriendlyByteBuf buffer){
        this(buffer.readInt(), buffer.readVarInt());
    }
    //menu should close in time in case of level change, shouldnt be any sync issues
    public void handle(CustomPayloadEvent.Context context){
        ServerLevel level = Objects.requireNonNull(context.getSender()).serverLevel();
        this.friend=decodeBuffer(level, this.id);
        if(friend!=null){
            this.friend.setFriendNorma(this.friend.getFriendNorma()+this.normachange);
            context.setPacketHandled(true);
        }
    }

    public static Friend decodeBuffer(Level level, int i) {
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }
}
