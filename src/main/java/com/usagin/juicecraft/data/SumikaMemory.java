package com.usagin.juicecraft.data;

import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.friends.Friend;
import org.apache.commons.lang3.SerializationException;

import java.io.*;

public class SumikaMemory implements Serializable {
    public CombatSettings settings;
    public boolean wander;
    public boolean farm;
    public double normalevel;
    public int[] specialsenabled;
    String ownerid;
    String name;

    public SumikaMemory(Friend friend) {
        this.settings = friend.getCombatSettings();
        this.wander = friend.getIsWandering();
        this.farm = friend.getIsFarming();
        this.normalevel = friend.getFriendNorma();
        this.specialsenabled = friend.getSpecialDialogueEnabled();
        if (friend.getOwner() != null) {
            this.ownerid = friend.getOwner().getStringUUID();
        }
        this.name = friend.getFriendName();
    }

    public static SumikaMemory deserialize(byte[] bytes) {

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return (SumikaMemory) in.readObject();
        } catch (final ClassNotFoundException | IOException ex) {
            throw new SerializationException(ex);
        }
    }

    public byte[] serialize() {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeObject(this);
        } catch (final IOException ex) {
            throw new SerializationException(ex);
        }

        return baos.toByteArray();
    }

    public boolean verifyValid(Friend friend) {
        boolean flag = false;
        if (friend.getOwner() != null) {
            String temp = friend.getOwner().getStringUUID();
            flag = temp.equals(this.ownerid) && friend.getFriendName().equals(this.name);
        }
        return flag;
    }
}
