package com.usagin.juicecraft.friends;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import com.usagin.juicecraft.ai.goals.*;
import com.usagin.juicecraft.client.menu.FriendMenuProvider;
import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.Seagull;
import com.usagin.juicecraft.data.*;
import com.usagin.juicecraft.ai.goals.navigation.FriendPathNavigation;
import com.usagin.juicecraft.items.Sweet;
import com.usagin.juicecraft.items.SweetHandler;
import com.usagin.juicecraft.particles.DiceHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

import static com.usagin.juicecraft.Init.ItemInit.GOLDEN_ORANGE;
import static com.usagin.juicecraft.Init.ParticleInit.SUGURIVERSE_LARGE;
import static com.usagin.juicecraft.Init.UniversalSoundInit.*;
import static net.minecraft.core.particles.ParticleTypes.HEART;
import static net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK;
import static net.minecraft.world.entity.Pose.*;
import static net.minecraft.world.item.Items.AIR;

public abstract class Friend extends FakeWolf implements ContainerListener, MenuProvider {
    int captureDifficulty;
    int hungerMeter;
    public final AnimationState idleAnimState = new AnimationState();
    public final AnimationState patAnimState = new AnimationState();
    public final AnimationState idleAnimStartState = new AnimationState();
    public final AnimationState sitAnimState = new AnimationState();
    public final AnimationState sitPatAnimState = new AnimationState();
    public final AnimationState sitImpatientAnimState = new AnimationState();
    public final AnimationState sleepAnimState = new AnimationState();
    public final AnimationState attackAnimState = new AnimationState();
    public final AnimationState deathAnimState = new AnimationState();
    public final AnimationState deathStartAnimState = new AnimationState();
    public static final Map<Pose, EntityDimensions> POSES = ImmutableMap.<Pose, EntityDimensions>builder().put(STANDING, EntityDimensions.scalable(0.6F, 1.8F)).put(SITTING, EntityDimensions.scalable(0.6F, 1.1F)).put(Pose.SLEEPING, EntityDimensions.scalable(0.6F, 0.5F)).build();
    public int impatientCounter = 0;
    public int deathAnimCounter;
    private int enemiesKilled=0;
    private int itemsCollected=0;
    private float experience=0;
    private int norma=1;
    int aggression;
    public int mood;
    public boolean canDoThings = true;
    public boolean isDying = false;
    public boolean isSitting;
    public int recoveryDifficulty;
    public int deathCounter;
    public int attackCounter;
    public int attackType;
    int time;
    public int blinkCounter = 150;
    public int soundCounter = 40;
    public int patCounter = 20;
    public int idleCounter = 0;
    public int deathTimer = 199;
    int invRows;
    boolean isArmorable;
    boolean isModular;
    String name;
    boolean isShaking;
    public float volume = 0.5F;
    float shakeAnim;
    float shakeAnimO;
    public SimpleContainer inventory = new SimpleContainer(16);
    float[] home;
    Relationships relationships;
    int[] dialogueTree = new int[300];
    public CombatSettings combatSettings;
    SumikaMemory oldMemory;

    public String getFriendName() {
        return this.name;
    }

    public Friend(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        initializeNew();
        if (!pLevel.isClientSide()) {
            registerCustomGoals();
        }
        if (this.level().isClientSide()) {
            this.inventory.setItem(1, this.getFriendWeapon());
        }
    }

    void initializeNew() {
        this.setHungerMeter(100);
        this.setRecoveryDifficulty();
        this.setCaptureDifficulty();
        this.setCaptureDifficulty();
        this.deathCounter = 7 - this.recoveryDifficulty;
        this.setAggression();
        this.mood = 100;
        this.time = 0;
        this.socialInteraction = 100;
        this.setPersistenceRequired();
        this.setName();
        this.home = new float[4];
        this.initializeDialogueSettings();
        this.relationships = new Relationships();
        this.combatSettings = new CombatSettings(4,3,1,0,0);
        this.oldMemory = new SumikaMemory();
        this.setInventoryRows();
        this.setArmorableModular();
        ((FriendPathNavigation) this.getNavigation()).setCanOpenDoors(true);

        this.createInventory();
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        return new FriendPathNavigation(this, pLevel);
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(7 + this.getInventoryRows() * 5);
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }
        this.inventory.addListener(this);
        this.updateContainerEquipment();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.inventory));
    }

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.core.Direction facing) {
        if (this.isAlive() && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (itemHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = itemHandler;
            itemHandler = null;
            oldHandler.invalidate();
        }
    }

    void doMeleeAttack() {
        int rand = this.random.nextInt(3);

        if (rand == 0) { //heavy
            this.setAttackCounter(40);
            this.setAttackType(40);
        } else if (rand == 1) { //med
            this.setAttackCounter(20);
            this.setAttackType(20);
        } else { //light
            this.setAttackCounter(10);
            this.setAttackType(10);
        }
    }
    public void playTimedVoice(SoundEvent sound){
        if(this.soundCounter>=50){
            this.playVoice(sound);
        }
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
        this.getEntityData().set(FRIEND_ATTACKTYPE, attackType);
    }

    public int getAttackType() {
        return this.getEntityData().get(FRIEND_ATTACKTYPE);
    }

    public void doHurtTarget() {
        AABB hitTracer = new AABB(this.getX() - 1.5, this.getY(), this.getZ() - 1.5, this.getX() + 1.5, this.getY() + 1, this.getZ() + 1.5);
        List<Entity> entityList = this.level().getEntities(this, hitTracer);
        if (this.getTarget() != null) {
            this.lookAt(this.getTarget(), 360, 360);
        }
        double angle = Math.atan2(this.getLookAngle().z, this.getLookAngle().x);
        angle = Math.toDegrees(angle);
        double maxFov;
        if (this.attackType == 40 || this.attackType==50) {
            maxFov = 50;
        } else if (this.attackType == 20) {
            maxFov = 40;
        } else {
            maxFov = 30;
        }
        for (Entity e : entityList) {
            if (e instanceof LivingEntity lEntity) {
                if (this.isOwnedBy(lEntity)) {
                    continue;
                }
            }
            double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
            entityAngle = Math.toDegrees(entityAngle);
            if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                this.doHurtTarget(e);
            }
        }
        this.playVoice(this.getAttack());
        if(!this.inventory.getItem(1).isEmpty()){
            this.playSound(this.getHitSound(),0.5F,1);
        }
        this.inventory.getItem(1).hurtAndBreak(1, this, (a) -> this.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        LOGGER.info("c");
        this.updateGear();
    }
    SoundEvent getHitSound(){
        if(this.attackType==10){
            return LIGHT_ATTACK.get();
        }
        else if(this.attackType==20){
            return MEDIUM_ATTACK.get();
        }
        else if(this.attackType==40){
            return HEAVY_ATTACK.get();
        }else{
            return COUNTER_ATTACK.get();
        }
    }
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if(pEntity.level() instanceof ServerLevel t){
            t.sendParticles(SWEEP_ATTACK,pEntity.getX(),pEntity.getY()+1,pEntity.getZ(),1,0.2,0.2,0.2, 0.3);
        }
        boolean flag = false;
        if (pEntity != null) {
            if (this.distanceTo(pEntity) < 3) {
                float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * this.getAttackType() / 20;
                float f1 = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                if (pEntity instanceof LivingEntity) {
                    f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity) pEntity).getMobType());
                    f1 += (float) EnchantmentHelper.getKnockbackBonus(this);
                }

                int i = EnchantmentHelper.getFireAspect(this);
                if (i > 0) {
                    pEntity.setSecondsOnFire(i * 4);
                }

                flag = pEntity.hurt(this.damageSources().mobAttack(this), f);
                if (flag) {
                    if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                        ((LivingEntity) pEntity).knockback((double) (f1 * 0.5F), (double) Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), (double) (-Mth.cos(this.getYRot() * ((float) Math.PI / 180F))));
                        this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                    }

                    this.doEnchantDamageEffects(this, pEntity);
                    this.setLastHurtMob(pEntity);
                }
            }
        }
        return flag;
    }

    public void broadcastVoiceToNearby(String string) {
        AABB nearby = new AABB(this.getX() - 5, this.getY() - 5, this.getZ() - 5, this.getX() + 5, this.getY() + 5, this.getZ() + 5);
        List<Entity> entityList = this.level().getEntities(this, nearby);
        for (Entity e : entityList) {
            if (e instanceof Player pPlayer) {
                pPlayer.displayClientMessage(Component.literal(this.getName().getString() + ": " + Component.translatable(string).getString()), true);
            }
        }
    }

    abstract void setInventoryRows();

    abstract void setArmorableModular();

    abstract void initializeDialogueSettings();

    abstract void setName();

    abstract void setAggression();

    abstract void setCaptureDifficulty();

    abstract void setRecoveryDifficulty();

    public int getRecoveryDifficulty() {
        this.setRecoveryDifficulty();
        return this.recoveryDifficulty;
    }

    abstract void indicateTamed();

    public void spawnHorizontalParticles() {
        if (this.level() instanceof ServerLevel pLevel) {
            pLevel.sendParticles(SUGURIVERSE_LARGE.get(), this.getX(), this.getY() + 1, this.getZ(), 1, 0.0D, 0, 0.0D, 1);
        }
    }

    void petEvent() {
        this.playTimedVoice(this.getPat());
        patCounter = 20;
    }

    public void playVoice(SoundEvent sound) {
        if(!this.level().isClientSide()){
        this.soundCounter = 0;
        if (sound != null) {
            this.broadcastVoiceToNearby(sound.getLocation().getNamespace() + "." + sound.getLocation().getPath());
            this.playSound(sound, this.volume, 1);
        }}
    }

    void doRecoveryEvent() {
        this.setHealth(this.getMaxHealth() / 2);
        this.deathCounter = 7 - recoveryDifficulty;
        this.getEntityData().set(FRIEND_ISDYING, false);
        this.isDying = false;
        this.playSound(RECOVERY.get(),1,1);
        this.playVoice(this.getRecovery());
        this.spawnHorizontalParticles();
    }

    void doDyingEvent() {
        this.playVoice(getRecoveryFail());
        if (this.deathCounter <= 0) {
            this.doDeathEvent();
        }
    }

    public DamageSource deathSource;

    public void doDeathEvent() {
        this.spawnHorizontalParticles();
        this.playSound(FRIEND_DEATH.get(),1,1);
        this.playVoice(this.getDeathSound());
        if(deathSource==null){
            deathSource= new DamageSources(this.level().registryAccess()).generic();
        }
        this.die(deathSource);

        this.setRemoved(RemovalReason.KILLED);
    }

    FriendCombatTracker combatTracker = new FriendCombatTracker(this);

    @Override
    public @NotNull CombatTracker getCombatTracker() {
        return this.combatTracker;
    }

    abstract SoundEvent getIdle();

    abstract SoundEvent getInjured();

    abstract SoundEvent getInteract();

    abstract SoundEvent getPat();

    public abstract SoundEvent getHurt(float dmg);

    public abstract SoundEvent getAttack();

    public abstract SoundEvent getEvade();

    public abstract SoundEvent getBattle();

    abstract public SoundEvent getHyperEquip();

    abstract SoundEvent getHyperUse();

    abstract SoundEvent getRecovery();

    public abstract SoundEvent getOnHeal();

    abstract SoundEvent getRecoveryFail();

    abstract SoundEvent getWarning();

    public abstract SoundEvent getEquip();

    public abstract SoundEvent getModuleEquip();

    float maxhealth;
    float mvspeed;
    float atkdmg;



    abstract Relationships parseRelationships(int[] relations);

    abstract int[] convertRelationships(Relationships relations);

    public boolean isArmorable() {
        return this.isArmorable;
    }

    public boolean hasInventoryChanged(Container pInventory) {
        return this.inventory != pInventory;
    }

    public boolean isModular() {
        return this.isModular;
    }

    public boolean isModule(ItemStack pStack) {
        return pStack.getItem() instanceof ModuleItem;
    }

    public boolean isLivingTame() {
        return this.isAlive() && this.isTame();
    }

    boolean testMood(LivingEntity a) {
        return this.mood < 15;
    }

    public int getInventoryRows() {
        return this.invRows;
    }

    void saveMemory() {
        this.oldMemory.saveDialogue(dialogueTree);
        this.oldMemory.saveCombatSettings(combatSettings);
        this.oldMemory.saveRelationships(relationships);
        this.oldMemory.saveHome(home);
    }

    void loadMemory() {
        this.dialogueTree = this.oldMemory.getDialogueTree();
        this.relationships = this.oldMemory.getRelationships();
        this.combatSettings = this.oldMemory.getCombatSettings();
        this.home = this.oldMemory.getHome();
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }

        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putIntArray("juicecraft.home", new int[]{(int) this.home[0], (int) this.home[1], (int) this.home[2], (int) this.home[3]});
        pCompound.putIntArray("juicecraft.dialogue", this.dialogueTree);
        pCompound.putIntArray("juicecraft.relationships", convertRelationships(this.relationships));
        pCompound.putInt("juicecraft.csettings", this.getCombatSettings().makeHash());
        pCompound.putInt("juicecraft.social", this.socialInteraction);
        pCompound.putInt("juicecraft.mood", this.mood);
        pCompound.putInt("juicecraft.existed", 1);
        pCompound.putBoolean("Tame", this.isTame());
        pCompound.putBoolean("juicecraft.isdying", this.isDying);
        pCompound.putInt("juicecraft.deathcounter", this.deathCounter);
        pCompound.putInt("juicecraft.hungermeter", this.hungerMeter);
        pCompound.putInt("juicecraft.norma", this.norma);
        pCompound.putFloat("juicecraft.experience", this.experience);
        pCompound.putInt("juicecraft.itemscollected", this.itemsCollected);
        pCompound.putInt("juicecraft.enemieskilled", this.enemiesKilled);
        ListTag listtag = new ListTag();

        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte) i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }
        pCompound.put("juicecraft.inventory", listtag);


        if (this.getOwnerUUID() != null) {
            pCompound.putUUID("Owner", this.getOwnerUUID());
        }
        boolean sitting = this.isSitting;

        pCompound.putBoolean("juicecraft.sitting", sitting);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.getInt("juicecraft.existed") == 0) {
            super.readAdditionalSaveData(pCompound);
            this.initializeNew();
            return;
        }
        super.readAdditionalSaveData(pCompound);
        this.initializeNew();
        int[] temp = pCompound.getIntArray("juicecraft.home");
        if (temp.length != 0) {
            this.home = new float[]{temp[0], temp[1], temp[2], temp[3]};
        }
        this.dialogueTree = pCompound.getIntArray("juicecraft.dialogue");
        this.relationships = this.parseRelationships((pCompound.getIntArray("juicecraft.relationships")));
        this.combatSettings = CombatSettings.decodeHash((pCompound.getInt("juicecraft.csettings")));
        this.updateCombatSettings();
        this.socialInteraction = (pCompound.getInt("juicecraft.social"));
        this.mood = (pCompound.getInt("juicecraft.mood"));
        this.setHealth(pCompound.getFloat("Health"));
        this.isDying = (pCompound.getBoolean("juicecraft.isdying"));
        this.deathCounter = (pCompound.getInt("juicecraft.deathcounter"));
        this.setHungerMeter(pCompound.getInt("juicecraft.hungermeter"));
        this.setFriendNorma(pCompound.getInt("juicecraft.norma"));
        this.setFriendExperience(pCompound.getFloat("juicecraft.experience"));
        this.setFriendItemsCollected(pCompound.getInt("juicecraft.itemscollected"));
        this.setFriendEnemiesKilled(pCompound.getInt("juicecraft.enemieskilled"));

        this.setTame(pCompound.getBoolean("Tame"));
        this.createInventory();
        ListTag listtag = pCompound.getList("juicecraft.inventory", 10);
        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.of(compoundtag));
            }
            this.setFriendWeapon(this.inventory.getItem(1));
        }
        UUID uuid;
        if (pCompound.hasUUID("Owner")) {
            uuid = pCompound.getUUID("Owner");
        } else {
            String s = pCompound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }

        if (pCompound.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.of(pCompound.getCompound("SaddleItem"));
            if (itemstack.is(Items.SADDLE)) {
                this.inventory.setItem(0, itemstack);
            }
        }
        this.updateContainerEquipment();
        this.isSitting = pCompound.getBoolean("juicecraft.sitting");
        this.setFriendInSittingPose(this.isSitting);

        this.maxhealth = pCompound.getFloat("juicecraft.maxhealth");
        this.mvspeed = pCompound.getFloat("juicecraft.mvspeed");
        this.atkdmg = pCompound.getFloat("juicecraft.atkdmg");

    }


    public void updateGear() {
        LOGGER.info(this.getFriendWeapon().getDisplayName().getString());
        if (!this.getFriendWeapon().isEmpty()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, this.getFriendWeapon());
        } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AIR));
        }
        if (!this.inventory.getItem(3).isEmpty()) {
            this.setItemSlot(EquipmentSlot.HEAD, this.inventory.getItem(3));
        } else {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AIR));
        }
        if (!this.inventory.getItem(4).isEmpty()) {
            this.setItemSlot(EquipmentSlot.CHEST, this.inventory.getItem(4));
        } else {
            this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AIR));
        }
        if (!this.inventory.getItem(5).isEmpty()) {
            this.setItemSlot(EquipmentSlot.LEGS, this.inventory.getItem(5));
        } else {
            this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(AIR));
        }
        if (!this.inventory.getItem(6).isEmpty()) {
            this.setItemSlot(EquipmentSlot.FEET, this.inventory.getItem(6));
        } else {
            this.setItemSlot(EquipmentSlot.FEET, new ItemStack(AIR));
        }

    }

    public void setDeathAnimCounter(int c) {
        this.deathAnimCounter = c;
        this.getEntityData().set(FRIEND_DEATHCOUNTER, c);
    }

    public int getDeathAnimCounter() {
        return this.getEntityData().get(FRIEND_DEATHCOUNTER);
    }

    public void setIsDying(boolean b) {
        this.isDying = b;
        this.getEntityData().set(FRIEND_ISDYING, b);
    }

    public void setHungerMeter(int hun) {
        this.hungerMeter = hun;
        this.getEntityData().set(FRIEND_HUNGERMETER, hun);
    }

    public int getHungerMeter() {
        return this.getEntityData().get(FRIEND_HUNGERMETER);
    }

    public boolean getIsDying() {
        return this.getEntityData().get(FRIEND_ISDYING);
    }
    public void setFriendItemsCollected(int c) {
        this.itemsCollected = c;
        this.getEntityData().set(FRIEND_ITEMSCOLLECTED, c);
    }

    public int getFriendItemsCollected() {
        return this.getEntityData().get(FRIEND_ITEMSCOLLECTED);
    }
    public void setFriendEnemiesKilled(int c) {
        this.enemiesKilled = c;
        this.getEntityData().set(FRIEND_ENEMIESKILLED, c);
    }

    public int getFriendEnemiesKilled() {
        return this.getEntityData().get(FRIEND_ENEMIESKILLED);
    }
    public void updateCombatSettings(){
        this.getEntityData().set(FRIEND_COMBATSETTINGS,this.combatSettings.makeHash());
    }
    public CombatSettings getCombatSettings(){
        return CombatSettings.decodeHash(this.getEntityData().get(FRIEND_COMBATSETTINGS));
    }
    public void setFriendWeapon(ItemStack wep) {
        this.getEntityData().set(FRIEND_WEAPON, wep);
    }

    public ItemStack getFriendWeapon() {
        return this.getEntityData().get(FRIEND_WEAPON);
    }
    public void setFriendNorma(int n){
        this.norma=n;
        this.getEntityData().set(FRIEND_NORMA,n);
    }
    public int getFriendNorma(){
        return this.getEntityData().get(FRIEND_NORMA);
    }
    public void setFriendExperience(float n){
        this.experience=n;
        this.getEntityData().set(FRIEND_LEVEL,n);
    }
    public float getFriendExperience(){
        return this.getEntityData().get(FRIEND_LEVEL);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_FLAGS, (byte) 0);
        this.entityData.define(FRIEND_ISDYING, this.isDying);
        this.entityData.define(FRIEND_ISSITTING, this.isSitting);
        this.entityData.define(FRIEND_ATTACKCOUNTER, this.attackCounter);
        this.entityData.define(FRIEND_DEATHCOUNTER, this.deathAnimCounter);
        this.entityData.define(FRIEND_ATTACKTYPE, this.attackType);
        this.inventory = new SimpleContainer(16);
        this.entityData.define(FRIEND_WEAPON, this.inventory.getItem(1));
        this.entityData.define(SLEEPING_POS_ID, Optional.empty());
        this.combatSettings=new CombatSettings(4,3,1,0,0);
        this.entityData.define(FRIEND_COMBATSETTINGS, this.combatSettings.hash);
        this.entityData.define(FRIEND_HUNGERMETER, this.hungerMeter);
        this.entityData.define(FRIEND_NORMA, this.norma);
        this.entityData.define(FRIEND_LEVEL, this.experience);
        this.entityData.define(FRIEND_ITEMSCOLLECTED, this.itemsCollected);
        this.entityData.define(FRIEND_ENEMIESKILLED, this.enemiesKilled);
    }
    public static final EntityDataAccessor<Integer> FRIEND_COMBATSETTINGS = SynchedEntityData.defineId(Friend.class,EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Boolean> FRIEND_ISDYING = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> FRIEND_ISSITTING = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FRIEND_ATTACKCOUNTER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_HUNGERMETER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ITEMSCOLLECTED = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ENEMIESKILLED = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_DEATHCOUNTER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ATTACKTYPE = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_NORMA = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> FRIEND_LEVEL = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> FRIEND_WEAPON = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Optional<BlockPos>> SLEEPING_POS_ID = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

    protected void setFlag(int pFlagId, boolean pValue) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);

        if (pValue) {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 | pFlagId));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 & ~pFlagId));
        }

    }

    public void setAttackCounter(int time) {
        this.attackCounter = time;
        this.getEntityData().set(FRIEND_ATTACKCOUNTER, time);
    }

    public int getAttackCounter() {
        return this.getEntityData().get(FRIEND_ATTACKCOUNTER);
    }

    private SlotAccess createEquipmentSlotAccess(final int pSlot, final Predicate<ItemStack> pStackFilter) {
        return new SlotAccess() {
            public ItemStack get() {
                return inventory.getItem(pSlot);
            }

            public boolean set(ItemStack p_149528_) {
                if (!pStackFilter.test(p_149528_)) {
                    return false;
                } else {
                    inventory.setItem(pSlot, p_149528_);
                    updateContainerEquipment();
                    return true;
                }
            }
        };
    }

    protected void updateContainerEquipment() {
        if (!this.level().isClientSide) {
            this.setFlag(4, !this.inventory.getItem(0).isEmpty());
        }
    }

    int socialInteraction;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        //this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        //this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(4, new FriendLadderClimbGoal(this));
        this.goalSelector.addGoal(5, new FriendMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FriendSleepGoal(this));
        this.goalSelector.addGoal(2, new FriendSitGoal(this));
        this.goalSelector.addGoal(8, new FriendFollowGoal(this, 1D, 10.0F, 2.0F, false));
        //this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D)); //...maybe in the future...
        this.goalSelector.addGoal(9, new FriendWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new FriendBegGoal(this, 8.0F));
        this.goalSelector.addGoal(11, new FriendLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new FriendRandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new FriendOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new FriendOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new FriendNearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new FriendNearestAttackableTargetGoal<>(this, Seagull.class, true));
        this.targetSelector.addGoal(9, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    void registerCustomGoals() {
        if (this.aggression > 75) {
            this.targetSelector.addGoal(6, new FriendNearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::testMood));
            this.targetSelector.addGoal(7, new FriendNearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> p_28879_ instanceof Enemy));
        } else if (this.aggression > 49) {
            this.targetSelector.addGoal(7, new FriendNearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper)));
        } else {
            this.targetSelector.addGoal(7, new AvoidEntityGoal<>(this, Mob.class, 5, 1, 1, (p_28879_) -> p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper)));
        }
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public void reapplyPosition() {
        this.setPos(this.getX(), this.getY(), this.getZ());
    }

    public void setFriendInSittingPose(boolean sit) {
        this.isSitting = sit;
        this.entityData.set(FRIEND_ISSITTING, sit);
    }

    public boolean getInSittingPose() {
        return this.entityData.get(FRIEND_ISSITTING);
    }

    public boolean isEdible(ItemStack pStack) {
        return pStack.is(ItemInit.ORANGE.get()) || pStack.is(ItemInit.GOLDEN_ORANGE.get()) || pStack.getItem() instanceof Sweet;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        double totalZ = this.getZ() - pPlayer.getZ();
        double totalX = this.getX() - pPlayer.getX();
        double totalY = this.getY() + this.getBbHeight() - 0.4 - pPlayer.getBbHeight() - pPlayer.getY();
        double lookRot = pPlayer.getXRot();

        double worstRot = Math.atan2(totalY, Math.sqrt(totalX * totalX + totalZ * totalZ));
        worstRot = -Math.toDegrees(worstRot);
        if (lookRot < worstRot) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (this.level().isClientSide) {
                boolean flag = (this.isOwnedBy(pPlayer) && this.isTame()) || this.isEdible(itemstack);
                if (this.isOwnedBy(pPlayer) || this.isTame()) {
                    if (itemstack.isEmpty() && !pPlayer.isCrouching()) {
                        this.patCounter = 20;
                    } else if (itemstack.isEmpty()) {
                        this.idleCounter = 0;
                    }
                }
                return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else if (this.isTame() && this.isOwnedBy(pPlayer)) {
                if (this.isEdible(itemstack)) {
                    SweetHandler.doSweetEffect(this, itemstack);
                    this.socialInteraction += 1;
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    this.gameEvent(GameEvent.EAT);
                    return InteractionResult.SUCCESS;
                } else if (itemstack.is(ItemInit.SUMIKA_MEMORY.get())) {
                    this.loadMemory();
                    this.doRecoveryEvent(); //recovery animation
                    return InteractionResult.SUCCESS;
                }else{

                    if (!itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {

                        if (pPlayer instanceof ServerPlayer serverPlayer) {

                            this.playVoice(this.getInteract());
                            serverPlayer.openMenu(new FriendMenuProvider(this), buffer -> buffer.writeVarInt(this.getId()));

                        }
                        return InteractionResult.SUCCESS;
                    } else if ((itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching())||(!itemstack.isEmpty() && this.isOwnedBy(pPlayer) && !pPlayer.isCrouching())) {
                        this.setFriendInSittingPose(!this.getInSittingPose());
                        if (sleeping() && animateSleep()) {
                            this.setPose(Pose.SLEEPING);
                        } else {
                            this.setPose(STANDING);
                        }
                    } else if (itemstack.isEmpty() && this.isOwnedBy(pPlayer)) {
                        if (this.mood > 50) {
                            petEvent();
                            if (this.random.nextInt(20) == 6) {
                                if (this.mood <= 80) {
                                    this.mood += 20;
                                    if (this.level() instanceof ServerLevel sLevel) {
                                        for (int i = 0; i < 5; i++) {
                                            sLevel.sendParticles(HEART, this.getX(), this.getY() + 1, this.getZ(), 1, this.random.nextInt(-1, 2), this.random.nextInt(-1, 2), this.random.nextInt(-1, 2), 0.5);
                                        }

                                    }
                                } else {
                                    this.mood = 100;
                                    if (this.level() instanceof ServerLevel sLevel) {
                                        for (int i = 0; i < 5; i++) {
                                            sLevel.sendParticles(HEART, this.getX(), this.getY() + 1, this.getZ(), 1, this.random.nextInt(-1, 2), this.random.nextInt(-1, 2), this.random.nextInt(-1, 2), 0.5);
                                        }
                                    }
                                }
                            }
                        } else {
                            this.level().broadcastEntityEvent(this, (byte) 6);
                            this.mood--;
                        }
                        this.socialInteraction++;
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if ((itemstack.is(ItemInit.ORANGE.get()) || itemstack.is(GOLDEN_ORANGE.get())) && !this.isAngry() && this.mood == 100) {
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (captureDifficulty > 1 && itemstack.is(GOLDEN_ORANGE.get())) {
                    captureDifficulty--;
                }
                if (this.random.nextInt(captureDifficulty) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                    this.tame(pPlayer);
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    this.indicateTamed();
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    if (aggression < 50) {
                        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 50, 1, 1));
                    } else if (aggression > 75) {
                        this.setRemainingPersistentAngerTime(24000);
                    }
                    this.mood = 0;
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }
        else {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if(!this.level().isClientSide()){
            if (this.isTame() && this.isOwnedBy(pPlayer)) {

                if (!itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {

                    if (pPlayer instanceof ServerPlayer serverPlayer) {

                        this.playVoice(this.getInteract());
                        serverPlayer.openMenu(new FriendMenuProvider(this), buffer -> buffer.writeVarInt(this.getId()));

                    }
                    return InteractionResult.SUCCESS;
                } else if (itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {
                    this.setFriendInSittingPose(!this.getInSittingPose());
                    if (sleeping() && animateSleep()) {
                        this.setPose(Pose.SLEEPING);
                    } else {
                        this.setPose(STANDING);
                    }
                }
            }}else{
                boolean flag = (this.isOwnedBy(pPlayer) && this.isTame()) || this.isEdible(itemstack);
                if (this.isOwnedBy(pPlayer) || this.isTame()) {
                    if (itemstack.isEmpty() && !pPlayer.isCrouching()) {
                        this.patCounter = 20;
                    } else if (itemstack.isEmpty()) {
                        this.idleCounter = 0;
                    }
                }
                return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
            }
            return InteractionResult.PASS;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //shaking anim, work on later
        if (!this.level().isClientSide && this.isWet() && !this.isShaking && !this.isPathFinding() && this.onGround()) {
            this.isShaking = true;
            this.shakeAnim = 0.0F;
            this.shakeAnimO = 0.0F;
            this.level().broadcastEntityEvent(this, (byte) 8);
        }
        if (!this.level().isClientSide()) {
            if (this.tickCount % 80 == 0) {
                if (this.random.nextInt(3) == 2 && this.getPose() != SLEEPING && !this.getIsDying()) {
                    this.playVoice(this.getIdle());
                }
            }
        }
        if (this.aggression < 50) {
            if (this.time % 100 == 0) {
                this.time = 0;
                if (mood != 100) {
                    this.mood++;
                }
                if (mood != 1) {
                    if (this.isTame()) {
                        this.socialInteraction--;
                        mood -= 2;
                    }
                }
            }
        } else if (this.aggression < 75) {
            if (this.time % 200 == 0) {
                this.time = 0;
                if (this.mood != 100) {
                    this.mood++;
                }
                if (mood != 1) {
                    if (this.isTame()) {
                        this.socialInteraction--;
                        mood -= 2;
                    }
                }
            }
        } else {
            if (this.time % 300 == 0) {
                this.time = 0;
                if (this.mood != 100) {
                    this.mood++;
                }
                if (mood != 1) {
                    if (this.isTame()) {
                        this.socialInteraction--;
                        mood -= 2;
                    }
                }
            }
        }
        if (socialInteraction == 0) {
            this.setTame(false);
            this.setOwnerUUID(null);
            this.socialInteraction = 100;
            this.mood = 0;
            this.captureDifficulty *= 2;
        }
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
        this.time++;
        if (blinkCounter == 0) {
            blinkCounter = 150;
        }
        if (soundCounter < 50) {
            soundCounter++;
        }
        blinkCounter--;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.getIdle();
    }

    @Override
    public void playAmbientSound() {
        //do nothing
    }

    @Override
    protected float getSoundVolume() {
        return this.volume;
    }

    @Override
    public float getVoicePitch() {
        return 1F;
    }

    public boolean day() {
        long time = this.level().getDayTime();

        return time < 12300 || time > 23850;
    }


    public boolean idle() {
        return this.walkAnimation.speed() < 0.2 && !this.isDescending() && !this.isAggressive();
    }

    public boolean sleeping() {
        return idle() && !this.day();
    }

    public boolean animateSleep() {
        return this.getFeetBlockState().isBed(this.level(), new BlockPos(this.getBlockX(), this.getBlockY() - 1, this.getBlockZ()), null);
    }

    int n = 5;

    @Override
    public void tick() {
        if (this.patCounter > 0) {
            this.idleCounter = 0;
            this.patCounter--;
        }
        if (this.mood > 100) {
            this.mood = 100;
        }
        if (this.impatientCounter > 0) {
            impatientCounter--;
        }
        if (this.getAttackCounter() == 22 && this.getAttackType() == 40) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == 8 && this.getAttackType() == 20) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == 8 && this.getAttackType() == 10) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == 24 && this.getAttackType() == 50) {
            this.doHurtTarget();
        }
        if (this.getAttackCounter() != 0) {
            this.setAttackCounter(this.getAttackCounter() - 1);
        }
        if(this.isAggressive()){
            this.aggroCounter=20;
        }
        else if(this.aggroCounter>0){
            this.aggroCounter--;
        }
        if (level().isClientSide()) {
            if (this.getPose() == STANDING && !idle() && idleCounter > 0) {
                this.idleCounter = 0;
            }
            if (this.level().getGameTime() % 150 == 0 && this.getInSittingPose()) {
                this.impatientCounter = 100;
            } else if (!this.getInSittingPose() || this.patCounter != 0) {
                this.impatientCounter = 0;
            }
            if (this.tickCount%20==0){
                LOGGER.info("d");
                this.updateGear();
            }
            boolean sit = this.getInSittingPose();
            this.deathStartAnimState.animateWhen(this.getIsDying() && this.getDeathAnimCounter() != 0, this.tickCount);
            this.deathAnimState.animateWhen(this.getIsDying() && this.getDeathAnimCounter() == 0, this.tickCount);
            this.idleAnimState.animateWhen(!sit && idle() && this.idleCounter == 20 && this.patCounter == 0, this.tickCount);
            this.idleAnimStartState.animateWhen(!sit && idle() && this.idleCounter < 20 && this.patCounter == 0, this.tickCount);
            this.patAnimState.animateWhen(this.patCounter > 0 && !this.walkAnimation.isMoving() && !this.isDescending(), this.tickCount);
            this.sitPatAnimState.animateWhen(this.getPose() == SITTING && this.patCounter != 0, this.tickCount);
            this.sitAnimState.animateWhen(this.getPose() == SITTING && this.patCounter == 0 && this.impatientCounter == 0, this.tickCount);
            this.sitImpatientAnimState.animateWhen(this.getPose() == SITTING && this.patCounter == 0 && this.impatientCounter != 0, this.tickCount);
            this.attackAnimState.animateWhen(this.getAttackCounter() != 0, this.tickCount);
            this.sleepAnimState.animateWhen(true, this.tickCount);
            if (this.getPose() == STANDING && this.idle() && idleCounter < 20) {
                this.idleCounter++;
            }
        } else {
            if (this.deathAnimCounter != 0) {
                setDeathAnimCounter(this.deathAnimCounter - 1);
            }
            if (isDying) {
                this.setTarget(null);
                if (deathTimer == 100) {
                    n = this.random.nextInt(1, 7);
                    ServerLevel sLevel = (ServerLevel) this.level();
                    sLevel.sendParticles(DiceHandler.getDice(n), this.getX(), this.getY() + 2.5, this.getZ(), 1, 0, 0, 0, 0.1);
                }
                if (deathTimer == 80 && n >= this.getRecoveryDifficulty()) {
                    doRecoveryEvent();
                } else if (deathTimer == 80) {
                    deathCounter--;
                    this.doDyingEvent();
                    LOGGER.info(this.name + " is dying.");
                }
                deathTimer--;
                if (deathTimer == 0) {
                    deathTimer = 100;
                }

            } else {
                deathTimer = 200;
            }
            if (this.hungerMeter > 0) {
                if (this.tickCount % 200 == 0) {
                    this.setHungerMeter(this.hungerMeter - 1);
                }
            } else {
                if (this.tickCount % 200 == 0) {
                    this.mood -= 20;
                }
            }
        }
        super.tick();
    }
    public int aggroCounter=0;
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean b;
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            this.setFriendInSittingPose(false);
            Entity entity = pSource.getEntity();
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }
            b = super.hurt(pSource, pAmount);
        }
        if (this.isDying) {
            this.setPersistentAngerTarget(null);
        }
        return b;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource dmgsrc) {
        return SoundEvents.PLAYER_HURT;

    }

    @Override
    public @NotNull Vec3 handleRelativeFrictionAndCalculateMovement(@NotNull Vec3 pDeltaMovement, float pFriction) {
        this.moveRelative(this.getFrictionInfluencedSpeed(pFriction), pDeltaMovement);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());

        return this.getDeltaMovement();
    }

    private Vec3 handleOnClimbable(Vec3 pDeltaMovement) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            float f = 0.15F;
            double d0 = Mth.clamp(pDeltaMovement.x, (double) -0.15F, (double) 0.15F);
            double d1 = Mth.clamp(pDeltaMovement.z, (double) -0.15F, (double) 0.15F);
            double d2 = Math.max(pDeltaMovement.y, (double) -0.15F);

            pDeltaMovement = new Vec3(d0, d2, d1);
        }
        return pDeltaMovement;
    }

    private float getFrictionInfluencedSpeed(float pFriction) {
        return this.onGround() ? this.getSpeed() * (0.21600002F / (pFriction * pFriction * pFriction)) : this.getFlyingSpeed();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return POSES.getOrDefault(pPose, new EntityDimensions(0.6F, 1.8F, false));
    }


    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        ItemStack stack = this.getItemInHand(pHand);
        if(this.getAttackCounter()==0 && !this.getIsDying()){
            if (!stack.isEmpty()) {
                this.doMeleeAttack();
            } else {
                this.setAttackCounter(10);
                this.setAttackType(10);
            }
        }
    }

    @Override
    public boolean isSleeping() {
        return this.getPose() == Pose.SLEEPING;
    }

    @Nullable
    @Override
    public Direction getBedOrientation() {
        BlockPos blockpos = this.getOnPos();
        BlockState state = this.getFeetBlockState();
        return !state.isBed(level(), blockpos, this) ? Direction.UP : state.getBedDirection(level(), blockpos);
    }

    @Override
    public void stopSleeping() {/*yucky method*/}
    @Override
    public void setTame(boolean pTamed) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (pTamed) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -5));
        }

        this.reassessTameGoals();
    }
    @Override
    public SoundEvent getDeathSound(){
        return this.getRecoveryFail();
    }
}
