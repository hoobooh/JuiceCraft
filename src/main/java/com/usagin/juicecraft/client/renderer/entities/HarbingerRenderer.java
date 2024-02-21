package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.client.models.harbinger.HarbingerModel;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.client.renderer.HarbingerSoulLayer;
import com.usagin.juicecraft.enemies.Harbinger;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class HarbingerRenderer extends MobRenderer<Harbinger,HarbingerModel<Harbinger>> {
    public static ResourceLocation EYES = new ResourceLocation(MODID,"textures/entities/harbinger/harbinger_eye_layer.png");
    public static ResourceLocation MAIN = new ResourceLocation(MODID,"textures/entities/harbinger/harbinger.png");
    public static ResourceLocation GLOW = new ResourceLocation(MODID,"textures/entities/harbinger/harbinger_glow.png");
    ResourceLocation skinloc;
    public HarbingerRenderer(EntityRendererProvider.Context pContext, HarbingerModel<Harbinger> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
        this.addLayer(new FriendEyeLayer<>(this,EYES,true));
        this.addLayer(new HarbingerSoulLayer(this));
    }
    public HarbingerRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new HarbingerModel<>(pContext.bakeLayer(HarbingerModel.LAYER_LOCATION)), 0.5F);
    }
    public void render(Harbinger pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(Harbinger pEntity) {
        return MAIN;
    }

}
