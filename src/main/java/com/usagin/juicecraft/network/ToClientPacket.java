package com.usagin.juicecraft.network;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
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
