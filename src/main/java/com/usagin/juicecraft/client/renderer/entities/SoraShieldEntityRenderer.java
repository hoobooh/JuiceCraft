package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.models.sora.ShieldEntityModel;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.client.renderer.FriendEyeTransparentLayer;
import com.usagin.juicecraft.miscentities.SoraChargeEntity;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SoraShieldEntityRenderer extends LivingEntityRenderer<SoraShieldEntity, ShieldEntityModel> {
    public SoraShieldEntityRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new ShieldEntityModel(pContext.bakeLayer(ShieldEntityModel.LAYER_LOCATION)), 0);
    }
    public FriendEyeTransparentLayer<SoraShieldEntity,ShieldEntityModel> layer=new FriendEyeTransparentLayer<>(this, main,true,0.2F);
    public SoraShieldEntityRenderer(EntityRendererProvider.Context pContext, ShieldEntityModel pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
        this.addLayer(this.layer);
    }
    protected boolean shouldShowName(SoraChargeEntity pEntity) {
        return false;

    }
    public static ResourceLocation main = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/shield.png");
    public static ResourceLocation none = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/shield_none.png");
    @Override
    public ResourceLocation getTextureLocation(SoraShieldEntity pEntity) {
        return none;
    }
    public void render(SoraShieldEntity shield, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        /*if(shield.sora!=null){
            pPoseStack.translate(-shield.getX()+shield.sora.getX(),-shield.getY()+shield.sora.getY(),-shield.getZ()+shield.sora.getZ());
        }*/
        this.layer.transparency= (float) Math.abs(((Math.sin((shield.tickCount/20F)%80)) / 4F));

        super.render(shield, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
