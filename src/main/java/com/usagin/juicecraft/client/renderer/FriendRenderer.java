package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.friends.Alte;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public abstract class FriendRenderer<T extends Friend, M extends FriendEntityModel<T>> extends MobRenderer<T,M> {

    public FriendRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
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
        super.render(pEntity,pEntityYaw,pPartialTicks,pPoseStack,pBuffer,pPackedLight);
    }
}
