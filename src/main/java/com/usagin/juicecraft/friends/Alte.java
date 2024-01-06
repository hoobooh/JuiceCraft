package com.usagin.juicecraft.friends;

import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Alte extends Friend{
    public Alte(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    int[] getSkillInfo() {
        return new int[0];
    }

    @Override
    void setInventoryRows() {

    }

    @Override
    void setArmorableModular() {

    }

    @Override
    void initializeDialogueSettings() {

    }

    @Override
    void setName() {

    }

    @Override
    void setAggression() {

    }

    @Override
    void setCaptureDifficulty() {

    }

    @Override
    void setRecoveryDifficulty() {

    }

    @Override
    void indicateTamed() {

    }

    @Override
    public SoundEvent getLaugh() {
        return null;
    }

    @Override
    public SoundEvent getAngry() {
        return null;
    }

    @Override
    public SoundEvent getFlee() {
        return null;
    }

    @Override
    public SoundEvent getIdle() {
        return null;
    }

    @Override
    public SoundEvent getInjured() {
        return null;
    }

    @Override
    public SoundEvent getInteract() {
        return null;
    }

    @Override
    public SoundEvent getPat() {
        return null;
    }

    @Override
    public SoundEvent getHurt(float dmg) {
        return null;
    }

    @Override
    public SoundEvent getAttack() {
        return null;
    }

    @Override
    public SoundEvent getEvade() {
        return null;
    }

    @Override
    public SoundEvent getBattle() {
        return null;
    }

    @Override
    public SoundEvent getHyperEquip() {
        return null;
    }

    @Override
    public SoundEvent getHyperUse() {
        return null;
    }

    @Override
    public SoundEvent getRecovery() {
        return null;
    }

    @Override
    public SoundEvent getOnHeal() {
        return null;
    }

    @Override
    public SoundEvent getRecoveryFail() {
        return null;
    }

    @Override
    public SoundEvent getWarning() {
        return null;
    }

    @Override
    public SoundEvent getEquip() {
        return null;
    }

    @Override
    public SoundEvent getModuleEquip() {
        return null;
    }

    @Override
    public AbstractDialogueManager getDialogueManager() {
        return null;
    }

    @Override
    void registerAdditionalGoals() {

    }

    @Override
    public void containerChanged(Container pContainer) {

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
