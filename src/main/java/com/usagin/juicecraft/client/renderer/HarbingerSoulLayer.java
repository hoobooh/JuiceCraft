package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.usagin.juicecraft.client.models.harbinger.HarbingerModel;
import com.usagin.juicecraft.client.renderer.entities.HarbingerRenderer;
import com.usagin.juicecraft.enemies.Harbinger;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class HarbingerSoulLayer extends RenderLayer<Harbinger, HarbingerModel<Harbinger>> {
    private final HarbingerModel<Harbinger> model;

    public HarbingerSoulLayer(RenderLayerParent<Harbinger, HarbingerModel<Harbinger>> pRenderer) {
        super(pRenderer);
        this.model=pRenderer.getModel();
    }


    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Harbinger pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity.isAbsorbingLife()) {
            int n = pLivingEntity.getSyncInt(Harbinger.LIFECOUNTER);
            float f = (float)pLivingEntity.tickCount + pPartialTicks;
            EntityModel<Harbinger> entitymodel = this.model();
            entitymodel.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
            this.getParentModel().copyPropertiesTo(entitymodel);
            VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(this.getTextureLocation(), true));
            entitymodel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            entitymodel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, (float) (-0.00125*(n-20)*(n-20)+0.5));
        }
    }

    protected float xOffset(float pTickCount) {
        return pTickCount * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return HarbingerRenderer.GLOW;
    }

    protected EntityModel<Harbinger> model() {
        return this.model;
    }
}
