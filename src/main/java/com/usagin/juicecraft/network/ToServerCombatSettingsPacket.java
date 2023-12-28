package com.usagin.juicecraft.network;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.slf4j.Logger;

import java.util.Objects;

public class ToServerCombatSettingsPacket {
    private final int combatSettings;
    private Friend friend;
    private final int id;
    private static final Logger LOGGER = LogUtils.getLogger();
    public ToServerCombatSettingsPacket(int settings, int id){
        this.combatSettings=settings;
        this.id=id;

    }
    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(this.combatSettings);
        buffer.writeVarInt(this.id);
    }

    //should be same order as write apparently
    public ToServerCombatSettingsPacket(FriendlyByteBuf buffer){
        this(buffer.readInt(), buffer.readVarInt());
    }
    //menu should close in time in case of level change, shouldnt be any sync issues
    public void handle(CustomPayloadEvent.Context context){
        ServerLevel level = Objects.requireNonNull(context.getSender()).serverLevel();
        this.friend=decodeBuffer(level, this.id);
        if(friend!=null){
            this.friend.combatSettings= CombatSettings.decodeHash(this.combatSettings);
            this.friend.updateCombatSettings();
            context.setPacketHandled(true);
        }
    }

    public static Friend decodeBuffer(Level level, int i) {
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }
}
