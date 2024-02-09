package com.usagin.juicecraft.particles;

import net.minecraft.core.particles.SimpleParticleType;

import static com.usagin.juicecraft.Init.ParticleInit.*;

public class DiceHandler {
    public static SimpleParticleType getDice(int n) {
        return switch (n) {
            case 0 -> DICEONE.get();
            case 1 -> DICETWO.get();
            case 2 -> DICETHREE.get();
            case 3 -> DICEFOUR.get();
            case 4 -> DICEFIVE.get();
            default -> DICESIX.get();
        };
    }
}
