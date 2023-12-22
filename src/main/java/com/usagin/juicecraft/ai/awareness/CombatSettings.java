package com.usagin.juicecraft.ai.awareness;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import org.slf4j.Logger;

public class CombatSettings {
    public int hyperCondition=4;
    public int aggression=3;
    public int willFlee=1;
    public int attackCreepers=0;
    public int defense=0;
    public int hash=43100;

    //aggression key
    //0: never attack
    //1: attack if owner is attacked
    //2: attack if owner attacks
    //3: always attack

    public CombatSettings(int a, int b, int c, int d, int e){
        this.hyperCondition=a;
        this.aggression=b;
        this.willFlee=c;
        this.attackCreepers=d;
        this.defense=e;
    }
    public boolean getHyperCondition(Friend pFriend){
        switch(this.hyperCondition){
            case 0: //do not use
            case 1: //use when owner is <25%
            case 2: //use when owner is <50%
            case 3: //use when self is <25%
            case 4: //use when self is <50%
            case 5: //use based on enemy number
            case 6: //use based on enemy difficulty rating (health * damage * familiarity (kill count of said entity type))
            case 7: //use whenever possible.
        }
        return false;
    }
    public boolean willFlee(Friend pFriend){
        switch(this.willFlee){
            case 0: //never flee
            case 1: //flee if under 25% health
            case 2: //flee is under 50% health
            case 3: //flee if friend takes any damage
            case 4: //flee if many enemies
            case 5: //flee based on enemy diff rating
            case 6: //flee on sight
        }
        return false;
    }
    public boolean attacksCreepers(){
        return switch (this.attackCreepers) {
            case 0 -> false;
            default -> true;
        };
    }
    public boolean wouldDefend(Friend pFriend){
        switch(this.defense){
            case 0: //always play defensively
            case 1: //play defensively if <25%
            case 2: //play defensively if <50%
            case 3: //play defensively if many enemies
            case 4: //play defensively based on enemy diff rating
            case 5: //never play defensively
        }
        return true;
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    public int makeHash(){
        int temp=0;
        temp+=this.hyperCondition;
        temp*=10;
        temp+=this.aggression;
        temp*=10;
        temp+=this.willFlee;
        temp*=10;
        temp+=this.attackCreepers;
        temp*=10;
        temp+=this.defense;
        this.hash=temp;
        return this.hash;
    }
    public static CombatSettings decodeHash(int h){
        String temp = String.valueOf(h);
        int i=5-temp.length();
        for(int n=0;n<i;n++){
            temp="0"+temp;
        }
        return new CombatSettings(temp.charAt(0)-'0',temp.charAt(1)-'0',temp.charAt(2)-'0',temp.charAt(3)-'0',temp.charAt(4)-'0');
    }
}
