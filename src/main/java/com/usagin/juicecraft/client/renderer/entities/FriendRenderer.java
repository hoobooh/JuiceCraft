package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.client.models.sora.SoraEntityModel;
import com.usagin.juicecraft.client.renderer.FriendItemInHandLayer;
import com.usagin.juicecraft.client.renderer.FriendItemOnBackLayer;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public abstract class FriendRenderer<T extends Friend, M extends FriendEntityModel<T>> extends MobRenderer<T, M> {
    FriendItemInHandLayer<T, M> pLayer;
    FriendItemOnBackLayer<T, M> pBackLayer;

    public FriendRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
        pLayer = new FriendItemInHandLayer<>(this, pContext.getItemInHandRenderer());
        pBackLayer = new FriendItemOnBackLayer<>(this, pContext.getItemInHandRenderer());
        this.addLayer(pBackLayer);
        this.addLayer(pLayer);
    }

    public static void drawFrontFacingPlane(PoseStack pPoseStack, VertexConsumer vertexconsumer, float radius, float dist) {
        drawFrontFacingPlane(pPoseStack, vertexconsumer, radius, dist, 255);
    }

    public static void drawFrontFacingPlane(PoseStack pPoseStack, VertexConsumer vertexconsumer, float radius, float dist, int pAlpha) {
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        vertex(vertexconsumer, matrix4f, matrix3f, -radius, dist, -radius, 255, 255, 255, 0, 0, pAlpha);
        vertex(vertexconsumer, matrix4f, matrix3f, radius, dist, -radius, 255, 255, 255, 1, 0, pAlpha);
        vertex(vertexconsumer, matrix4f, matrix3f, radius, dist, radius, 255, 255, 255, 1, 1, pAlpha);
        vertex(vertexconsumer, matrix4f, matrix3f, -radius, dist, radius, 255, 255, 255, 0, 1, pAlpha);
    }

    public static void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV, int pAlpha) {
        pConsumer.vertex(pPose, pX, pY, pZ).color(pRed, pGreen, pBlue, pAlpha).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        float f = pEntityLiving.getSwimAmount(pPartialTicks);
        if (f > 0.0F) {
            super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
            float f4 = this.getModel().swimming && pEntityLiving.swimAnimState.isStarted() ? (pEntityLiving.getXRot() > 40 ? -pEntityLiving.getXRot() : 0) : 0;
            float f5 = Mth.lerp(f, 0.0F, f4);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f5));
        } else {
            super.setupRotations(pEntityLiving, pPoseStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        }
    }

    @Override
    public void render(@NotNull T pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
