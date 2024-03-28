package com.usagin.juicecraft.miscentities;

import com.usagin.juicecraft.Init.ParticleInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import com.usagin.juicecraft.Init.sounds.UniversalSoundInit;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SoraChargeEntity extends LivingEntity {
    public static AttributeSupplier.Builder getChargeAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.ATTACK_DAMAGE, 0);
    }

    public Sora sora;
    public int soraid;
    public int lifetime = -100;

    public SoraChargeEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setInvulnerable(true);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.setDiscardFriction(true);
        this.setCustomNameVisible(false);
    }
    public boolean shouldShowName() {
        return false;
    }


    public boolean canCollideWith(Entity pEntity) {
        return pEntity.canBeCollidedWith() && !this.isPassengerOfSameVehicle(pEntity);
    }
    public void hurtAllTargets(){
        AABB box = this.getBoundingBox().inflate((144F-this.lifetime)/12);
        List<Entity> list = this.level().getEntities(this,box);
        ArrayList< BlockPos> poslist = new ArrayList<>();

        double p = (144D - this.lifetime)/12;

        for(double x = this.getX() - p; x <= this.getX() + p; x++){
            for(double y = this.getY() - p; y <= this.getY() + p; y++){
                for(double z = this.getZ() - p; z <= this.getZ() + p; z++){
                    if(Math.sqrt(this.distanceToSqr(new Vec3(x,y,z))) <= p)
                        poslist.add(new BlockPos((int) x,(int) y,(int) z));
                }
            }
        }

        for(BlockPos pos: poslist){
            if(!this.level().getBlockState(pos).isAir()){
                if(ForgeHooks.canEntityDestroy(this.level(),pos,this) && this.level().getBlockState(pos).getBlock().defaultDestroyTime() < 4 && this.level().getBlockState(pos).getBlock().getExplosionResistance() < 7){
                    this.level().destroyBlock(pos,true);
                }
            }
        }

        for(Entity e: list){
            if(e instanceof LivingEntity liv){
                if(!EnemyEvaluator.shouldDoHurtTarget(this.sora,liv)){
                    continue;
                }
            }
            int a = this.sora.getSkillLevels()[5];
            float n = -4/(0.15F*a+1) + 4;
            e.hurt(this.sora.damageSources().mobAttack(this.sora), 5 * n);
        }
        box = this.getBoundingBox().inflate((144F-this.lifetime)/6);
        list = this.level().getEntities(this,box);
        for(Entity e: list){
            if(e instanceof LivingEntity liv){
                if(!EnemyEvaluator.shouldDoHurtTarget(this.sora,liv)){
                    continue;
                }
            }
            e.addDeltaMovement(this.position().subtract(e.position()).multiply(0.05,0.05,0.05));
        }
    }
    @Override
    public void tick() {
        if(this.tickCount%5==0 && !this.level().isClientSide()){
            this.playSound(SoraSoundInit.SORA_CHARGE_IDLE.get());
            double p = (144D - this.lifetime)/12;
            this.spawnParticlesInRandomSpreadAtEntity(this,(int) (p * 5),(int) (p/4),0,(ServerLevel) this.level(), ParticleInit.SORA_ENERGY_PARTICLE.get());
        }
        if (this.lifetime != -100) {
            this.lifetime--;
        }
        if (this.lifetime == 0) {
            this.playSound(UniversalSoundInit.FRIEND_DEATH.get());
            this.spawnParticlesInRandomSpreadAtEntity(this,60,7,0,(ServerLevel) this.level(), ParticleInit.SORA_ENERGY_PARTICLE.get());
            this.remove(RemovalReason.DISCARDED);
        }
        //this.setDeltaMovement(Vec3.ZERO);
        if(this.sora != null){
            this.hurtAllTargets();
        }
        super.tick();


    }
    public <T extends ParticleOptions> void spawnParticlesInRandomSpreadAtEntity(Entity entity, int count, float radius, float distance, ServerLevel sLevel, T type) {
        float targetX = (float) entity.getX();
        float targetZ = (float) entity.getZ();
        float targetY = (float) entity.getEyeY();

        sLevel.sendParticles(type, targetX, targetY, targetZ, count, radius, radius, radius, 1);
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        UUID id = pCompound.getUUID("juicecraft.sora.charge.sora");
        for(Entity e: this.level().getEntities(this,this.getBoundingBox().inflate(2))){
            if(e instanceof Sora entity && e.getUUID().compareTo(id)==0){
                this.sora =entity;
                this.soraid =this.sora.getId();
            }
        }
        this.lifetime = pCompound.getInt("juicecraft.sora.charge.lifetime");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.sora != null) {
            pCompound.putUUID("juicecraft.sora.charge.sora", this.sora.getUUID());
            pCompound.putInt("juicecraft.sora.charge.lifetime", this.lifetime);
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
