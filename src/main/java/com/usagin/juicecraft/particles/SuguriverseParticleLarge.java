package com.usagin.juicecraft.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.sun.jna.platform.win32.OpenGL32;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.RenderTypeHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;

import java.util.SplittableRandom;



public class SuguriverseParticleLarge extends AlteLightningParticle {

    public SuguriverseParticleLarge(ClientLevel pLevel, double pX, double pY, double pZ, double pQuadSizeMultiplier, SpriteSet spriteset) {
        super(pLevel, pX, pY, pZ,pQuadSizeMultiplier, spriteset);
        this.lifetime=15;
        this.friction=0.8F;
        this.quadSize=1.5F;
        this.setSpriteFromAge(spriteset);


    }
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet spriteSet){
            this.sprites=spriteSet;
        }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SuguriverseParticleLarge(pLevel, pX,pY,pZ, pXSpeed,this.sprites);
        }
    }
}

