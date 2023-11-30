package com.usagin.juicecraft.network;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.inventory.HorseInventoryMenu;
import org.apache.commons.lang3.*;
public class ToClientPacket {
    private final Friend friend;
    ToClientPacket(Friend f){
        this.friend=f;
    }
    ToClientPacket(FriendlyByteBuf buffer){
        this((Friend) SerializationUtils.deserialize(buffer.readByteArray()));
    }
    public void encode(FriendlyByteBuf buffer){
        ObjectOutputStream oos;
        ByteArrayOutputStream bos = null;
        try{
            bos=new ByteArrayOutputStream();
            oos= new ObjectOutputStream(bos);
            oos.writeObject(friend);
            oos.flush();
            }catch(Exception E){
            //do nothing
        }
        assert bos != null;
        buffer.writeByteArray(bos.toByteArray());
    }
}
