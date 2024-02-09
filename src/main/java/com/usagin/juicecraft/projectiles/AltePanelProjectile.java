package com.usagin.juicecraft.projectiles;

import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import static com.usagin.juicecraft.Init.ParticleInit.ALTE_IMPACT_PARTICLE;
import static com.usagin.juicecraft.Init.ParticleInit.ALTE_LIGHTNING_PARTICLE;
import static com.usagin.juicecraft.Init.ProjectileInit.ALTE_PANEL_PROJECTILE;

public class AltePanelProjectile extends FriendEnergyProjectile {
    public AltePanelProjectile(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected AltePanelProjectile(EntityType<? extends ThrowableProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    protected AltePanelProjectile(EntityType<? extends ThrowableProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    public AltePanelProjectile(Friend source, float pX, float pY, float pZ) {
        super(ALTE_PANEL_PROJECTILE.get(), pX, pY, pZ, source.level());
        this.setOwner(source);
    }

    @Override
    public ParticleOptions getParticle() {
        return ALTE_LIGHTNING_PARTICLE.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel level && this.tickCount % 2 == 0) {
            level.sendParticles(ALTE_IMPACT_PARTICLE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(JuiceCraft.MODID, "textures/projectiles/alte_minigun_projectile.png");
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Friend alte = (Friend) this.getOwner();
        int i = 0;
        if (alte != null) {
            i = 15 + 2 * alte.getSkillLevels()[5];
        }
        entity.hurt(this.damageSources().explosion(this, this.getOwner()), (float) i);
    }



    @Override
    public RenderType getRenderType() {
        return RenderType.eyes(this.getTexture());
    }
}
