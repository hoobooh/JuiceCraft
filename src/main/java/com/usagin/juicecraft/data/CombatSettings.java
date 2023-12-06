package com.usagin.juicecraft.data;

import com.usagin.juicecraft.friends.Friend;

public class CombatSettings {
    int hyperCondition;
    int aggression;
    int willFlee;
    int attackCreepers;
    int defense;

    public CombatSettings(){
        this.hyperCondition=4;
        this.aggression=2;
        this.willFlee=1;
        this.attackCreepers=0;
        this.defense=0;
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
    public boolean shouldAggro(Friend pFriend){
        switch(this.aggression){
            case 0: //never engage
            case 1: //engage if player is attacked
            case 2: //engage if player attacks
            case 3: //engage on sight
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
}
