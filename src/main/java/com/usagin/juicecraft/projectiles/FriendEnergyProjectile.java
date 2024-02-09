package com.usagin.juicecraft.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public abstract class FriendEnergyProjectile extends FriendProjectile {
    protected FriendEnergyProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected FriendEnergyProjectile(EntityType<? extends ThrowableProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    protected FriendEnergyProjectile(EntityType<? extends ThrowableProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(this.getParticle(), this.getX(), this.getY(), this.getZ(), 5, 0.1F, 0.1F, 0.1F, 0);
            this.discard();
        }

    }

    public abstract ParticleOptions getParticle();

    public void tick() {
        super.tick();
        if (this.tickCount > 400) {
            this.kill();
        }
    }
}
