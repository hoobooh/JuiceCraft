package com.usagin.juicecraft.projectiles;


import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import static com.usagin.juicecraft.Init.ParticleInit.ALTE_IMPACT_PARTICLE;
import static com.usagin.juicecraft.Init.ParticleInit.ALTE_LIGHTNING_PARTICLE;
import static com.usagin.juicecraft.Init.ProjectileInit.ALTE_MINIGUN_PROJECTILE;

public class AlteMinigunProjectile extends FriendEnergyProjectile {


    public AlteMinigunProjectile(Friend source, double pX, double pY, double pZ) {
        super(ALTE_MINIGUN_PROJECTILE.get(), pX, pY, pZ, source.level());
        this.setOwner(source);
    }

    public AlteMinigunProjectile(EntityType<AlteMinigunProjectile> alteMinigunProjectileEntityType, Level level) {
        super(alteMinigunProjectileEntityType,level);
    }
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Friend alte = (Friend) this.getOwner();
        int i = 0;
        if(alte!=null){
            i = (int)(10+alte.getSkillLevels()[5]);
        }
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float)i);
    }
    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(JuiceCraft.MODID,"textures/projectiles/alte_minigun_projectile.png");
    }
    @Override
    public RenderType getRenderType(){
        return RenderType.eyes(this.getTexture());
    }
    @Override
    protected void defineSynchedData() {

    }
    @Override
    public ParticleOptions getParticle() {
        return ALTE_IMPACT_PARTICLE.get();
    }
}
