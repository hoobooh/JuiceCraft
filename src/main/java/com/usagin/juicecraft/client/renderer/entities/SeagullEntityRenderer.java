package com.usagin.juicecraft.client.renderer.entities;

import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.models.seagull.SeagullEntityModel;
import com.usagin.juicecraft.enemies.Seagull;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SeagullEntityRenderer extends MobRenderer<Seagull, SeagullEntityModel<Seagull>> {
    public SeagullEntityRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new SeagullEntityModel<>(pContext.bakeLayer(SeagullEntityModel.LAYER_LOCATION)), 0.5F);
    }
    public SeagullEntityRenderer(EntityRendererProvider.Context pContext, SeagullEntityModel<Seagull> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }
    public static final ResourceLocation SEAGULLTEX = new ResourceLocation(JuiceCraft.MODID, "textures/entities/seagull/seagull.png");
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Seagull pEntity) {
        return SEAGULLTEX;
    }
}
