package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.slf4j.Logger;

public abstract class FriendEyeLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    public boolean visible = false;

    public FriendEyeLayer(RenderLayerParent<T,M> pRenderer) {
        super(pRenderer);
    }
    private static final Logger LOGGER = LogUtils.getLogger();

    abstract public @NotNull RenderType renderType();

    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if(this.visible){
            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.renderType());
            this.getParentModel().renderToBuffer(pPoseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
