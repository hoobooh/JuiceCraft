package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FriendItemOnBackLayer<T extends Friend, M extends FriendEntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public FriendItemOnBackLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = pLivingEntity.getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemstack = flag ? pLivingEntity.getOffhandItem() : pLivingEntity.getMainHandItem();
        ItemStack itemstack1 = flag ? pLivingEntity.getMainHandItem() : pLivingEntity.getOffhandItem();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            pPoseStack.pushPose();
            if ((pLivingEntity.aggroCounter == 0 && pLivingEntity.getAttackCounter() == 0 && pLivingEntity.animatestandingtimer == 0 && !pLivingEntity.isHoldingThrowable())&&!pLivingEntity.forceShowWeapon()) {
                if (pLivingEntity.shouldShowWeapon()) {
                    this.renderBackWithItem(pLivingEntity, itemstack1, pPoseStack, pBuffer, pPackedLight);
                }
            }
            pPoseStack.popPose();
        }
    }

    protected void renderBackWithItem(@NotNull LivingEntity pLivingEntity, ItemStack pItemStack, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            this.getParentModel().translateToBack(pPoseStack, pItemStack);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(190.0F));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(260.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }
    }
}
