package com.usagin.juicecraft.friends;

import com.usagin.juicecraft.FriendMenu;
import com.usagin.juicecraft.data.CombatSettings;
import com.usagin.juicecraft.data.DialogueTree;
import com.usagin.juicecraft.data.Relationships;
import net.minecraft.world.Container;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.internal.ForgeBindings;
import org.jetbrains.annotations.Nullable;

public class Sora extends Friend{
    public final AnimationState idleAnimState = new AnimationState();
    public Sora(EntityType<? extends Wolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }
    public static AttributeSupplier.Builder getSoraAttributes(){
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH,100).add(Attributes.MOVEMENT_SPEED,0.1).add(Attributes.ATTACK_DAMAGE, 2).add(ForgeMod.ENTITY_GRAVITY.get(),0);
    }
    @Override
    void setInventoryRows() {
        this.invRows=5;
    }

    @Override
    void setArmorableModular() {
        this.isArmorable=false;
        this.isModular=false;
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
    void indicateTamed() {

    }

    @Override
    void petEvent() {

    }

    @Override
    void doRecoveryEvent() {

    }

    @Override
    DialogueTree parseDialogueTree(int[] dialogue) {
        return null;
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
    int[] convertDialogueTree(DialogueTree dialogue) {
        return new int[0];
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
    @Override
    public void tick() {
        if(level().isClientSide()) {
        this.idleAnimState.animateWhen(!this.walkAnimation.isMoving() && !this.isDescending(), this.tickCount);
        }
        super.tick();
    }
}
