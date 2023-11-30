package com.usagin.juicecraft.friends;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.*;
import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.data.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class Friend extends Wolf implements ContainerListener, MenuProvider {
    int captureDifficulty;
    int aggression;
    int mood;
    int time;
    int invColumns;
    boolean isArmorable;
    boolean isModular;
    String name;
    boolean isShaking;
    float shakeAnim;
    float shakeAnimO;
    public SimpleContainer inventory = new SimpleContainer(12);
    float[] home;
    Relationships relationships;
    DialogueTree dialogueTree;
    CombatSettings combatSettings;
    SumikaMemory oldMemory;

    public String getFriendName() {
        return this.name;
    }

    public Friend(EntityType<? extends Wolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        initializeNew();
    }

    void initializeNew() {
        this.setCaptureDifficulty();
        this.setAggression();
        this.mood = 100;
        this.time = 0;
        this.socialInteraction = 100;
        this.setPersistenceRequired();
        this.setName();
        this.home = new float[4];
        this.initializeDialogueSettings();
        this.relationships = new Relationships();
        this.combatSettings = new CombatSettings();
        this.oldMemory = new SumikaMemory();
        this.setInventoryColumns();
        this.setArmorableModular();
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.createInventory();
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(4 + this.getInventoryColumns() * 3);
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

    abstract void setInventoryColumns();

    abstract void setArmorableModular();

    abstract void initializeDialogueSettings();

    abstract void setName();

    abstract void setAggression();

    abstract void setCaptureDifficulty();

    abstract void indicateTamed();

    abstract void petEvent(); //add happy event

    abstract void doRecoveryEvent();

    abstract DialogueTree parseDialogueTree(int[] dialogue);

    abstract Relationships parseRelationships(int[] relations);

    abstract CombatSettings parseCombatSettings(int[] combatSettings);

    abstract int[] convertDialogueTree(DialogueTree dialogue);

    abstract int[] convertRelationships(Relationships relations);

    abstract int[] convertCombatSettings(CombatSettings combatSettings);

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

    public boolean isArmor(ItemStack pStack) {
        return pStack.getItem() instanceof ArmorItem;
    }

    boolean testMood(LivingEntity a) {
        return this.mood < 15;
    }

    public int getInventoryColumns() {
        return this.invColumns;
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
        pCompound.putIntArray("juicecraft.dialogue", convertDialogueTree(this.dialogueTree));
        pCompound.putIntArray("juicecraft.relationships", convertRelationships(this.relationships));
        pCompound.putIntArray("juicecraft.csettings", convertCombatSettings(this.combatSettings));
        pCompound.putInt("juicecraft.social", this.socialInteraction);
        pCompound.putInt("juicecraft.mood", this.mood);
        pCompound.putInt("juicecraft.existed", 1);
        pCompound.putBoolean("Tame", this.isTame());
        if (this.getOwnerUUID() != null) {
            pCompound.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.getInt("juicecraft.existed") == 0) {
            this.initializeNew();
            return;
        }
        super.readAdditionalSaveData(pCompound);
        int[] temp = pCompound.getIntArray("juicecraft.home");
        if (temp.length != 0) {
            this.home = new float[]{temp[0], temp[1], temp[2], temp[3]};
        }
        this.dialogueTree = this.parseDialogueTree((pCompound.getIntArray("juicecraft.dialogue")));
        this.relationships = this.parseRelationships((pCompound.getIntArray("juicecraft.relationships")));
        this.combatSettings = this.parseCombatSettings((pCompound.getIntArray("juicecraft.csettings")));
        this.socialInteraction = (pCompound.getInt("juicecraft.social"));
        this.mood = (pCompound.getInt("juicecraft.mood"));
        this.setTame(pCompound.getBoolean("Tame"));
        UUID uuid;
        if (pCompound.hasUUID("Owner")) {
            uuid = pCompound.getUUID("Owner");
        } else {
            String s = pCompound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
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
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_FLAGS, (byte) 0);
    }
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Friend.class, EntityDataSerializers.BYTE);
    protected void setFlag(int pFlagId, boolean pValue) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);

        if (pValue) {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 | pFlagId));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte) (b0 & ~pFlagId));
        }

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
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        //add Friend wandering/idle ai goal
        this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        //this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D)); //...maybe in the future...
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new BegGoal(this, 8.0F));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, Seagull.class, true));
        this.targetSelector.addGoal(9, new ResetUniversalAngerTargetGoal<>(this, true));
        if (this.aggression > 75) {
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::testMood));
        }

    }

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(ItemInit.ORANGE.get()) || itemstack.is(ItemInit.GOLDEN_ORANGE.get()) && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (this.isTame()) {
            if (itemstack.is(Items.COOKIE) && this.getHealth() < this.getMaxHealth()) {
                this.heal(1);
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.gameEvent(GameEvent.EAT, this);
                this.socialInteraction++;
                return InteractionResult.SUCCESS;
            } else if (itemstack.isEdible()) {
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, itemstack.getFoodProperties(this).getNutrition()));
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.socialInteraction++;
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else if (itemstack.is(ItemInit.SUMIKA_MEMORY.get())) {
                this.loadMemory();
                this.doRecoveryEvent(); //recovery animation
                return InteractionResult.SUCCESS;
            } else {
                if (itemstack.isEmpty() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {
                    ObjectOutputStream oos;
                    ByteArrayOutputStream bos = null;
                    try {
                        bos = new ByteArrayOutputStream();
                        oos = new ObjectOutputStream(bos);
                        oos.writeObject(this);
                        oos.flush();
                    } catch (Exception E) {
                        //do nothing
                    }
                    assert bos != null;
                    ByteArrayOutputStream finalBos = bos;
                    if (pPlayer instanceof ServerPlayer serverPlayer) {
                        serverPlayer.openMenu(new FriendMenuProvider(this), buffer -> finalBos.toByteArray());
                    }
                    return InteractionResult.SUCCESS;
                } else if (itemstack.isEmpty() && this.isOwnedBy(pPlayer)) {
                    if (this.mood > 50) {
                        petEvent();
                        this.mood++;
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                        this.mood--;
                    }
                    this.socialInteraction++;
                    return InteractionResult.SUCCESS;
                }
            }
        } else if ((itemstack.is(ItemInit.ORANGE.get()) || itemstack.is(ItemInit.GOLDEN_ORANGE.get())) && !this.isAngry() && this.mood == 100) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (captureDifficulty > 1 && itemstack.is(ItemInit.GOLDEN_ORANGE.get())) {
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
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
        return InteractionResult.SUCCESS;
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
    }
}
