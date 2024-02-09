package com.usagin.juicecraft.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class EnergyParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    EnergyParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.friction = 0.7F;
        this.gravity = 0;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += pXSpeed * 0.4D;
        this.yd += pYSpeed * 0.4D;
        this.zd += pZSpeed * 0.4D;
        this.quadSize *= 0.8F;
        this.lifetime = 3 * Math.max((int) (6.0D / (Math.random() * 0.8D + 0.6D)), 1);
        this.hasPhysics = false;
        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);
        this.setAlpha(((float) this.lifetime - this.age) / this.lifetime);
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void move(double pX, double pY, double pZ) {
        this.setBoundingBox(this.getBoundingBox().move(pX, pY, pZ));
        this.setLocationFromBoundingbox();
    }

    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    @OnlyIn(Dist.CLIENT)
    public static class AlteEnergyProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public AlteEnergyProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            EnergyParticle energyparticle = new EnergyParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            return energyparticle;
        }
    }

}
