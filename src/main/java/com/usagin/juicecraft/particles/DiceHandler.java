package com.usagin.juicecraft.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import static com.usagin.juicecraft.Init.ParticleInit.*;

public class DiceHandler {
    public static SimpleParticleType getDice(int n){
        return switch (n) {
            case 1 -> DICEONE.get();
            case 2 -> DICETWO.get();
            case 3 -> DICETHREE.get();
            case 4 -> DICEFOUR.get();
            case 5 -> DICEFIVE.get();
            default -> DICESIX.get();
        };
    }
}
