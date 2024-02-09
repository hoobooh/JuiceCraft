package com.usagin.juicecraft.ai.awareness;

import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.syncher.SynchedEntityData;

public class SkillManager {
    public static int makeBooleanHash(boolean[] skills) {
        int temp = 0;
        temp += booleanToInt(skills[0]);
        temp *= 10;
        temp += booleanToInt(skills[1]);
        temp *= 10;
        temp += booleanToInt(skills[2]);
        temp *= 10;
        temp += booleanToInt(skills[3]);
        temp *= 10;
        temp += booleanToInt(skills[4]);
        temp *= 10;
        temp += booleanToInt(skills[5]);
        return temp;
    }

    public static int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static boolean[] decodeBooleanHash(int h) {
        String temp = String.valueOf(h);
        int i = 6 - temp.length();
        for (int n = 0; n < i; n++) {
            temp = "0" + temp;
        }
        return new boolean[]{temp.charAt(0) - '0' == 1, temp.charAt(1) - '0' == 1, temp.charAt(2) - '0' == 1, temp.charAt(3) - '0' == 1, temp.charAt(4) - '0' == 1, temp.charAt(5) - '0' == 1};
    }

    public static int[] assembleSkillLevels(Friend friend) {
        SynchedEntityData data = friend.getEntityData();
        return new int[]{data.get(Friend.FRIEND_SKILLLEVELS1), data.get(Friend.FRIEND_SKILLLEVELS2), data.get(Friend.FRIEND_SKILLLEVELS3), data.get(Friend.FRIEND_SKILLLEVELS4), data.get(Friend.FRIEND_SKILLLEVELS5), data.get(Friend.FRIEND_SKILLLEVELS6),};
    }
}
