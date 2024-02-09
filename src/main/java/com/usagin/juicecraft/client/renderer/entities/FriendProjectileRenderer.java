package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.usagin.juicecraft.projectiles.FriendProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FriendProjectileRenderer<T extends FriendProjectile> extends EntityRenderer<T> {
    private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public FriendProjectileRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, 1.0F, false);
    }

    public FriendProjectileRenderer(EntityRendererProvider.Context pContext, float pScale, boolean pFullBright) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
        this.scale = pScale;
        this.fullBright = pFullBright;
    }

    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(pEntity, pPos);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        //super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        if (pEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25D)) {
            pPoseStack.pushPose();
            pPoseStack.scale(this.scale, this.scale, this.scale);
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            FriendRenderer.drawFrontFacingPlane(pPoseStack, pBuffer.getBuffer(this.getRenderType(pEntity)), pEntity.getBbHeight(), 0);
            pPoseStack.popPose();
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(T pEntity) {
        return pEntity.getTexture();
    }

    public RenderType getRenderType(T proj) {
        return proj.getRenderType();
    }
}
