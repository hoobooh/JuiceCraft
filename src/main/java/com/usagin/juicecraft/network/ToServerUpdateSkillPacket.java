package com.usagin.juicecraft.network;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Objects;

public class ToServerUpdateSkillPacket {
    private final boolean[] enabled;
    private final int cost;
    private final int[] levels;
    private final int id;
    private Friend friend;

    //should be same order as write apparently
    public ToServerUpdateSkillPacket(FriendlyByteBuf buffer) {
        this(buffer.readVarIntArray(), buffer.readVarInt(), getBooleanArray(buffer), buffer.readVarInt());
    }

    public ToServerUpdateSkillPacket(int[] levels, int cost, boolean[] enabled, int id) {
        this.levels = levels;
        this.cost = cost;
        this.enabled = enabled;
        this.id = id;

    }

    public static boolean[] getBooleanArray(FriendlyByteBuf buffer) {
        boolean[] temp = new boolean[6];
        int[] temp2 = buffer.readVarIntArray();
        for (int i = 0; i < 6; i++) {
            temp[i] = temp2[i] == 1;
        }
        return temp;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarIntArray(this.levels);
        buffer.writeVarInt(this.cost);
        int[] temp = new int[6];
        int i = 0;
        for (boolean b : this.enabled) {
            temp[i++] = b ? 1 : 0;
        }
        buffer.writeVarIntArray(temp);
        buffer.writeVarInt(this.id);
    }

    //menu should close in time in case of level change, shouldnt be any sync issues
    public void handle(CustomPayloadEvent.Context context) {
        ServerLevel level = Objects.requireNonNull(context.getSender()).serverLevel();
        this.friend = decodeBuffer(level, this.id);
        if (this.friend != null) {
            if (this.friend.getSkillPoints() >= cost) {
                //LOGGER.info(Arrays.toString(this.levels) +"") ;
                this.friend.setSkillLevels(this.levels);
                this.friend.setSkillPoints(this.friend.getSkillPoints() - cost);
                this.friend.setSkillEnabled(this.enabled);
                context.setPacketHandled(true);
            } else {
                context.setPacketHandled(false);
            }

        }
    }

    public static Friend decodeBuffer(Level level, int i) {
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }
}
