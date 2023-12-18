package com.usagin.juicecraft.friends;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.menu.FriendMenu;
import com.usagin.juicecraft.data.CombatSettings;
import com.usagin.juicecraft.data.Relationships;
import com.usagin.juicecraft.goals.SoraHyperGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import static com.usagin.juicecraft.Init.SoraSoundInit.*;


public class Sora extends Friend{
    private static final Logger LOGGER = LogUtils.getLogger();
    public Sora(EntityType<? extends Wolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier.Builder getSoraAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 25).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 2);
    }

    @Override
    void setInventoryRows() {
        this.invRows=5;
    }

    @Override
    void setArmorableModular() {
        this.isArmorable=true;
        this.isModular=false;
    }
    @Override
    void registerCustomGoals(){
        super.registerCustomGoals();
        this.goalSelector.addGoal(2,new SoraHyperGoal(!this.inventory.getItem(0).isEmpty(),this.combatSettings));
    }
    @Override
    void initializeDialogueSettings() {

    }

    @Override
    void setName() {
        this.name="Sora";
    }

    @Override
    void setAggression() {
        this.aggression=50;
    }

    @Override
    void setCaptureDifficulty() {
        this.captureDifficulty=10;
    }

    @Override
    void setRecoveryDifficulty() {
        this.recoveryDifficulty=5;
    }

    @Override
    void indicateTamed() {

    }
    @Override
    SoundEvent getIdle() {
        if(this.sleeping() && this.animateSleep() && !this.getInSittingPose()){
            return null;
        }
        if(this.isAggressive()){
            return getBattle();
        }
        if(this.getHealth()<this.getMaxHealth()/2){
            return getInjured();
        }
        int a=this.random.nextInt(10);
        if(a==5&&!this.level().isDay()){
            return SORA_IDLE_NIGHT1.get();
        }
        if(a==4&&!this.level().isDay()){
            return SORA_IDLE_NIGHT2.get();
        }
        a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_IDLE2.get();

            case 1 -> SORA_IDLE3.get();

            case 2 -> SORA_IDLE4.get();

            default -> SORA_IDLE1.get();
        };
    }

    @Override
    SoundEvent getInjured() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_INJURED2.get();
            case 1 -> SORA_INJURED3.get();
            case 2 -> SORA_INJURED4.get();
            default -> SORA_INJURED1.get();
        };
    }

    @Override
    SoundEvent getInteract() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_INTERACT2.get();
            case 1 -> SORA_INTERACT3.get();
            case 2 -> SORA_INTERACT4.get();
            default -> SORA_INTERACT1.get();
        };
    }

    @Override
    SoundEvent getPat() {
        int a=this.random.nextInt(3);
        return switch (a) {
            case 0 -> SORA_PAT1.get();
            case 1 -> SORA_PAT2.get();
            default -> SORA_PAT3.get();
        };
    }

    @Override
    public SoundEvent getHurt(float dmg) {
        if(dmg>this.getHealth()*0.2){
            int a=this.random.nextInt(3);
            return switch (a) {
                case 0 -> SORA_GREATLYHURT1.get();
                case 1 -> SORA_GREATLYHURT2.get();
                default -> SORA_GREATLYHURT3.get();
            };
        }
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_HURT1.get();
            case 1 -> SORA_HURT2.get();
            case 2 -> SORA_HURT3.get();
            default -> SORA_HURT4.get();
        };
    }

    @Override
    public SoundEvent getAttack() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_ATTACK1.get();
            case 1 -> SORA_ATTACK2.get();
            case 2 -> SORA_ATTACK3.get();
            default -> SORA_ATTACK4.get();
        };
    }

    @Override
    SoundEvent getEvade() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_EVADE1.get();
            case 1 -> SORA_EVADE2.get();
            case 2 -> SORA_EVADE3.get();
            default -> SORA_EVADE4.get();
        };
    }

    @Override
    public SoundEvent getBattle() {
        int a=this.random.nextInt(8);
        return switch (a) {
            case 0 -> SORA_BATTLE1.get();
            case 1 -> SORA_BATTLE2.get();
            case 2 -> SORA_BATTLE3.get();
            case 3 -> SORA_BATTLE4.get();
            case 4 -> SORA_BATTLE5.get();
            case 5 -> SORA_BATTLE6.get();
            case 6 -> SORA_BATTLE7.get();
            default -> SORA_BATTLE8.get();
        };
    }

    @Override
    public SoundEvent getHyperEquip() {
        return SORA_HYPEREQUIP.get();
    }

    @Override
    SoundEvent getHyperUse() {
        return SORA_HYPERUSE.get();
    }

    @Override
    SoundEvent getRecovery() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_RECOVERY1.get();
            case 1 -> SORA_RECOVERY2.get();
            case 2 -> SORA_RECOVERY3.get();
            default -> SORA_RECOVERY4.get();
        };
    }

    @Override
    public SoundEvent getOnHeal() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_HEAL2.get();
            case 1 -> SORA_HEAL3.get();
            case 2 -> SORA_HEAL4.get();
            default -> SORA_HEAL1.get();
        };
    }

    @Override
    SoundEvent getRecoveryFail() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_RECOVERYFAILED1.get();
            case 1 -> SORA_RECOVERYFAILED2.get();
            case 2 -> SORA_RECOVERYFAILED3.get();
            default -> SORA_RECOVERYFAILED4.get();
        };
    }

    @Override
    SoundEvent getWarning() {
        int a=this.random.nextInt(4);
        return switch (a) {
            case 0 -> SORA_WARNING1.get();
            case 1 -> SORA_WARNING2.get();
            case 2 -> SORA_WARNING3.get();
            default -> SORA_WARNING4.get();
        };
    }

    @Override
    public SoundEvent getEquip() {
        int a=this.random.nextInt(5);
        return switch (a) {
            case 0 -> SORA_EQUIP1.get();
            case 1 -> SORA_EQUIP2.get();
            case 2 -> SORA_EQUIP3.get();
            case 3 -> SORA_EQUIP4.get();
            default -> SORA_EQUIP5.get();
        };
    }
    @Override
    public SoundEvent getModuleEquip() {
        return SORA_MODULEEQUIP.get();
    }



    @Override
    Relationships parseRelationships(int[] relations) {
        return null;
    }

    @Override
    CombatSettings parseCombatSettings(int[] combatSettings) {
        return null;
    }

    @Override
    int[] convertRelationships(Relationships relations) {
        return new int[0];
    }

    @Override
    int[] convertCombatSettings(CombatSettings combatSettings) {
        return new int[0];
    }

    @Override
    public void containerChanged(Container pContainer) {
        this.updateContainerEquipment();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FriendMenu(pContainerId, pPlayerInventory, this);
    }
}
