package com.usagin.juicecraft.projectiles;

import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public abstract class FriendProjectile extends ThrowableProjectile {
    protected FriendProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected FriendProjectile(EntityType<? extends ThrowableProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    protected FriendProjectile(EntityType<? extends ThrowableProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    protected boolean canHitEntity(Entity pTarget) {
        if (this.getOwner() instanceof Friend friend && pTarget instanceof LivingEntity livingEntity) {
            if (EnemyEvaluator.shouldDoHurtTarget(friend, livingEntity)) {
                return super.canHitEntity(pTarget);
            } else {
                return false;
            }
        }
        return super.canHitEntity(pTarget);
    }

    public abstract ResourceLocation getTexture();

    public abstract RenderType getRenderType();

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
    }
}
