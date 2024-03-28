package com.usagin.juicecraft.friends;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.Seagull;
import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.ai.awareness.FriendDefense;
import com.usagin.juicecraft.ai.awareness.SkillManager;
import com.usagin.juicecraft.ai.goals.common.*;
import com.usagin.juicecraft.ai.goals.navigation.FriendPathNavigation;
import com.usagin.juicecraft.client.menu.FriendMenu;
import com.usagin.juicecraft.client.menu.FriendMenuProvider;
import com.usagin.juicecraft.data.FriendCombatTracker;
import com.usagin.juicecraft.data.SumikaMemory;
import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.items.ModuleItem;
import com.usagin.juicecraft.items.SweetHandler;
import com.usagin.juicecraft.items.SweetItem;
import com.usagin.juicecraft.network.CircleParticlePacketHandler;
import com.usagin.juicecraft.network.ToClientCircleParticlePacket;
import com.usagin.juicecraft.particles.DiceHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
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
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

import static com.usagin.juicecraft.Init.ItemInit.GOLDEN_ORANGE;
import static com.usagin.juicecraft.Init.ParticleInit.SUGURIVERSE_LARGE;
import static com.usagin.juicecraft.Init.sounds.UniversalSoundInit.*;
import static net.minecraft.core.particles.ParticleTypes.HEART;
import static net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK;
import static net.minecraft.world.entity.Pose.*;
import static net.minecraft.world.item.Items.AIR;
import static net.minecraft.world.item.Items.COOKIE;

public abstract class Friend extends FakeWolf implements ContainerListener, MenuProvider, RangedAttackMob, CrossbowAttackMob {
    public static final EntityDataAccessor<ItemStack> FRIEND_WEAPON = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<Integer> FRIEND_COMBATMOD = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SWIMCOUNTER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_PLACECOUNTER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_VIEWFLOWER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ITEMPICKUP = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_TIMESINCEPAT = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> FRIEND_EVENTLOG = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> FRIEND_ISFARMING = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> FRIEND_ISWANDERING = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FRIEND_SPECIALSENABLED = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLPOINTS = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLLEVELS1 = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLLEVELS2 = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLLEVELS3 = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLLEVELS4 = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLLEVELS5 = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLLEVELS6 = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_SKILLENABLED = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_COMBATSETTINGS = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> FRIEND_ISDYING = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> FRIEND_ISSITTING = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FRIEND_ATTACKCOUNTER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_HUNGERMETER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ITEMSCOLLECTED = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ENEMIESKILLED = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_DEATHCOUNTER = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FRIEND_ATTACKTYPE = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> FRIEND_NORMA = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> FRIEND_LEVEL = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.FLOAT);
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<BlockPos>> SLEEPING_POS_ID = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    public final AnimationState idleAnimState = new AnimationState();
    public final AnimationState patAnimState = new AnimationState();
    public final AnimationState idleAnimStartState = new AnimationState();
    public final AnimationState inspectAnimState = new AnimationState();
    public final AnimationState sitAnimState = new AnimationState();
    public final AnimationState sitPatAnimState = new AnimationState();
    public final AnimationState sitImpatientAnimState = new AnimationState();
    public final AnimationState sleepAnimState = new AnimationState();
    public final AnimationState attackAnimState = new AnimationState();
    public final AnimationState attackCounterAnimState = new AnimationState();
    public final AnimationState deathAnimState = new AnimationState();
    public final AnimationState deathStartAnimState = new AnimationState();
    public final AnimationState drawBowAnimationState = new AnimationState();
    public final AnimationState wetAnimState = new AnimationState();
    public final AnimationState viewFlowerAnimState = new AnimationState();
    public final AnimationState swimAnimState = new AnimationState();
    public final AnimationState interactAnimState = new AnimationState();
    public final AnimationState snowballIdleAnimState = new AnimationState();
    public final AnimationState snowballIdleTransitionAnimState = new AnimationState();
    public final AnimationState snowballThrowAnimState = new AnimationState();
    public final RangedBowAttackGoal<Friend> bowGoal = new FriendRangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
    public final FriendRangedCrossbowAttackGoal crossbowGoal = new FriendRangedCrossbowAttackGoal(this, 1.0D, 20);
    public final FriendThrowSnowballGoal snowballGoal = new FriendThrowSnowballGoal(this);

    public int homeX;
    public int homeY;
    public int homeZ;
    public int snoozeCounter = 40;
    public int flowercooldown = 300;
    public int viewflower = 0;
    public int itempickup = 0;
    public int[] skillinfo = new int[6];
    public Creeper fleeTarget = null;
    public int combatmodifier = 0;
    public int placecounter = 0;
    public int timesincelastpat = 0;
    public boolean wandering = false;
    public boolean chasingitem = false;
    public int[] skillLevels = new int[6];
    public boolean[] skillEnabled = new boolean[]{false, false, false, false, false, false};
    public Map<Pose, EntityDimensions> POSES = ImmutableMap.<Pose, EntityDimensions>builder().put(STANDING, EntityDimensions.scalable(0.6F, 1.8F)).put(SITTING, EntityDimensions.scalable(0.6F, 1.1F)).put(Pose.SLEEPING, EntityDimensions.scalable(0.6F, 0.5F)).put(SWIMMING, EntityDimensions.scalable(0.6F, 0.9F)).build();
    public int impatientCounter = 0;
    public int runTimer = 0;
    public int aggressiontimer = 0;
    public int animatestandingtimer = 0;
    public int skillPoints = 0;
    public int deathAnimCounter;
    public int aggression;
    public int mood;
    public boolean isDying = false;
    public boolean isSitting;
    public boolean doFarming = true;
    public int recoveryDifficulty;
    public int deathCounter;
    public int attackCounter;
    public int attackType;
    public String eventlog = "";
    public int blinkCounter = 150;
    public int soundCounter = 40;
    public int patCounter = 20;
    public int idleCounter = 0;
    public int deathTimer = 199;
    public float volume = 0.5F;
    public SimpleContainer inventory = new SimpleContainer(16);
    public CombatSettings combatSettings;
    public boolean setupcomplete;
    public int quicksoundcounter = 0;
    public DamageSource deathSource;
    public boolean attackplayertoo = false;
    public int isembarassed = 0;
    public Queue<BlockPos> farmqueue = new LinkedList<>();
    public int aggroCounter = 0;
    public int startfloattimer = 0;
    int captureDifficulty;
    int hungerMeter;
    int maxnorma = 0;
    double[] normaprogress = new double[9];
    double[] normacaps = new double[]{0.1, 0.2, 0.1, 0.2, 0.1, 0.1, 0.1, 0.1};
    int[] specialDialogueEnabled = {0, 0, 0};
    int invRows;
    boolean isArmorable;
    boolean isModular;
    String name;
    boolean isShaking;
    int[] dialogueTree = new int[300];
    FriendCombatTracker combatTracker = new FriendCombatTracker(this);
    float maxhealth;
    float mvspeed;
    float atkdmg;
    int socialInteraction;
    int deathdiceroll = 5;
    boolean wasDay = false;
    private int enemiesKilled = 0;
    private int itemsCollected = 0;
    private float experience = 0;
    private float norma = 1;
    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    public Friend(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setupcomplete = false;
        initializeNew();
        if (this.level().isClientSide()) {
            this.inventory.setItem(1, this.getFriendWeapon());
        }
        this.setCanPickUpLoot(true);
        this.counters=new ArrayList<>();
    }

    void initializeNew() {
        this.setFriendNorma(this.norma, -1);
        this.setHungerMeter(100);
        this.setRecoveryDifficulty();
        this.setCaptureDifficulty();
        this.setCaptureDifficulty();
        this.deathCounter = 7 - this.recoveryDifficulty;
        this.setAggression();
        this.mood = 100;
        this.socialInteraction = 100;
        this.setPersistenceRequired();
        this.setName();
        this.combatSettings = new CombatSettings(4, 3, 1, 0, 0);
        this.setInventoryRows();
        this.setArmorableModular();
        ((FriendPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.setSkillInfo();
        this.createInventory();
    }

    public ItemStack getFriendWeapon() {
        return this.getEntityData().get(FRIEND_WEAPON);
    }

    public void setFriendNorma(float n, int source) {
        this.norma = n;
        int orig = (int) this.getFriendNorma();
        int newone = (int) n;
        if (this.setupcomplete) {
            if (newone > orig && newone < 5 && newone > 1 && newone > maxnorma) {
                this.maxnorma = newone;
                this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.norma" + newone).getString());
                this.playSound(NORMAUP.get(), 1, 1);
            } else if (newone > orig && newone == 5 && this.getOwner() != null && newone > maxnorma) {
                this.maxnorma = newone;
                this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.norma5.0").getString() + this.getOwner().getScoreboardName() + Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.norma5.1").getString());
                this.playSound(NORMAUP.get(), 1, 1);
            }
        }
        this.getEntityData().set(FRIEND_NORMA, n);
    }
    public boolean shouldAfterImage(){return false;};
    abstract void setRecoveryDifficulty();

    abstract void setCaptureDifficulty();

    abstract void setAggression();

    abstract void setName();

    abstract void setInventoryRows();

    abstract void setArmorableModular();

    public void setSkillInfo() {
        this.skillinfo = this.getSkillInfo();
    }

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

    public float getFriendNorma() {
        return this.getEntityData().get(FRIEND_NORMA);
    }

    public void appendEventLog(String s) {
        if (this.getEventLog().length() > 5000) {
            this.setEventLog(this.getEventLog().substring(1000));
        }
        this.eventlog = this.eventlog + s + "\n";
        this.getEntityData().set(FRIEND_EVENTLOG, this.eventlog);
    }

    public String getFriendName() {
        return this.name;
    }

    abstract int[] getSkillInfo();

    public int getInventoryRows() {
        return this.invRows;
    }

    protected void updateContainerEquipment() {
        if (!this.level().isClientSide) {
            this.setFlag(4, !this.inventory.getItem(0).isEmpty());
        }
    }

    public String getEventLog() {
        return this.getEntityData().get(FRIEND_EVENTLOG);
    }

    public void setEventLog(String s) {
        this.eventlog = s;
        this.getEntityData().set(FRIEND_EVENTLOG, s);
    }

    protected void setFlag(int pFlagId, boolean pValue) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);

        if (pValue) {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 | pFlagId));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 & ~pFlagId));
        }

    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        return new FriendPathNavigation(this, pLevel);
    }

    @Override
    public void playAmbientSound() {
        //do nothing
    }

    protected void pickUpItem(ItemEntity pItemEntity) {
        this.chasingitem = false;
        ItemStack itemstack = pItemEntity.getItem();
        ItemStack copy = itemstack.copy();
        this.onItemPickup(pItemEntity);
        this.take(pItemEntity, itemstack.getCount());
        this.playTimedVoice(this.getEquip());
        this.moveItemToEmptySlots(this.inventory, itemstack);

        itemstack.shrink(copy.getCount() - itemstack.getCount());
        if (itemstack.isEmpty()) {
            pItemEntity.discard();
        }
    }

    public boolean wantsToPickUp(ItemStack pStack) {


        if (this.getFriendItemPickup() == 0) {
            return true;
        } else if (this.getFriendItemPickup() == 1) {
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                if (ItemStack.isSameItemSameTags(this.inventory.getItem(i), pStack)) {
                    return true;
                }
            }
        } else return false;
        return false;
    }

    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    public boolean isLeftHanded() {
        return false;
    }

    public boolean isAggressive() {
        return super.isAggressive();
    }

    protected @NotNull AABB getAttackBoundingBox() {
        return super.getAttackBoundingBox().inflate(1.5, 0.0D, 1.5);
    }

    public int getFriendItemPickup() {
        return this.getEntityData().get(FRIEND_ITEMPICKUP);
    }

    public void setFriendItemPickup(int n) {
        this.itempickup = n;
        this.getEntityData().set(FRIEND_ITEMPICKUP, n);
    }

    public void playTimedVoice(SoundEvent sound) {
        if (this.soundCounter >= 50) {
            this.playVoice(sound, false);
        }
    }

    public abstract SoundEvent getEquip();

    private void moveItemToEmptySlots(SimpleContainer inventory, ItemStack pStack) {
        EquipmentSlot equipmentSlot = getEquipmentSlotForItem(pStack);
        if (equipmentSlot == EquipmentSlot.MAINHAND && (pStack.getItem() instanceof SwordItem || pStack.getItem() instanceof DiggerItem)) {
            if (this.inventory.getItem(1).isEmpty()) { //equip weapon
                this.inventory.setItem(1, pStack.copyAndClear());
                return;
            }
        } else { //armor
            if (equipmentSlot == EquipmentSlot.HEAD) {
                if (this.inventory.getItem(3).isEmpty()) {
                    this.inventory.setItem(3, pStack.copyAndClear());
                    return;
                }
            } else if (equipmentSlot == EquipmentSlot.CHEST) {
                if (this.inventory.getItem(4).isEmpty()) {
                    this.inventory.setItem(4, pStack.copyAndClear());
                    return;
                }
            } else if (equipmentSlot == EquipmentSlot.LEGS) {
                if (this.inventory.getItem(5).isEmpty()) {
                    this.inventory.setItem(5, pStack.copyAndClear());
                    return;
                }
            } else if (equipmentSlot == EquipmentSlot.FEET) {
                if (this.inventory.getItem(6).isEmpty()) {
                    this.inventory.setItem(6, pStack.copyAndClear());
                    return;
                }
            }

        }
        for (int i = 7; i < inventory.getContainerSize(); ++i) {
            if (ItemStack.isSameItemSameTags(inventory.getItem(i), pStack)) {
                moveItemToOccupiedSlotsWithSameType(inventory, pStack);
            }
        }
        if (!pStack.isEmpty()) {
            for (int i = 7; i < inventory.getContainerSize(); ++i) {
                if (inventory.getItem(i).isEmpty()) {
                    inventory.setItem(i, pStack.copyAndClear());
                    return;
                }
            }
        } else {
            return;
        }
        Block.popResource(this.level(), this.getOnPos(), pStack);
    }

    private void moveItemToOccupiedSlotsWithSameType(SimpleContainer inventory, ItemStack pStack) {
        for (int i = 7; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (ItemStack.isSameItemSameTags(itemstack, pStack)) {
                this.moveItemsBetweenStacks(inventory, pStack, itemstack);
                if (pStack.isEmpty()) {
                    return;
                }
            }
        }

    }

    private void moveItemsBetweenStacks(SimpleContainer inventory, ItemStack pStack, ItemStack pOther) {
        int i = Math.min(inventory.getMaxStackSize(), pOther.getMaxStackSize());
        int j = Math.min(pStack.getCount(), i - pOther.getCount());
        if (j > 0) {
            pOther.grow(j);
            pStack.shrink(j);
            inventory.setChanged();
        }

    }

    @Override
    public void setChargingCrossbow(boolean pChargingCrossbow) {
        //
    }

    @Override
    public void shootCrossbowProjectile(LivingEntity pTarget, ItemStack pCrossbowStack, Projectile pProjectile, float pProjectileAngle) {
        //pCrossbowStack.shrink(1);
        //this.shootCrossbowProjectile(this, pTarget, pProjectile, pProjectileAngle, 1.6F);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        //this.noActionTime = 0;
    }

    public void throwSnowball() {
        ItemStack itemstack = this.getItemInHand(InteractionHand.MAIN_HAND);
        if (!this.level().isClientSide) {
            this.playVoice(this.getAttack());
            this.playSound(SoundEvents.SNOWBALL_THROW);
            Snowball snowball = new Snowball(this.level(), this);
            snowball.setItem(itemstack);
            if (this.getTarget() != null) {
                this.lookAt(this.getTarget(), 360, 360);
            }
            float xrot = this.getXRot();
            float yrot = this.getYRot();
            snowball.shootFromRotation(this, xrot, yrot, 0.0F, 1.5F, 0.8F);
            this.level().addFreshEntity(snowball);
        }
        itemstack.shrink(1);
    }

    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        boolean flag = false;
        ItemStack ammo = null;
        for (int i = 1; i < this.inventory.getContainerSize(); i++) {
            if (this.inventory.getItem(i).getItem() instanceof ArrowItem) {
                ammo = this.inventory.getItem(i);
                flag = true;
                break;
            }
        }
        if (flag) {
            AbstractArrow abstractarrow = this.getArrow(ammo, pDistanceFactor);
            if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
                abstractarrow = ((net.minecraft.world.item.BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrow);
            double d0 = pTarget.getX() - this.getX();
            double d1 = pTarget.getY(0.3333333333333333D) - abstractarrow.getY();
            double d2 = pTarget.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            abstractarrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().addFreshEntity(abstractarrow);
            this.getMainHandItem().hurtAndBreak(1, this, (a) -> this.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            this.playVoice(this.getAttack());
            ammo.setCount(ammo.getCount() - 1);
        } else {
            this.playVoice(this.getRecoveryFail());
        }
    }

    protected AbstractArrow getArrow(ItemStack pArrowStack, float pVelocity) {
        return ProjectileUtil.getMobArrow(this, pArrowStack, pVelocity);
    }

    public void playVoice(SoundEvent sound) {
        this.playVoice(sound, false);
    }

    public abstract SoundEvent getAttack();

    public abstract SoundEvent getRecoveryFail();

    public void playVoice(SoundEvent sound, boolean extended) {
        if (!this.level().isClientSide()) {
            this.soundCounter = 0;
            if (sound != null && (this.quicksoundcounter == 0 || extended)) {
                this.broadcastVoiceToNearby(sound.getLocation().getNamespace() + "." + sound.getLocation().getPath());
                this.playSound(sound, this.volume, 1);
                this.quicksoundcounter = 10;
            }
        }
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

    public double getCombatAffinityModifier() {
        return (this.aggression) * 0.01;
    }

    public double getTravelAffinityModifier() {
        return (100 - Math.abs(this.aggression - 50)) * 0.01;
    }

    public void increaseEXP(double gain) {
        float currentxp = this.getFriendExperience();
        float nextlevel = 100 * (((int) (currentxp / 100)) + 1);
        float afterxp = currentxp + (float) gain;
        if (afterxp >= nextlevel) {
            this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.levelup").getString());
            this.playSound(SoundEvents.PLAYER_LEVELUP, 1, 1);
            this.setSkillPoints(this.getSkillPoints() + ((int) (afterxp / 100) - ((int) (currentxp / 100))));
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getAttributeBaseValue(Attributes.MAX_HEALTH) + 0.2 * ((int) (afterxp / 100) - ((int) (currentxp / 100))));
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) + 0.1 * ((int) (afterxp / 100) - ((int) (currentxp / 100))));
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) + 0.1 * ((int) (afterxp / 100) - ((int) (currentxp / 100))));
            this.playVoice(this.getModuleEquip());
            this.spawnHorizontalParticles();
        }
        this.setFriendExperience(afterxp);
    }

    public <T extends ParticleOptions> void spawnParticlesInRandomSpreadAtEntity(Entity entity, int count, float radius, float distance, ServerLevel sLevel, T type) {
        float targetX = (float) entity.getX();
        float targetZ = (float) entity.getZ();
        float targetY = (float) entity.getEyeY();

        sLevel.sendParticles(type, targetX, targetY, targetZ, count, radius, radius, radius, 1);
    }

    public <T extends ParticleOptions> void spawnParticlesInUpFacingCircle(Entity entity, float radius, T type) {
        CircleParticlePacketHandler.sendToClient(new ToClientCircleParticlePacket(entity.getId(), radius, type), entity);
    }

    public <T extends ParticleOptions> void spawnParticlesInSphereAtEntity(Entity entity, int count, float radius, float distance, ServerLevel sLevel, T type, float yOffset) {
        if (count < 1) {
            return;
        }
        float targetX = (float) entity.getX();
        float targetZ = (float) entity.getZ();
        float targetY = (float) entity.getEyeY();

        for (int i = 0; i < count; i++) {
            float x = (float) (Math.sin(i)) / 2 * radius;
            float z = (float) (Math.cos(i)) / 2 * radius;
            if (this.getRandom().nextBoolean()) {
                x = -x;
                z = -z;
            }
            sLevel.sendParticles(type, targetX + x, targetY + yOffset, targetZ + z, 1, 0, 0, 0, 0.5);

        }

        this.spawnParticlesInSphereAtEntity(entity, (int) (count * 0.8), radius * 0.8F, distance, sLevel, type, yOffset + 0.3F);
        this.spawnParticlesInSphereAtEntity(entity, (int) (count * 0.8), radius * 0.8F, distance, sLevel, type, yOffset - 0.3F);

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

    //override if change attack timing
    void doReleaseStart() {
        if (this.getAttackCounter() == (int) (22 / this.getAttackSpeed()) && this.getAttackType() == 40) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == (int) (10 / this.getAttackSpeed()) && this.getAttackType() == 20) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == (int) (5 / this.getAttackSpeed()) && this.getAttackType() == 10) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == (int) (16 / this.getAttackSpeed()) && this.getAttackType() == 50) {
            this.doHurtTarget();
        } else if (this.getAttackCounter() == (int) (8 / this.getAttackSpeed()) && this.getAttackType() == 60) {
            this.throwSnowball();
        }
    }

    public int getAttackType() {
        return this.getEntityData().get(FRIEND_ATTACKTYPE);
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
        this.getEntityData().set(FRIEND_ATTACKTYPE, attackType);
    }

    public void doHurtTarget() {
        if (FriendDefense.shouldBeCareful(this)) {
            this.runTimer = 35;
        }
        AABB hitTracer = new AABB(this.getX() - 1.8, this.getY(), this.getZ() - 1.8, this.getX() + 1.8, this.getY() + 2, this.getZ() + 1.8);
        List<Entity> entityList = this.level().getEntities(this, hitTracer);
        if (this.getTarget() != null) {
            this.lookAt(this.getTarget(), 360, 360);
        }
        double angle = Math.atan2(this.getLookAngle().z, this.getLookAngle().x);
        angle = Math.toDegrees(angle);
        double maxFov;
        if (this.attackType == 40 || this.attackType == 50) {
            maxFov = 50;
        } else if (this.attackType == 20) {
            maxFov = 40;
        } else {
            maxFov = 30;
        }
        boolean flag = false;
        for (Entity e : entityList) {
            if (e instanceof LivingEntity ent) {
                if (EnemyEvaluator.shouldDoHurtTarget(this, ent)) {
                    double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                    entityAngle = Math.toDegrees(entityAngle);
                    if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                        boolean temp = this.doHurtTarget(e);
                        flag = flag || temp;
                    }
                }
            } else {
                double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    boolean temp = this.doHurtTarget(e);
                    flag = flag || temp;
                }
            }
        }
        this.playVoice(this.getAttack());
        if (!this.inventory.getItem(1).isEmpty() || this.hasShellWeapon()) {
            if(flag){

            this.playSound(this.getHitSound(), 0.5F, 1);

            }else{
                this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
            }
        }else{
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
        }
        this.inventory.getItem(1).hurtAndBreak(1, this, (a) -> this.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        this.updateGear();
    }
    public void doHurtTargetDetailed(double maxFov, double range, float damagemult, SoundEvent hitsound) {
        if (FriendDefense.shouldBeCareful(this)) {
            this.runTimer = 35;
        }
        AABB hitTracer = new AABB(this.getX() - range, this.getY(), this.getZ() - range, this.getX() + range, this.getY() + 5, this.getZ() + range);
        List<Entity> entityList = this.level().getEntities(this, hitTracer);
        if (this.getTarget() != null) {
            this.lookAt(this.getTarget(), 360, 360);
            this.getLookControl().setLookAt(this.getTarget());
        }
        double angle = Math.atan2(this.getLookAngle().z, this.getLookAngle().x);
        angle = Math.toDegrees(angle);

        boolean flag = false;

        for (Entity e : entityList) {
            if (e instanceof LivingEntity ent) {
                if(EnemyEvaluator.shouldDoHurtTarget(this, ent)){
                    double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                    entityAngle = Math.toDegrees(entityAngle);
                    if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                        boolean temp = this.doHurtTarget(e);
                        flag = flag || temp;
                    }
                }
            } else {
                double entityAngle = -Math.atan2(e.position().z - this.position().z, e.position().x - this.position().x);
                entityAngle = Math.toDegrees(entityAngle);
                if (Math.abs(Math.abs(angle) - Math.abs(entityAngle)) < maxFov) {
                    boolean temp = this.doHurtTarget(e);
                    flag = flag || temp;
                }
            }
        }
        this.playVoice(this.getAttack());
        if(!flag){
            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
        }else{
            this.playSound(hitsound);
        }
        this.inventory.getItem(1).hurtAndBreak(1, this, (a) -> this.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        this.updateGear();
    }

    public boolean hasShellWeapon() {
        return false;
    }

    SoundEvent getHitSound() {
        if (this.attackType == 10) {
            return LIGHT_ATTACK.get();
        } else if (this.attackType == 20) {
            return MEDIUM_ATTACK.get();
        } else if (this.attackType == 40) {
            return HEAVY_ATTACK.get();
        } else {
            return COUNTER_ATTACK.get();
        }
    }

    public float getGenericDamageMod() {
        return 1F;
    }

    public boolean shouldMoveLegs() {
        return true;
    }

    public boolean shouldFollow() {
        return !this.isAttackLockedOut();
    }

    public boolean isAttackLockedOut() {
        return false;
    }

    public int getRecoveryDifficulty() {
        this.setRecoveryDifficulty();
        return this.recoveryDifficulty;
    }

    public void spawnHorizontalParticles() {
        if (this.level() instanceof ServerLevel pLevel) {
            pLevel.sendParticles(SUGURIVERSE_LARGE.get(), this.getX(), this.getY() + 1, this.getZ(), 1, 0.0D, 0, 0.0D, 1);
        }
    }

    void petEvent() {
        this.playTimedVoice(this.getPat());
        patCounter = 20;
        this.setTimeSinceLastPat(0);
        this.getNavigation().stop();
        if (this.random.nextInt(20) == 6) {
            this.mood += Mth.clamp(this.mood + 20, 0, 100);
            if (this.level() instanceof ServerLevel sLevel) {
                for (int i = 0; i < 5; i++) {
                    sLevel.sendParticles(HEART, this.getX(), this.getY() + 1, this.getZ(), 1, this.random.nextInt(-1, 2), this.random.nextInt(-1, 2), this.random.nextInt(-1, 2), 0.5);
                }

            }
            this.updateFriendNorma(0.02F, 0);
        }
    }

    void doRecoveryEvent() {
        this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.closecall").getString());
        this.setHealth(this.getMaxHealth() / 2);
        this.deathCounter = 7 - recoveryDifficulty;
        this.getEntityData().set(FRIEND_ISDYING, false);
        this.isDying = false;
        this.playSound(RECOVERY.get(), 1, 1);
        this.playVoice(this.getRecovery());
        this.getFriendNav().setShouldMove(true);
        this.spawnHorizontalParticles();
        this.setInvulnerable(false);
    }

    void doDyingEvent() {
        this.playVoice(getRecoveryFail());
        if (this.deathCounter <= 0) {
            this.doDeathEvent();
        }
    }

    public void doDeathEvent() {
        this.spawnHorizontalParticles();
        this.playSound(FRIEND_DEATH.get(), 1, 1);
        this.playVoice(this.getDeathSound());
        if (deathSource == null) {
            deathSource = new DamageSources(this.level().registryAccess()).generic();
        }
        this.die(deathSource);

        this.setRemoved(RemovalReason.KILLED);
    }

    public abstract SoundEvent getDeath();

    public abstract SoundEvent getOnKill();

    public abstract SoundEvent getLaugh();

    public abstract SoundEvent getAngry();

    public abstract SoundEvent getFlee();

    public abstract SoundEvent getInjured();

    public abstract SoundEvent getInteract();

    public abstract SoundEvent getPat();

    public abstract SoundEvent getEvade();

    public abstract SoundEvent getBattle();

    abstract public SoundEvent getHyperEquip();

    public abstract SoundEvent getHyperUse();

    public abstract SoundEvent getRecovery();

    public abstract SoundEvent getOnHeal();

    public abstract SoundEvent getWarning();

    public abstract SoundEvent getModuleEquip();

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

    public abstract AbstractDialogueManager getDialogueManager();

    public boolean shouldMoveArms() {
        return this.getAttackCounter() <= 0 && !this.drawBowAnimationState.isStarted() && !this.swimAnimState.isStarted() && this.shakeAnimO == 0 && !this.snowballIdle();
    }

    public int getAttackCounter() {
        return this.getEntityData().get(FRIEND_ATTACKCOUNTER);
    }

    public void setAttackCounter(int time) {
        this.attackCounter = (int) ((time + 2) / this.getAttackSpeed());
        this.getEntityData().set(FRIEND_ATTACKCOUNTER, this.attackCounter);
    }

    public boolean snowballIdle() {
        return this.isHoldingThrowable() && !this.isDescending() && this.canDoThings() && this.shakeAnimO == 0;
    }

    public boolean isHoldingThrowable() {
        return this.getMainHandItem().getItem() instanceof SnowballItem;
    }

    public boolean canDoThings() {
        return !this.getInSittingPose() && !this.isDying && this.getVehicle() == null;
    }

    public boolean getInSittingPose() {
        return this.entityData.get(FRIEND_ISSITTING);
    }    public void setFriendSwimCounter(int n) {
        this.startfloattimer = n;
        this.getEntityData().set(FRIEND_SWIMCOUNTER, n);
    }

    public boolean shouldMoveLeftArm() {
        return this.getAttackCounter() <= 0 && !this.drawBowAnimationState.isStarted() && !this.swimAnimState.isStarted() && this.shakeAnimO == 0 && this.snowballIdle();
    }    public int getFriendSwimCounter() {
        return this.getEntityData().get(FRIEND_SWIMCOUNTER);
    }

    public int getDeathAnimCounter() {
        return this.getEntityData().get(FRIEND_DEATHCOUNTER);
    }

    public void setDeathAnimCounter(int c) {
        this.deathAnimCounter = c;
        this.getEntityData().set(FRIEND_DEATHCOUNTER, c);
    }

    public int getHungerMeter() {
        return this.getEntityData().get(FRIEND_HUNGERMETER);
    }

    public void setHungerMeter(int hun) {
        this.hungerMeter = hun;
        this.getEntityData().set(FRIEND_HUNGERMETER, hun);
    }

    public int getFriendItemsCollected() {
        return this.getEntityData().get(FRIEND_ITEMSCOLLECTED);
    }

    public void setFriendItemsCollected(int c) {
        this.itemsCollected = c;
        this.getEntityData().set(FRIEND_ITEMSCOLLECTED, c);
    }

    public int getFriendEnemiesKilled() {
        return this.getEntityData().get(FRIEND_ENEMIESKILLED);
    }

    public void setFriendEnemiesKilled(int c) {
        this.enemiesKilled = c;
        this.getEntityData().set(FRIEND_ENEMIESKILLED, c);
    }

    public void updateCombatSettings() {
        this.getEntityData().set(FRIEND_COMBATSETTINGS, this.combatSettings.makeHash());
    }

    public FriendPathNavigation getFriendNav() {
        return (FriendPathNavigation) this.navigation;
    }

    public void updateFriendNorma(float n, int source) {
        double netup = 0;
        if (source == 0) { //pats
            if (this.normaprogress[source] + n * this.getPeaceAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getPeaceAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getPeaceAffinityModifier();
                this.normaprogress[source] += netup;
            }
        } else if (source == 1) { //combat kill
            if (this.normaprogress[source] + n * this.getCombatAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getCombatAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getCombatAffinityModifier();
                this.normaprogress[source] += netup;
            }
        } else if (source == 2) { //dialogue
            if (this.normaprogress[source] + n * this.getPeaceAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getPeaceAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getPeaceAffinityModifier();
                this.normaprogress[source] += netup;
            }
        } else if (source == 3) { //eating
            if (this.normaprogress[source] + n * this.getTravelAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getTravelAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getTravelAffinityModifier();
                this.normaprogress[source] += netup;
            }
        } else if (source == 5) { //passive
            if (this.normaprogress[source] + n * this.getTravelAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getTravelAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getTravelAffinityModifier();
                this.normaprogress[source] += netup;
            }
        } else if (source == 6) { //travel
            if (this.normaprogress[source] + n * this.getTravelAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getTravelAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getTravelAffinityModifier();
                this.normaprogress[source] += netup;
            }
        } else { //sleep
            if (this.normaprogress[source] + n * this.getTravelAffinityModifier() <= this.normacaps[source]) {
                netup = n * this.getPeaceAffinityModifier();
                this.normaprogress[source] += netup;
            } else {
                netup = (this.normacaps[source] - this.normaprogress[source]) * this.getPeaceAffinityModifier();
                this.normaprogress[source] += netup;
            }
        }

        this.setFriendNorma((float) (this.getFriendNorma() + netup), 0);
    }

    public float getFriendExperience() {
        return this.getEntityData().get(FRIEND_LEVEL);
    }

    public void setFriendExperience(float n) {
        this.experience = n;
        this.getEntityData().set(FRIEND_LEVEL, n);
    }

    public int[] getSkillLevels() {
        return SkillManager.assembleSkillLevels(this);
    }

    public void setSkillLevels(int[] a) {
        this.skillLevels = a;

        this.getEntityData().set(FRIEND_SKILLLEVELS1, a[0]);
        this.getEntityData().set(FRIEND_SKILLLEVELS2, a[1]);
        this.getEntityData().set(FRIEND_SKILLLEVELS3, a[2]);
        this.getEntityData().set(FRIEND_SKILLLEVELS4, a[3]);
        this.getEntityData().set(FRIEND_SKILLLEVELS5, a[4]);
        this.getEntityData().set(FRIEND_SKILLLEVELS6, a[5]);
    }

    public int[] getSpecialDialogueEnabled() {
        return AbstractDialogueManager.decodeSpecialHash(this.getEntityData().get(FRIEND_SPECIALSENABLED));
    }

    public void setSpecialDialogueEnabled(int[] n) {
        this.specialDialogueEnabled = n;
        this.getEntityData().set(FRIEND_SPECIALSENABLED, AbstractDialogueManager.encodeSpecialHash(n));
    }

    public boolean getIsWandering() {
        return this.getEntityData().get(FRIEND_ISWANDERING);
    }

    public void setIsWandering(boolean b) {
        this.wandering = b;
        this.getEntityData().set(FRIEND_ISWANDERING, b);
    }

    public boolean getIsFarming() {
        return this.getEntityData().get(FRIEND_ISFARMING);
    }

    public void setIsFarming(boolean b) {
        this.doFarming = b;
        this.getEntityData().set(FRIEND_ISFARMING, b);
    }

    public boolean hasActivator() {
        return !this.inventory.getItem(0).isEmpty();
    }

    public int getViewFlower() {
        return this.getEntityData().get(FRIEND_VIEWFLOWER);
    }

    public void setViewFlower(int n) {
        this.viewflower = n;
        this.getEntityData().set(FRIEND_VIEWFLOWER, n);
    }

    public int getFriendPlaceCounter() {
        return this.getEntityData().get(FRIEND_PLACECOUNTER);
    }

    public void setFriendPlaceCounter(int n) {
        this.placecounter = n;
        this.getEntityData().set(FRIEND_PLACECOUNTER, n);
    }

    //override if change attack timing
    public int getCounterTiming() {
        return (int) (24 / this.getAttackSpeed());
    }

    public int getCounterMax() {
        return (int) ((34 + 2) / this.getAttackSpeed());
    }

    public double getAttackSpeed() {
        float nightmod = (!this.day() && !this.isNocturnal()) ? 0.7F : 1F;
        try {
            double temp = -3;
            for (AttributeModifier mod : this.getFriendWeapon().getItem().getDefaultAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED)) {
                if (mod.getName().equals("Weapon modifier")) {
                    temp = mod.getAmount();
                    break;
                }
            }
            temp += 4;
            //LOGGER.info(this.combatmodifier + "BASE");
            //LOGGER.info(this.getCombatMod() +"MOD");
            return (temp / 1.6 * Mth.clamp(1 + 0.05 * this.getCombatMod(), 0.1, 1.5)) * nightmod;
        } catch (Exception e) {
            return (0.625 * Mth.clamp(1 + 0.05 * this.getCombatMod(), 0.1, 1.5)) * nightmod;
        }
    }

    public float getCombatMod() {
        float mod = this.getCombatModifier();
        if(!this.day() && !this.isNocturnal()){
            mod=mod/2;
        }
        return 10 * (1 + mod) / (10 + mod);
    }

    public int getCombatModifier() {
        return this.getEntityData().get(FRIEND_COMBATMOD);
    }

    public void setCombatModifier(int n) {
        this.combatmodifier = n;
        this.getEntityData().set(FRIEND_COMBATMOD, n);
    }

    public void decrementAttackCounter() {
        this.attackCounter--;
        this.getEntityData().set(FRIEND_ATTACKCOUNTER, this.attackCounter);
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
    public void setHome(int x, int y, int z){
        this.homeX=x;
        this.homeY=y;
        this.homeZ=z;
    }
    public void setHome(BlockPos pos){
        this.setHome(pos.getX(),pos.getY(),pos.getZ());
    }
    public BlockPos getHome(){
        return new BlockPos(this.homeX,this.homeY,this.homeZ);
    }
    public abstract boolean isNocturnal();

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new FriendLonelyGoal(this, 1, false));
        this.goalSelector.addGoal(1, new FriendFloatGoal(this));
        this.goalSelector.addGoal(4, new FriendOpenDoorGoal(this, true));
        this.goalSelector.addGoal(4, new FriendLadderClimbGoal(this));
        this.goalSelector.addGoal(5, new FriendMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FriendSleepGoal(this));
        this.goalSelector.addGoal(2, new FriendSitGoal(this));
        this.goalSelector.addGoal(8, new FriendFollowGoal(this, 1D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new FriendGoHomeGoal(this));
        //this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D)); //...maybe in the future...
        this.goalSelector.addGoal(9, new FriendWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new FriendBegGoal(this, 1, false));
        this.goalSelector.addGoal(11, new FriendLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new FriendRandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new FriendOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new FriendOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new FriendNearestAttackableTargetGoal<>(this, Player.class, 0, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new FriendNearestAttackableTargetGoal<>(this, Seagull.class, true));
        this.targetSelector.addGoal(9, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(5, new FriendFarmGoal(this));
        this.goalSelector.addGoal(2, new FriendHitAndRunGoal(this));
        this.goalSelector.addGoal(2, new FriendFleeGoal(this));
        this.goalSelector.addGoal(2, new FriendFleeFromCreeperGoal(this));
        this.targetSelector.addGoal(7, new FriendNearestAttackableTargetGoal<>(this, Mob.class, 0, false, false, (p_28879_) -> p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper)));
        this.registerCustomGoals();
    }
    public static EntityDataAccessor<Boolean> FLAGFORRESET = SynchedEntityData.defineId(Friend.class,EntityDataSerializers.BOOLEAN);
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
        this.combatSettings = new CombatSettings(4, 3, 1, 0, 0);
        this.entityData.define(FRIEND_COMBATSETTINGS, this.combatSettings.hash);
        this.entityData.define(FRIEND_HUNGERMETER, this.hungerMeter);
        this.entityData.define(FRIEND_NORMA, this.norma);
        this.entityData.define(FRIEND_LEVEL, this.experience);
        this.entityData.define(FRIEND_ITEMSCOLLECTED, this.itemsCollected);
        this.entityData.define(FRIEND_ENEMIESKILLED, this.enemiesKilled);
        this.skillEnabled = new boolean[6];
        this.skillLevels = new int[6];
        this.entityData.define(FRIEND_SKILLENABLED, SkillManager.makeBooleanHash(this.skillEnabled));
        this.entityData.define(FRIEND_SKILLLEVELS1, this.skillLevels[0]);
        this.entityData.define(FRIEND_SKILLLEVELS2, this.skillLevels[1]);
        this.entityData.define(FRIEND_SKILLLEVELS3, this.skillLevels[2]);
        this.entityData.define(FRIEND_SKILLLEVELS4, this.skillLevels[3]);
        this.entityData.define(FRIEND_SKILLLEVELS5, this.skillLevels[4]);
        this.entityData.define(FRIEND_SKILLLEVELS6, this.skillLevels[5]);
        this.entityData.define(FLAGFORRESET, false);
        this.specialDialogueEnabled = new int[]{0, 0, 0};
        this.entityData.define(FRIEND_SPECIALSENABLED, AbstractDialogueManager.encodeSpecialHash(this.specialDialogueEnabled));
        this.entityData.define(FRIEND_SKILLPOINTS, this.skillPoints);
        this.entityData.define(FRIEND_ISWANDERING, this.wandering);
        this.entityData.define(FRIEND_ISFARMING, this.doFarming);
        this.eventlog = "";
        this.entityData.define(FRIEND_EVENTLOG, this.eventlog);
        this.entityData.define(FRIEND_TIMESINCEPAT, this.timesincelastpat);
        this.entityData.define(FRIEND_ITEMPICKUP, this.itempickup);
        this.entityData.define(FRIEND_VIEWFLOWER, this.viewflower);
        this.entityData.define(FRIEND_PLACECOUNTER, this.placecounter);
        this.entityData.define(FRIEND_SWIMCOUNTER, this.startfloattimer);
        this.entityData.define(FRIEND_COMBATMOD, this.combatmodifier);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("juicecraft.maxnorma", this.maxnorma);
        pCompound.putInt("juicecraft.itempickup", this.getFriendItemPickup());
        pCompound.putInt("juicecraft.timesincelastpat", this.timesincelastpat);
        pCompound.putIntArray("juicecraft.normaprogress", new int[]{(int) (this.normaprogress[0] * 10000), (int) (this.normaprogress[1] * 10000), (int) (this.normaprogress[2] * 10000), (int) (this.normaprogress[3] * 10000), (int) (this.normaprogress[4] * 10000), (int) (this.normaprogress[5] * 10000), (int) (this.normaprogress[6] * 10000), (int) (this.normaprogress[7] * 10000)});
        pCompound.putString("juicecraft.eventlog", this.eventlog);
        pCompound.putIntArray("juicecraft.dialogue", this.dialogueTree);
        pCompound.putIntArray("juicecraft.specialsenabled", this.specialDialogueEnabled);
        pCompound.putInt("juicecraft.csettings", this.getCombatSettings().makeHash());
        pCompound.putInt("juicecraft.social", this.socialInteraction);
        pCompound.putInt("juicecraft.mood", this.mood);
        pCompound.putInt("juicecraft.existed", 1);
        pCompound.putBoolean("Tame", this.isTame());
        pCompound.putBoolean("juicecraft.isdying", this.isDying);
        pCompound.putInt("juicecraft.deathcounter", this.deathCounter);
        pCompound.putInt("juicecraft.hungermeter", this.hungerMeter);
        pCompound.putFloat("juicecraft.norma", this.norma);
        pCompound.putFloat("juicecraft.experience", this.experience);
        pCompound.putInt("juicecraft.itemscollected", this.itemsCollected);
        pCompound.putInt("juicecraft.enemieskilled", this.enemiesKilled);
        pCompound.putIntArray("juicecraft.homepos",new int[]{this.homeX,this.homeY,this.homeZ});

        //pCompound.putInt("juicecraft.skilllevels", SkillManager.saveSkills(this.getSkillLevels()));
        byte[] bytes = new byte[]{(byte) this.skillLevels[0], (byte) this.skillLevels[1], (byte) this.skillLevels[2], (byte) this.skillLevels[3], (byte) this.skillLevels[4], (byte) this.skillLevels[5],};
        pCompound.putByteArray("juicecraft.skilllevels", bytes);

        pCompound.putInt("juicecraft.skillenabled", SkillManager.makeBooleanHash(this.getSkillEnabled()));
        pCompound.putInt("juicecraft.skillpoints", this.getSkillPoints());
        pCompound.putBoolean("juicecraft.dofarming", this.doFarming);
        pCompound.putBoolean("juicecraft.iswandering", this.wandering);
        pCompound.putInt("juicecraft.combatmodifier", this.combatmodifier);
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

    //TYPES
    //verify that both type and counter are correct when you override
    //10: light attack
    //20: medium
    //34: counterattack
    //40: heavy attack
    //60 snowball throw

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.getInt("juicecraft.existed") == 0) {
            super.readAdditionalSaveData(pCompound);
            this.initializeNew();
            return;
        }
        super.readAdditionalSaveData(pCompound);
        this.initializeNew();
        int[] pos = pCompound.getIntArray("juicecraft.homepos");
        if(pos.length>1);
        this.homeX=pos[0];
        this.homeY=pos[1];
        this.homeZ=pos[2];
        this.maxnorma = pCompound.getInt("juicecraft.maxnorma");
        int[] temp2 = pCompound.getIntArray("juicecraft.normaprogress");
        this.setTimeSinceLastPat(pCompound.getInt("juicecraft.timesincelastpat"));
        this.setFriendItemPickup(pCompound.getInt("juicecraft.itempickup"));
        this.normaprogress = new double[]{((double) temp2[0]) / 10000, ((double) temp2[1]) / 10000, ((double) temp2[2]) / 10000, ((double) temp2[3]) / 10000, ((double) temp2[4]) / 10000, ((double) temp2[5]) / 10000, ((double) temp2[6]) / 10000, ((double) temp2[7]) / 10000};
        this.dialogueTree = pCompound.getIntArray("juicecraft.dialogue");
        this.setSpecialDialogueEnabled(pCompound.getIntArray("juicecraft.specialsenabled"));
        this.combatSettings = CombatSettings.decodeHash((pCompound.getInt("juicecraft.csettings")));
        this.updateCombatSettings();
        this.socialInteraction = (pCompound.getInt("juicecraft.social"));
        this.mood = (pCompound.getInt("juicecraft.mood"));
        this.setHealth(pCompound.getFloat("Health"));
        this.isDying = (pCompound.getBoolean("juicecraft.isdying"));
        this.deathCounter = (pCompound.getInt("juicecraft.deathcounter"));
        this.setHungerMeter(pCompound.getInt("juicecraft.hungermeter"));
        this.setFriendNorma(pCompound.getFloat("juicecraft.norma"), -1);
        this.setFriendExperience(pCompound.getFloat("juicecraft.experience"));
        this.setFriendItemsCollected(pCompound.getInt("juicecraft.itemscollected"));
        this.setFriendEnemiesKilled(pCompound.getInt("juicecraft.enemieskilled"));

        byte[] bytes = pCompound.getByteArray("juicecraft.skilllevels");
        this.setSkillLevels(new int[]{(int) bytes[0], (int) bytes[1], (int) bytes[2], (int) bytes[3], (int) bytes[4], (int) bytes[5]});
        this.setSkillEnabled(SkillManager.decodeBooleanHash(pCompound.getInt("juicecraft.skillenabled")));
        this.setSkillPoints(pCompound.getInt("juicecraft.skillpoints"));
        this.setIsWandering(pCompound.getBoolean("juicecraft.iswandering"));
        this.setIsFarming(pCompound.getBoolean("juicecraft.dofarming"));
        this.setCombatModifier(pCompound.getInt("juicecraft.combatmodifier"));
        this.setEventLog(pCompound.getString("juicecraft.eventlog"));

        this.setTame(pCompound.getBoolean("Tame"));
        this.createInventory();
        ListTag listtag = pCompound.getList("juicecraft.inventory", 10);
        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.of(compoundtag));
            }
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
        this.updateGear();

    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.getIdle();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource dmgsrc) {
        return SoundEvents.PLAYER_HURT;

    }

    @Override
    public SoundEvent getDeathSound() {
        return this.getRecoveryFail();
    }

    @Override
    protected float getSoundVolume() {
        return this.volume;
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
            //ambient noise
            if (this.tickCount % 80 == 0) {
                if (this.random.nextInt(3) == 2 && this.getPose() != SLEEPING && !this.getIsDying()) {
                    this.playTimedVoice(this.getIdle());
                }
                if (this.isTame()) {
                    this.setTimeSinceLastPat(this.getTimeSinceLastPat() + 80);
                }
                if (this.getTimeSinceLastPat() == 3600) {
                    if (this.getOwner() != null) {
                        this.appendEventLog(this.getOwner().getScoreboardName() + Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.lonely").getString());
                    }
                }
            }

            //mood/social
            if (!this.wandering) {
                if (this.aggression < 50) {
                    if (this.tickCount % (100 * Mth.clamp((int) this.getFriendNorma(), 1, 5)) == 0) {
                        if (mood < 100) {
                            this.mood++;
                        }
                        if (mood > 1) {
                            if (this.isTame()) {
                                this.socialInteraction--;
                                mood -= 2;
                            }
                        }
                    }
                } else if (this.aggression < 75) {
                    if (this.tickCount % (200 * Mth.clamp((int) this.getFriendNorma(), 1, 5)) == 0) {
                        if (this.mood < 100) {
                            this.mood++;
                        }
                        if (mood > 1) {
                            if (this.isTame()) {
                                this.socialInteraction--;
                                mood -= 2;
                            }
                        }
                    }
                } else {
                    if (this.tickCount % (300 * Mth.clamp((int) this.getFriendNorma(), 1, 5)) == 0) {
                        if (this.mood < 100) {
                            this.mood++;
                        }
                        if (mood > 1) {
                            if (this.isTame()) {
                                this.socialInteraction--;
                                mood -= 2;
                            }
                        }
                    }
                }
                if (socialInteraction <= 0 || this.getFriendNorma() < 1) {
                    this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.untame").getString());
                    this.setTame(false);
                    this.setFriendNorma(1, -1);
                    this.setOwnerUUID(null);
                    this.socialInteraction = 100;
                    this.mood = 0;
                    this.captureDifficulty *= 2;
                }
            }
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
        if (blinkCounter == 0) {
            blinkCounter = 150;
        }
        if (soundCounter < 50) {
            soundCounter++;
        }
        if (this.quicksoundcounter > 0) {
            this.quicksoundcounter--;
        }
        blinkCounter--;
    }

    public boolean getIsDying() {
        return this.getEntityData().get(FRIEND_ISDYING);
    }

    public abstract SoundEvent getIdle();

    public int getTimeSinceLastPat() {
        return this.getEntityData().get(FRIEND_TIMESINCEPAT);
    }
    public void setTarget(@Nullable LivingEntity pTarget) {
        super.setTarget(pTarget);
    }

    public void setTimeSinceLastPat(int n) {
        this.timesincelastpat = n;
        this.getEntityData().set(FRIEND_TIMESINCEPAT, n);
    }

    public void setIsDying(boolean b) {
        this.isDying = b;
        this.getEntityData().set(FRIEND_ISDYING, b);
    }
    public static final UUID NIGHTFATIGUEID = UUID.fromString("7c3f9e32-0fbb-4b93-8230-6a03f2be21b6");
    AttributeModifier FATIGUE;
    public void updateFatigue(){
        if(this.day()){
            if(!this.isNocturnal()){
            this.getAttribute(Attributes.MOVEMENT_SPEED).removePermanentModifier(NIGHTFATIGUEID);}
        }else{
            if(!this.isNocturnal()){
            this.getAttribute(Attributes.MOVEMENT_SPEED).removePermanentModifier(NIGHTFATIGUEID);
            this.FATIGUE = new AttributeModifier(NIGHTFATIGUEID, "NIGHTFATIGUE", -0.1F, AttributeModifier.Operation.ADDITION);
            this.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(this.FATIGUE);}}
    }
    public void decrementAnimCounter(EntityDataAccessor<Integer> accessor) {
        int temp = this.getSyncInt(accessor);
        if (temp > 0) {
            this.setSyncInt(accessor, temp - 1);
        }

    }
    public boolean isFearless(){
        return false;
    }
    public boolean areAnimationsBusy() {
        for (EntityDataAccessor<Integer> access : this.counters) {
            if (this.getSyncInt(access) > 0) {
                return true;
            }
        }
        return false;
    }
    public double xOldOld;
    public double xOldOldOld;
    public double yOldOld;
    public double yOldOldOld;
    public double zOldOld;
    public double zOldOldOld;
    public boolean isCounter(){
        return false;
    }
    @Override
    public void tick() {

        this.xOldOldOld=this.xOldOld;
        this.xOldOld=this.xOld;
        this.yOldOldOld=this.yOldOld;
        this.yOldOld=this.yOld;
        this.zOldOldOld=this.zOldOld;
        this.zOldOld=this.zOld;

        this.setupcomplete = true;
        for (EntityDataAccessor<Integer> counter : this.counters) {
            this.decrementAnimCounter(counter);
        }
        super.tick();
        if (this.patCounter > 0) {
            this.idleCounter = 0;
            this.patCounter--;
        }
        if (this.isembarassed > 0) {
            this.isembarassed--;
        }
        if (this.mood > 100) {
            this.mood = 100;
        }
        if (this.impatientCounter > 0) {
            impatientCounter--;
        }
        if (this.isAggressive()) {
            this.aggroCounter = 20;
        } else if (this.aggroCounter > 0) {
            this.aggroCounter--;
        }
        if (level().isClientSide()) {
            if(this.getSyncBoolean(FLAGFORRESET)){
                this.attackAnimState.stop();
                this.attackCounterAnimState.stop();
            }
            if (this.level().getGameTime() % 150 == 0 && this.getInSittingPose()) {
                this.impatientCounter = 100;
            } else if (!this.getInSittingPose() || this.patCounter != 0) {
                this.impatientCounter = 0;
            }
            if (this.tickCount % 160 == 0) {
                this.updateGear();
                if (!this.isUsingItem() && this.random.nextBoolean() && this.random.nextBoolean() && this.animatestandingtimer <= 0 && this.idleCounter == 20 && !this.getFriendWeapon().isEmpty() && this.additionalInspectConditions()) {
                    this.animatestandingtimer = 80;
                }
            }
            if (this.animatestandingtimer > 0) {
                this.animatestandingtimer--;
            }
            if(!this.idle()){
                this.animatestandingtimer=0;
            }
            if (this.getPose() == STANDING && (this.idle() || this.snowballIdle()) && idleCounter < 20) {
                this.idleCounter++;
            }
            //LOGGER.info(this.isAttackLockout() +"");
            if(this.isHoldingThrowable() && !(this.snowballIdle() && !this.walkAnimation.isMoving() && !this.isDescending() && !this.isAggressive() && this.onGround() && this.canDoThings() && this.shakeAnimO == 0)){
                this.idleCounter=0;
            }
            if ((this.getPose() == STANDING && !idle() && !snowballIdle() && idleCounter > 0) || this.isAttackLockedOut()) {
                this.idleCounter = 0;
            }
            if ((this.getAttackCounter() > 0 && this.getAttackType() != 60) || this.shakeAnimO > 0 || this.getViewFlower() > 0) {
                this.idleCounter = 0;
            }
            this.snowballThrowAnimState.animateWhen(this.canDoThings() && this.getAttackCounter() > 0 && this.getAttackType() == 60, this.tickCount);
            this.attackAnimState.animateWhen(!this.attackCounterAnimState.isStarted() && this.canDoThings() && this.getAttackCounter() != 0 && this.getAttackType() != 50 && this.getAttackType() != 60, this.tickCount);

            this.attackCounterAnimState.animateWhen(this.canDoThings() && this.getAttackCounter() != 0 && (this.getAttackType() == 50 || this.isCounter()), this.tickCount);

            this.interactAnimState.animateWhen(this.canDoThings() && this.getFriendPlaceCounter() > 0, this.tickCount);
            this.viewFlowerAnimState.animateWhen(this.canDoThings() && this.getViewFlower() > 0, this.tickCount);
            this.wetAnimState.animateWhen(this.canDoThings() && this.shakeAnimO > 0 && !this.walkAnimation.isMoving(), this.tickCount);
            this.deathStartAnimState.animateWhen(this.getIsDying() && this.getDeathAnimCounter() != 0, this.tickCount);
            this.deathAnimState.animateWhen(this.getIsDying() && this.getDeathAnimCounter() == 0, this.tickCount);
            this.idleAnimState.animateWhen(idle() && this.idleCounter == 20 && this.patCounter == 0, this.tickCount);
            this.idleAnimStartState.animateWhen(!this.idleAnimState.isStarted() && idle() && this.idleCounter > 0 && this.idleCounter < 20 && this.patCounter == 0, this.tickCount);
            this.snowballIdleAnimState.animateWhen(snowballIdle() && this.idleCounter == 20 && this.patCounter == 0 && !this.snowballThrowAnimState.isStarted(), this.tickCount);
            this.snowballIdleTransitionAnimState.animateWhen(!this.snowballIdleAnimState.isStarted() && snowballIdle() && this.idleCounter > 0 && this.idleCounter < 20 && this.patCounter == 0 && !this.snowballThrowAnimState.isStarted(), this.tickCount);

            this.inspectAnimState.animateWhen(idle() && this.idleCounter == 20 && this.patCounter == 0 && this.animatestandingtimer > 0, this.tickCount);
            this.patAnimState.animateWhen(this.canDoThings() && this.patCounter > 0 && !this.walkAnimation.isMoving() && !this.isDescending() && (this.idle() || this.snowballIdle()) && this.shakeAnimO == 0, this.tickCount);
            this.sitPatAnimState.animateWhen(this.getPose() == SITTING && this.patCounter != 0, this.tickCount);
            this.sitAnimState.animateWhen(this.getPose() == SITTING && this.patCounter == 0 && this.impatientCounter == 0, this.tickCount);
            this.sitImpatientAnimState.animateWhen(this.getPose() == SITTING && this.patCounter == 0 && this.impatientCounter != 0, this.tickCount);
            this.sleepAnimState.animateWhen(this.canDoThings(), this.tickCount);
            this.drawBowAnimationState.animateWhen(this.canDoThings() && ((this.getMainHandItem().getItem() instanceof BowItem && this.isUsingItem()) || (this.getMainHandItem().getItem() instanceof CrossbowItem && this.isAggressive())), this.tickCount);
            this.swimAnimState.animateWhen(this.isInWater() && !this.onGround() && !this.jumping, this.tickCount);


        }

        //SERVERSIDE-ONLY TICKS

        else {
            if(this.getAttackCounter() == 1){
                this.attackplayertoo=false;
            }
            if(this.isAggressive()){
                this.aggressiontimer++;
            }else{
                this.aggressiontimer=0;
            }
            if(this.getSyncBoolean(FLAGFORRESET)){
                this.setSyncBoolean(FLAGFORRESET,false);
            }
            if (this.getViewFlower() == 1) {
                this.getFriendNav().setShouldMove(true);
            }
            if (this.getAttackCounter() > 0) {
                this.doReleaseStart();
                this.decrementAttackCounter();
            }
            if (this.placecounter > 0) {
                this.setFriendPlaceCounter(this.placecounter - 1);
            }
            this.increaseEXP(this.distanceToSqr(this.xOld, this.yOld, this.zOld) < 10 ? this.distanceToSqr(this.xOld, this.yOld, this.zOld) * 0.02 * this.getTravelAffinityModifier() : 0);
            this.updateFriendNorma(this.distanceToSqr(this.xOld, this.yOld, this.zOld) < 10 ? (float) (this.distanceToSqr(this.xOld, this.yOld, this.zOld) * 0.002 * this.getTravelAffinityModifier()) : 0, 6);
            if (this.runTimer > 0) {
                this.runTimer--;
            }
            if (this.deathAnimCounter != 0) {
                setDeathAnimCounter(this.deathAnimCounter - 1);
            }
            if (this.isDying) {
                this.setInvulnerable(true);
                this.getFriendNav().setShouldMove(false);
                this.setTarget(null);
                if (this.tickCount % 20 == 0) {
                    ServerLevel sLevel = (ServerLevel) this.level();
                }
                if (deathTimer == 100) {
                    deathdiceroll = this.random.nextInt(6);
                    ServerLevel sLevel = (ServerLevel) this.level();
                    this.playSound(DICE_THROW.get(), 1, 1);
                    sLevel.sendParticles(DiceHandler.getDice(deathdiceroll), this.getX(), this.getY() + 2.5, this.getZ(), 1, 0, 0, 0, 0.1);
                }
                if (deathTimer == 80 && deathdiceroll >= this.getRecoveryDifficulty()) {
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
                if (this.viewflower > 0) {
                    this.setViewFlower(this.viewflower - 1);
                }
                deathTimer = 200;
            }
            if (this.tickCount % 4000 == 0) {
                if (EnemyEvaluator.evaluateAreaDanger(this) > this.getFriendExperience() / 2) {
                    this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.dangerarea").getString());
                }
            }
            if (this.tickCount % 200 == 0) {
                this.updateFatigue();
                this.updateFriendNorma(0.01F, 5);
                if (this.hungerMeter > 0) {
                    this.setHungerMeter(this.hungerMeter - 1);
                    if (this.tickCount % 4000 == 0) {
                        if (this.hungerMeter < 50) {
                            this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.starvation").getString());
                        }
                    }
                } else {
                    this.updateFriendNorma(-0.1F, 7);
                    this.mood -= 20;
                }
                if (this.doFarming && this.isTame()) {
                    boolean hasSpace = false;
                    for (int i = 7; i < this.inventory.getContainerSize(); i++) {
                        if (this.inventory.getItem(i).isEmpty()) {
                            hasSpace = true;
                        }
                    }
                    if (hasSpace) {
                        this.farmqueue.clear();
                        for (int x = -5; x < 6; x++) {
                            for (int y = -2; y < 3; y++) {
                                for (int z = -5; z < 6; z++) {
                                    BlockPos pos = new BlockPos(this.getBlockX() + x, this.getBlockY() + y, this.getBlockZ() + z);
                                    BlockState state = this.level().getBlockState(pos);
                                    Block block = state.getBlock();

                                    if (block instanceof CropBlock cropBlock) {
                                        if (cropBlock.isMaxAge(this.level().getBlockState(pos)) && ((CropBlock) block).getPlantType(this.level(), pos) == PlantType.CROP) {
                                            if (this.farmqueue.size() < 20) {
                                                this.farmqueue.offer(pos);
                                            } else break;
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }
            if (!this.day() && this.wasDay) {
                this.normaprogress = new double[8];
            } else if (this.day()) {
                this.wasDay = true;
            }
        }

        if (this.isInWater() && !this.onGround() && !this.jumping && this.getPose() != SITTING) {
            this.setPose(SWIMMING);
        } else {
            if (this.getPose() == SWIMMING) {
                this.setPose(STANDING);
            }
        }
    }

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
    public boolean doHurtTargetDamage(Entity pEntity, float dmgmod){
        if(pEntity == null){
            return false;
        }
        if (pEntity.level() instanceof ServerLevel t) {
            t.sendParticles(SWEEP_ATTACK, pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 1, 0.2, 0.2, 0.2, 0.3);
        }
        boolean flag = false;
        if (this.distanceTo(pEntity) < 3) {
            float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * this.getGenericDamageMod() * dmgmod * (Mth.clamp((5 * this.getCombatMod() / 10) + this.getRandom().nextInt(1, 7), 1, 6) + 3) / 6 * 0.66F;
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
                    ((LivingEntity) pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                }

                this.doEnchantDamageEffects(this, pEntity);
                this.setLastHurtMob(pEntity);
            }
        }
        return flag;
    }
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if(pEntity == null){
            return false;
        }
        if (pEntity.level() instanceof ServerLevel t) {
            t.sendParticles(SWEEP_ATTACK, pEntity.getX(), pEntity.getY() + 1, pEntity.getZ(), 1, 0.2, 0.2, 0.2, 0.3);
        }
        boolean flag = false;
        if (this.distanceTo(pEntity) < 3) {
            float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * this.getGenericDamageMod() * this.getAttackType() / 20 * (Mth.clamp((5 * this.getCombatMod() / 10) + this.getRandom().nextInt(1, 7), 1, 6) + 3) / 6 * 0.66F;
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
                    ((LivingEntity) pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                }

                this.doEnchantDamageEffects(this, pEntity);
                this.setLastHurtMob(pEntity);
            }
        }
        return flag;
    }    public void reapplyPosition() {
        this.setPos(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public void setTame(boolean pTamed) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (pTamed) {
            this.entityData.set(DATA_FLAGS_ID, (byte) (b0 | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte) (b0 & -5));
        }

        this.reassessTameGoals();
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
                boolean flag = (this.isOwnedBy(pPlayer) && this.isTame());
                if (this.isOwnedBy(pPlayer) || this.isTame()) {
                    if (itemstack.isEmpty() && !pPlayer.isCrouching()) {
                        this.patCounter = 20;
                    } else if (itemstack.isEmpty()) {
                        this.idleCounter = 0;
                    }
                }
                if (this.isEdible(itemstack)) {
                    return InteractionResult.CONSUME_PARTIAL;
                }
                return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else if (this.isTame() && this.isOwnedBy(pPlayer)) {
                //LOGGER.info(this.getFriendNorma() + "");
                if (this.isEdible(itemstack)) {
                    SweetHandler.doSweetEffect(this, itemstack);
                    this.updateFriendNorma(0.001F, 3);
                    this.socialInteraction += 1;
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    this.gameEvent(GameEvent.EAT);
                    return InteractionResult.CONSUME_PARTIAL;
                }
                else if (itemstack.is(ItemInit.SUMIKA_MEMORY.get())) {

                    if (itemstack.getOrCreateTag().contains("juicecraft.memories")) {
                        SumikaMemory temp = SumikaMemory.deserialize(itemstack.getOrCreateTag().getByteArray("juicecraft.memories"));
                        if (temp.verifyValid(this)) {
                            this.loadMemory(temp);
                            if (!pPlayer.getAbilities().instabuild) {
                                itemstack.shrink(1);
                            }
                            this.setHealth(this.getMaxHealth() / 2);
                            this.deathCounter = 7 - recoveryDifficulty;
                            this.getEntityData().set(FRIEND_ISDYING, false);
                            this.isDying = false;
                            this.playSound(RECOVERY.get(), 1, 1);
                            this.playVoice(this.getRecovery());
                            this.spawnHorizontalParticles();
                            this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.revive").getString());
                        } else {
                            return InteractionResult.PASS;
                        }
                    } else {
                        this.playSound(MEMORY_WRITE.get(), 1, 1);
                        itemstack.getOrCreateTag().putByteArray("juicecraft.memories", new SumikaMemory(this).serialize());
                    }


                    return InteractionResult.SUCCESS;
                }
                else {

                    if (!itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching() || (itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching())) {

                        if (pPlayer instanceof ServerPlayer serverPlayer) {

                            this.playVoice(this.getInteract());
                            serverPlayer.openMenu(new FriendMenuProvider(this), buffer -> buffer.writeVarInt(this.getId()));

                        }
                        return InteractionResult.SUCCESS;
                    } else if ((!itemstack.isEmpty() && this.isOwnedBy(pPlayer) && !pPlayer.isCrouching()) && (!this.isInWater() || this.onGround())) {
                        this.setFriendInSittingPose(!this.getInSittingPose());
                        if (sleepy() && animateSleep()) {
                            this.setPose(Pose.SLEEPING);
                        } else {
                            this.setPose(STANDING);
                        }
                    } else if (itemstack.isEmpty() && this.isOwnedBy(pPlayer)) {
                            petEvent();
                        this.socialInteraction++;
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if ((itemstack.is(ItemInit.ORANGE.get()) || itemstack.is(GOLDEN_ORANGE.get())) && !this.isAngry() && this.mood > 50) {
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (captureDifficulty > 1 && itemstack.is(GOLDEN_ORANGE.get())) {
                    captureDifficulty--;
                }
                if (this.random.nextInt(captureDifficulty) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                    this.tame(pPlayer);
                    this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.tame").getString());
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.mood -= 10;
                }
                return InteractionResult.CONSUME_PARTIAL;
            }
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (this.level().isClientSide) {
                boolean flag = (this.isOwnedBy(pPlayer) && this.isTame());
                if (this.isOwnedBy(pPlayer) || this.isTame()) {
                    if (itemstack.isEmpty() && !pPlayer.isCrouching()) {
                        this.patCounter = 20;
                        this.isembarassed = 20;
                    } else if (itemstack.isEmpty()) {
                        this.idleCounter = 0;
                    }
                }
                if (this.isEdible(itemstack)) {
                    return InteractionResult.CONSUME_PARTIAL;
                }
                return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else {
                if (this.isTame() && this.isOwnedBy(pPlayer)) {

                    if (!itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {

                        if (pPlayer instanceof ServerPlayer serverPlayer) {

                            this.playVoice(this.getInteract());
                            serverPlayer.openMenu(new FriendMenuProvider(this), buffer -> buffer.writeVarInt(this.getId()));

                        }
                        return InteractionResult.SUCCESS;
                    } else if (itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {
                        this.setFriendInSittingPose(!this.getInSittingPose());
                        if (sleepy() && animateSleep()) {
                            this.setPose(Pose.SLEEPING);
                        } else {
                            this.setPose(STANDING);
                        }
                    } else if (itemstack.isEmpty() && this.isOwnedBy(pPlayer)) {
                        if (this.getRandom().nextInt(30) < 3) {
                            this.playTimedVoice(this.getBattle());
                            this.swing(InteractionHand.MAIN_HAND);
                            this.attackplayertoo = true;
                            this.level().broadcastEntityEvent(this, (byte) 6);
                            this.mood -= 5;
                        }
                        this.socialInteraction++;
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }

    public void setFriendInSittingPose(boolean sit) {
        this.isSitting = sit;
        this.entityData.set(FRIEND_ISSITTING, sit);
    }

    public CombatSettings getCombatSettings() {
        return CombatSettings.decodeHash(this.getEntityData().get(FRIEND_COMBATSETTINGS));
    }

    public boolean[] getSkillEnabled() {
        return SkillManager.decodeBooleanHash(this.getEntityData().get(FRIEND_SKILLENABLED));
    }

    public void setSkillEnabled(boolean[] b) {
        this.skillEnabled = b;
        this.getEntityData().set(FRIEND_SKILLENABLED, SkillManager.makeBooleanHash(b));
    }

    public int getSkillPoints() {
        return this.getEntityData().get(FRIEND_SKILLPOINTS);
    }

    public void setSkillPoints(int a) {
        this.skillPoints = a;
        this.getEntityData().set(FRIEND_SKILLPOINTS, a);
    }

    void registerCustomGoals() {
        if (this.aggression > 75) {
            this.targetSelector.addGoal(6, new FriendNearestAttackableTargetGoal<>(this, Player.class, 0, true, false, this::testMood));
        }
        this.registerAdditionalGoals();
    }

    boolean testMood(LivingEntity a) {
        if (this.mood < 55) {
            this.attackplayertoo = true;
            return true;
        }
        return false;
    }

    abstract void registerAdditionalGoals();

    public boolean isEdible(ItemStack pStack) {
        return pStack.is(ItemInit.ORANGE.get()) || pStack.is(ItemInit.GOLDEN_ORANGE.get()) || pStack.getItem() instanceof SweetItem || pStack.is(COOKIE);
    }

    void loadMemory(SumikaMemory memory) {
        this.combatSettings = memory.settings;
        this.setIsWandering(memory.wander);
        this.setIsFarming(memory.farm);
        this.setFriendNorma((float) memory.normalevel, -1);
        this.setSpecialDialogueEnabled(memory.specialsenabled);
        this.updateCombatSettings();
    }

    public boolean lockLookAround() {
        return this.getAttackCounter() <= 0;
    }

    public boolean shouldShowWeapon() {
        return true;
    }
    public boolean forceShowWeapon(){
        return false;
    }
    public void synchronizeLookAngle() {
        if (this.level() instanceof ServerLevel level) {
            int l = Mth.floor(this.getYRot() * 256.0F / 360.0F);
            int k1 = Mth.floor(this.getXRot() * 256.0F / 360.0F);
            level.getChunkSource().broadcastAndSend(this, new ClientboundMoveEntityPacket.Rot(this.getId(), (byte) l, (byte) k1, this.onGround()));
        }
    }

    public void alignBodyWithHeadAngle() {
        this.yBodyRot = this.yHeadRot;
        this.yBodyRotO = this.yHeadRotO;
    }

    public boolean day() {
        long time = this.level().getDayTime();

        return time < 12300 || time > 23850;
    }

    public boolean idle() {
        return !this.isHoldingThrowable() && this.walkAnimation.speed() < 0.1F && !this.isDescending() && !this.isAggressive() && this.onGround() && this.canDoThings() && this.shakeAnimO == 0;
    }

    public boolean sleepy() {
        return idle() && !this.day();
    }
    public ArrayList<EntityDataAccessor<Integer>> counters;

    public boolean animateSleep() {
        return this.getFeetBlockState().isBed(this.level(), new BlockPos(this.getBlockX(), this.getBlockY() - 1, this.getBlockZ()), null);
    }

    public boolean additionalInspectConditions() {
        return true;
    }

    public void tryCounter(LivingAttackEvent event) {
        if (event.getSource().getEntity() != null && !this.isDying && !this.isAttackLockedOut()) {
            if (this.getAttackType() == 50 && this.getAttackCounter() > this.getCounterTiming() / this.getAttackSpeed()) {
                event.setCanceled(true);
            } else if (FriendDefense.shouldDefendAgainst(this)) {
                this.setAttackCounter(34);
                this.setAttackType(50);
                this.setSyncBoolean(FLAGFORRESET,true);
                this.playTimedVoice(this.getEvade());
                this.playSound(COUNTER_BLOCK.get());
                event.setCanceled(true);
            }
        }
    }

    public int getSyncInt(EntityDataAccessor<Integer> accessor) {
        return this.getEntityData().get(accessor);
    }

    public void setSyncInt(EntityDataAccessor<Integer> accessor, int n) {
        this.getEntityData().set(accessor, n);
    }

    public void setSyncBoolean(EntityDataAccessor<Boolean> accessor, boolean n) {
        this.getEntityData().set(accessor, n);
    }

    public boolean getSyncBoolean(EntityDataAccessor<Boolean> accessor) {
        return this.getEntityData().get(accessor);
    }

    public boolean canBeSeenAsEnemy() {
        return this.canBeSeenByAnyone();
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

    protected void actuallyHurt(@NotNull DamageSource pSource, float pAmount) {
        if (!this.isInvulnerableTo(pSource)) {
            this.playVoice(this.getHurt(pAmount));
            this.mood -= (int) (3 * this.getPeaceAffinityModifier());
            super.actuallyHurt(pSource, pAmount);
        }
    }

    @Override
    public @NotNull CombatTracker getCombatTracker() {
        return this.combatTracker;
    }

    public void swing(InteractionHand pHand) {
        this.swing(pHand, false);
    }

    @Override
    public void swing(InteractionHand pHand, boolean pUpdateSelf) {
        ItemStack stack = this.getItemInHand(pHand);
        if (this.getAttackCounter() <= 0 && !this.getIsDying() && !this.isAttackLockedOut()) {
            if (!stack.isEmpty()) {
                this.doMeleeAttack();
            } else {
                this.setAttackCounter(10);
                this.setAttackType(10);
            }
        }
    }

    public ItemStack getMainHandItem() {
        return this.getFriendWeapon();
    }

    public ItemStack getOffhandItem() {
        return this.getItemBySlot(EquipmentSlot.OFFHAND);
    }

    @Override
    public float getVoicePitch() {
        return 1F;
    }

    @Override
    public @NotNull Vec3 handleRelativeFrictionAndCalculateMovement(@NotNull Vec3 pDeltaMovement, float pFriction) {
        this.moveRelative(this.getFrictionInfluencedSpeed(pFriction), pDeltaMovement);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());

        return this.getDeltaMovement();
    }

    private float getFrictionInfluencedSpeed(float pFriction) {
        return this.onGround() ? this.getSpeed() * (0.21600002F / (pFriction * pFriction * pFriction)) : this.getFlyingSpeed();
    }

    private Vec3 handleOnClimbable(Vec3 pDeltaMovement) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            float f = 0.15F;
            double d0 = Mth.clamp(pDeltaMovement.x, -0.15F, 0.15F);
            double d1 = Mth.clamp(pDeltaMovement.z, -0.15F, 0.15F);
            double d2 = Math.max(pDeltaMovement.y, -0.15F);

            pDeltaMovement = new Vec3(d0, d2, d1);
        }
        return pDeltaMovement;
    }    public void playSound(SoundEvent pSound, float pVolume, float pPitch) {
        super.playSound(SoundEvent.createFixedRangeEvent(pSound.getLocation(), 64), pVolume, pPitch);

    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return POSES.getOrDefault(pPose, new EntityDimensions(0.6F, 1.8F, false));
    }

    @Override
    public boolean isSleeping() {
        return this.getPose() == Pose.SLEEPING;
    }    public boolean isFree(double pX, double pY, double pZ) {
        if (this.isInWater() && this.getFriendSwimCounter() != 0) {
            //LOGGER.info(this.startfloattimer +"");
            return false;
        }
        return super.isFree(pX, pY, pZ);
    }

    @Override
    public void stopSleeping() {/*yucky method*/}

    @Nullable
    @Override
    public Direction getBedOrientation() {
        BlockPos blockpos = this.getOnPos();
        BlockState state = this.getFeetBlockState();
        return !state.isBed(level(), blockpos, this) ? Direction.UP : state.getBedDirection(level(), blockpos);
    }

    public void broadcastBreakEvent(EquipmentSlot pSlot) {
        if (pSlot == EquipmentSlot.MAINHAND) {
            this.appendEventLog(Component.translatable("juicecraft.menu." + this.getFriendName().toLowerCase() + ".eventlog.breakweapon").getString());
        }
        super.broadcastBreakEvent(pSlot);
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

    public abstract SoundEvent getHurt(float dmg);

    public double getPeaceAffinityModifier() {
        return (100 - this.aggression) * 0.01;
    }

    public float getSurfaceWaterDistanceFromEye() {
        float eyeheight = (float) this.getEyeY();

        BlockPos toppos = BlockPos.containing(this.getX(), eyeheight, this.getZ());
        int topheight = toppos.getY();

        FluidState fluidstate = this.level().getFluidState(toppos);
        float fluidheight = fluidstate.getHeight(this.level(), toppos);
        if (this.canSwimInFluidType(fluidstate.getFluidType()) && fluidheight > 0) {
            return eyeheight - topheight - fluidheight;
        } else {
            return getBackupSurfaceWaterDistanceFromEye();
        }
    }

    public float getBackupSurfaceWaterDistanceFromEye() {
        float eyeheight = (float) this.getEyeY();


        BlockPos toppos = BlockPos.containing(this.getX(), eyeheight - 1, this.getZ());
        int topheight = toppos.getY();

        FluidState fluidstate = this.level().getFluidState(toppos);
        float fluidheight = fluidstate.getHeight(this.level(), toppos);
        if (this.canSwimInFluidType(fluidstate.getFluidType()) && fluidheight > 0) {
            return eyeheight - topheight - fluidheight;
        } else {
            return 1;
        }
    }

    private SoundEvent getFallDamageSound(int pHeight) {
        return pHeight > 4 ? this.getFallSounds().big() : this.getFallSounds().small();
    }

    @Override
    public void containerChanged(Container pContainer) {
        this.updateContainerEquipment();
        this.updateGear();
    }

    public void updateGear() {
        if (!this.level().isClientSide()) {
            this.getEntityData().set(FRIEND_WEAPON, this.inventory.getItem(1));
            if (this.inventory.getItem(1).getItem() instanceof BowItem) {
                this.goalSelector.removeGoal(this.bowGoal);
                this.goalSelector.removeGoal(this.crossbowGoal);
                this.goalSelector.removeGoal(this.snowballGoal);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else if (this.inventory.getItem(1).getItem() instanceof CrossbowItem) {
                this.goalSelector.removeGoal(this.crossbowGoal);
                this.goalSelector.removeGoal(this.bowGoal);
                this.goalSelector.removeGoal(this.snowballGoal);
                this.goalSelector.addGoal(4, this.crossbowGoal);
            } else if (this.isHoldingThrowable()) {
                this.goalSelector.removeGoal(this.crossbowGoal);
                this.goalSelector.removeGoal(this.bowGoal);
                this.goalSelector.removeGoal(this.snowballGoal);
                this.goalSelector.addGoal(4, this.snowballGoal);
            } else {
                this.goalSelector.removeGoal(this.snowballGoal);
                this.goalSelector.removeGoal(crossbowGoal);
                this.goalSelector.removeGoal(bowGoal);
            }
        }
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

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FriendMenu(pContainerId, pPlayerInventory, this);
    }    @Override
    public float getEyeHeight(Pose pPose) {
        return super.getEyeHeight(pPose);
    }

    @Override
    public void setPos(double x, double y, double z) {
        if (this.isInWater() && this.getFriendSwimCounter() <= 0) {
            double diff = y - this.getY();
            diff = diff / 4;
            this.setPosRaw(x, this.getY() + diff, z);
        } else {
            this.setPosRaw(x, y, z);
        }
        this.setBoundingBox(this.makeBoundingBox());
    }













}
