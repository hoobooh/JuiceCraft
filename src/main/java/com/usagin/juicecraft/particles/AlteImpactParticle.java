package com.usagin.juicecraft.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AlteImpactParticle extends EnergyParticle {
    AlteImpactParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, spriteSet);
        this.quadSize *= 0.4F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class AlteImpactProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public AlteImpactProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            AlteImpactParticle energyparticle = new AlteImpactParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            return energyparticle;
        }
    }
}
