package com.usagin.juicecraft.network;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.slf4j.Logger;

import java.util.Objects;

public class ToServerItemPickupPacket {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final int combatSettings;
    private final int id;
    private Friend friend;

    //should be same order as write apparently
    public ToServerItemPickupPacket(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readVarInt());
    }

    public ToServerItemPickupPacket(int settings, int id) {
        this.combatSettings = settings;
        this.id = id;

    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.combatSettings);
        buffer.writeVarInt(this.id);
    }

    //menu should close in time in case of level change, shouldnt be any sync issues
    public void handle(CustomPayloadEvent.Context context) {
        ServerLevel level = Objects.requireNonNull(context.getSender()).serverLevel();
        this.friend = decodeBuffer(level, this.id);
        if (friend != null) {
            this.friend.setFriendItemPickup(this.combatSettings);
            context.setPacketHandled(true);
        }
    }

    public static Friend decodeBuffer(Level level, int i) {
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }
}
