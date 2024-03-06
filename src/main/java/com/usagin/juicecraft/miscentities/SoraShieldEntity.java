package com.usagin.juicecraft.miscentities;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SoraShieldEntity extends LivingEntity {
    public static AttributeSupplier.Builder getShieldAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.ATTACK_DAMAGE, 0);
    }

    public LivingEntity host;
    public double damagetaken = 0;
    public int lifetime = -100;

    public SoraShieldEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics = true;

    }

    public boolean canCollideWith(Entity pEntity) {
        return pEntity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(pEntity);
    }

    public void releaseEnergy(Sora sora) {
        sora.spawnParticlesInUpFacingCircle(this, 10, ParticleInit.SORA_ENERGY_PARTICLE.get());
        //this.playSound(SoraSoundInit.SHIELD_DETONATE.get());
        AABB box = this.getBoundingBox().inflate(5);
        List<Entity> list = this.level().getEntities(this, box);
        for (Entity e : list) {
            if (e instanceof LivingEntity entity) {
                if (!EnemyEvaluator.shouldDoHurtTarget(sora, entity)) {
                    continue;
                }
            }
            e.hurt(sora.damageSources().explosion(this, sora), (float) (this.damagetaken * sora.getSkillLevels()[3] * 3) / (2 + sora.getSkillLevels()[3]));
        }
        this.remove(RemovalReason.DISCARDED);
    }

    protected float ridingOffset(Entity pEntity) {
        return -2F;
    }

    @Override
    public void tick() {
        /*if(this.host!=null && this.getVehicle()==null){
            AlteLightningParticle.LOGGER.info(this.startRiding(this.host) +"");
        //this.startRiding(this.host);
        }*/
        if (this.lifetime != -100) {
            this.lifetime--;
        }
        if (this.lifetime == -10) {
            this.remove(RemovalReason.DISCARDED);
        }
        this.setDeltaMovement(Vec3.ZERO);
        super.tick();
        Minecraft mc = Minecraft.getInstance();
        float pPartialTick = mc.getPartialTick();
        if (this.host != null) {
            double d0 = Mth.lerp((double) pPartialTick, this.host.xOld, this.host.getX());
            double d1 = Mth.lerp((double) pPartialTick, this.host.yOld, this.host.getY());
            double d2 = Mth.lerp((double) pPartialTick, this.host.zOld, this.host.getZ());
            Vec3 one = new Vec3(d0, d1, d2);
            this.setPos(one);
        }

    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        UUID id = pCompound.getUUID("juicecraft.sora.shield.host");
        for(Entity e: this.level().getEntities(this,this.getBoundingBox().inflate(2))){
            if(e instanceof LivingEntity entity && e.getUUID().compareTo(id)==0){
                this.host=entity;
            }
        }
        this.lifetime = pCompound.getInt("juicecraft.sora.shield.lifetime");
        this.damagetaken = pCompound.getDouble("juicecraft.sora.shield.damagetaken");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.host != null) {
            pCompound.putUUID("juicecraft.sora.shield.host", this.host.getUUID());
            pCompound.putInt("juicecraft.sora.shield.lifetime", this.lifetime);
            pCompound.putDouble("juicecraft.sora.shield.damagetaken", this.damagetaken);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }


}
