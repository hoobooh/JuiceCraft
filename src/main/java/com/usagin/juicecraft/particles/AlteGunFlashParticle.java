package com.usagin.juicecraft.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class AlteGunFlashParticle extends AlteLightningParticle{
    public AlteGunFlashParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMultiplier, SpriteSet spriteset) {
        super(pLevel, pX, pY, pZ, pQuadSizeMultiplier, spriteset);
    }
    public static class GunFlashProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public GunFlashProvider(SpriteSet spriteSet){
            this.sprites=spriteSet;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {LOGGER.info("HJIT");
            return new AlteGunFlashParticle(pLevel, pX,pY,pZ, pXSpeed,this.sprites);
        }
    }
}
