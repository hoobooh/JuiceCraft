package com.usagin.juicecraft.ai.awareness;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import org.slf4j.Logger;

public class CombatSettings {
    public int hyperCondition=3;
    public int aggression=3;
    public int willFlee=1;
    public int defense=0;
    public int hash=33100;

    //aggression key
    //0: never attack
    //1: attack if owner is attacked
    //2: attack if owner attacks
    //3: always attack

    public CombatSettings(int a, int b, int c, int d, int e){
        this.hyperCondition=a;
        this.aggression=b;
        this.willFlee=c;
        this.defense=e;
    }
    public boolean getHyperCondition(Friend pFriend){
        switch(this.hyperCondition){
            case 0: //use when owner is <25%
            case 1: //use when self is <25%
            case 2: //use based on enemy difficulty rating
            case 3: //use whenever possible.
        }
        return false;
    }
    public boolean willFlee(Friend pFriend){
        switch(this.willFlee){
            case 0: //never flee
            case 1: //flee if under 25% health
            case 2: //flee based on area diff rating
            case 3: //flee on sight
        }
        return false;
    }
    public boolean wouldDefend(Friend pFriend){
        switch(this.defense){
            case 0: //always play defensively
            case 1: //play defensively if <50%
            case 2: //play defensively based on enemy diff rating
            case 3: //never play defensively
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
        temp*=100;
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
