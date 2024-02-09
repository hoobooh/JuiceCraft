package com.usagin.juicecraft.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class AlteSelfDestructParticle extends AlteLightningParticle {
    float accel = 0.05F;

    public AlteSelfDestructParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMultiplier, SpriteSet spriteset) {
        super(pLevel, pX, pY, pZ, pQuadSizeMultiplier, spriteset);
        this.lifetime = 40;
        this.quadSize = 0.4F;

    }

    @Override
    public void tick() {
        this.roll += accel;
        this.accel += 0.01F;
        this.oRoll = this.roll;
        super.tick();

        this.quadSize += 0.13;

    }

    public static class SelfDestructProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public SelfDestructProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AlteSelfDestructParticle(pLevel, pX, pY, pZ, pXSpeed, this.sprites);
        }
    }
}
