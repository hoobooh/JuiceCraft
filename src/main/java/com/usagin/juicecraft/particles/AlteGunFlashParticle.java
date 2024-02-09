package com.usagin.juicecraft.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class AlteGunFlashParticle extends AlteLightningParticle {
    public AlteGunFlashParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMultiplier, SpriteSet spriteset) {
        super(pLevel, pX, pY, pZ, pQuadSizeMultiplier, spriteset);
        this.lifetime = 6;
        this.quadSize = 0.2F;
        this.setAlpha(this.alpha *= 0.3F);
    }

    public void tick() {
        super.tick();
        this.quadSize *= 1.3;
    }

    public static class GunFlashProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public GunFlashProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AlteGunFlashParticle(pLevel, pX, pY, pZ, pXSpeed, this.sprites);
        }
    }
}
