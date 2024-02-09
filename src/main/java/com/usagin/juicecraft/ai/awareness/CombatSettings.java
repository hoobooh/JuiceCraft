package com.usagin.juicecraft.ai.awareness;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import org.slf4j.Logger;

import java.io.Serializable;

public class CombatSettings implements Serializable {
    private static final Logger LOGGER = LogUtils.getLogger();
    public int hyperCondition = 3;
    public int aggression = 3;
    public int willFlee = 1;
    public int defense = 0;
    public int pickup = 0;

    //pickup key
    //0: always pickup
    //1: pickup if exists in inventory
    //2: never pickup

    //aggression key
    //0: never attack
    //1: attack if owner is attacked
    //2: attack if owner attacks
    //3: always attack
    public int hash = 33100;

    public CombatSettings(int a, int b, int c, int d, int e) {
        this.hyperCondition = a;
        this.aggression = b;
        this.willFlee = c;
        this.defense = e;
    }

    public static CombatSettings decodeHash(int h) {
        String temp = String.valueOf(h);


        int i = 5 - temp.length();
        for (int n = 0; n < i; n++) {
            temp = "0" + temp;
        }

        return new CombatSettings(temp.charAt(0) - '0', temp.charAt(1) - '0', temp.charAt(2) - '0', temp.charAt(3) - '0', temp.charAt(4) - '0');
    }

    public boolean getHyperCondition(Friend pFriend) {
        if (this.hyperCondition == 0) {
            if (pFriend.getOwner() != null) {
                return pFriend.getOwner().getHealth() / pFriend.getOwner().getMaxHealth() > 0.25F;
            }
        } else if (this.hyperCondition == 1) {
            return pFriend.getHealth() / pFriend.getMaxHealth() > 0.25F;
        } else if (this.hyperCondition == 2) {
            if (pFriend.getTarget() != null) {
                return EnemyEvaluator.evaluate(pFriend.getTarget()) > pFriend.getFriendExperience() / 2;
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean willFlee(Friend pFriend) {
        switch (this.willFlee) {
            case 0: //never flee
            case 1: //flee if under 25% health
            case 2: //flee based on area diff rating
            case 3: //flee on sight
        }
        return false;
    }

    public boolean wouldDefend(Friend pFriend) {
        switch (this.defense) {
            case 0: //always play defensively
            case 1: //play defensively if <50%
            case 2: //play defensively based on enemy diff rating
            case 3: //never play defensively
        }
        return true;
    }

    public int makeHash() {
        int temp = 0;
        temp += this.hyperCondition;
        temp *= 10;
        temp += this.aggression;
        temp *= 10;
        temp += this.willFlee;
        temp *= 100;
        temp += this.defense;
        this.hash = temp;
        return this.hash;
    }
}
