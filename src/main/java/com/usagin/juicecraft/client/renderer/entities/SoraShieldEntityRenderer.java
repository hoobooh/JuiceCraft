package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.models.sora.ShieldEntityModel;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.client.renderer.FriendEyeTransparentLayer;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SoraShieldEntityRenderer extends LivingEntityRenderer<SoraShieldEntity, ShieldEntityModel> {
    public SoraShieldEntityRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new ShieldEntityModel(pContext.bakeLayer(ShieldEntityModel.LAYER_LOCATION)), 0);
    }
    public SoraShieldEntityRenderer(EntityRendererProvider.Context pContext, ShieldEntityModel pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
        this.addLayer(new FriendEyeTransparentLayer<>(this, main,true,0.5F));
    }
    public static ResourceLocation main = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/shield");
    public static ResourceLocation none = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/shield_none");
    @Override
    public ResourceLocation getTextureLocation(SoraShieldEntity pEntity) {
        return none;
    }
}
