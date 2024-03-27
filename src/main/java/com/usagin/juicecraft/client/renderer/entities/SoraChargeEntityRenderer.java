package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.models.sora.ChargeEntityModel;
import com.usagin.juicecraft.client.models.sora.ShieldEntityModel;
import com.usagin.juicecraft.client.renderer.FriendEyeTransparentLayer;
import com.usagin.juicecraft.miscentities.SoraChargeEntity;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SoraChargeEntityRenderer extends LivingEntityRenderer<SoraChargeEntity, ChargeEntityModel> {
    public SoraChargeEntityRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new ChargeEntityModel(pContext.bakeLayer(ChargeEntityModel.LAYER_LOCATION)), 0);
    }
    public FriendEyeTransparentLayer<SoraChargeEntity, ChargeEntityModel> layer=new FriendEyeTransparentLayer<>(this, main,true,0.2F);
    public FriendEyeTransparentLayer<SoraChargeEntity, ChargeEntityModel> layer2=new FriendEyeTransparentLayer<>(this, inner,true,0.8F);
    public SoraChargeEntityRenderer(EntityRendererProvider.Context pContext, ChargeEntityModel pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
        this.addLayer(this.layer);
        this.addLayer(this.layer2);
    }
    public static ResourceLocation main = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/charge.png");
    public static ResourceLocation inner = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/charge_inner.png");
    protected boolean shouldShowName(SoraChargeEntity pEntity) {
        return false;

    }
    public static ResourceLocation none = new ResourceLocation(JuiceCraft.MODID,"textures/entities/sora/charge_none.png");
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SoraChargeEntity pEntity) {
        return none;
    }
    public void render(SoraChargeEntity shield, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        /*if(shield.sora!=null){
            pPoseStack.translate(-shield.getX()+shield.sora.getX(),-shield.getY()+shield.sora.getY(),-shield.getZ()+shield.sora.getZ());
        }*/
        //this.layer.transparency= (float) Math.abs(((Math.sin((shield.tickCount/20F)%80)) / 4F));

        super.render(shield, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
