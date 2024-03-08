package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FriendItemInHandLayer<T extends Friend, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public FriendItemInHandLayer(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = pItemInHandRenderer;
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = pLivingEntity.getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemstack = flag ? pLivingEntity.getOffhandItem() : pLivingEntity.getMainHandItem();
        ItemStack itemstack1 = flag ? pLivingEntity.getMainHandItem() : pLivingEntity.getOffhandItem();
        Logger LOGGER = LogUtils.getLogger();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            pPoseStack.pushPose();
            if ((!pLivingEntity.getInSittingPose() && (pLivingEntity.aggroCounter > 0 || pLivingEntity.getAttackCounter() > 0) || pLivingEntity.animatestandingtimer > 0 || pLivingEntity.isHoldingThrowable() )||pLivingEntity.forceShowWeapon()) {
                if (pLivingEntity.shouldShowWeapon()) {
                    this.renderArmWithItem(pLivingEntity, itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, pPoseStack, pBuffer, pPackedLight);
                    this.renderArmWithItem(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, pPoseStack, pBuffer, pPackedLight);
                }
            }
            pPoseStack.popPose();
        }
    }

    protected void renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, @NotNull HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            this.getParentModel().translateToHand(pArm, pPoseStack);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean flag = pArm == HumanoidArm.LEFT;
            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, flag, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }
    }
}
