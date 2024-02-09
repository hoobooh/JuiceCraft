package com.usagin.juicecraft.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;


public class SuguriverseParticleLarge extends AlteLightningParticle {

    public SuguriverseParticleLarge(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMultiplier, SpriteSet spriteset) {
        super(pLevel, pX, pY, pZ, pQuadSizeMultiplier, spriteset);
        this.lifetime = 15;
        this.friction = 0.8F;
        this.quadSize = 1.5F;
        this.setSpriteFromAge(spriteset);


    }

    public static class SugPartProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public SugPartProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SuguriverseParticleLarge(pLevel, pX, pY, pZ, pXSpeed, this.sprites);
        }
    }
}

