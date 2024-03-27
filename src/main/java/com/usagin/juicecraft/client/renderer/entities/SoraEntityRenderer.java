package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.models.sora.SoraEntityModel;
import com.usagin.juicecraft.client.renderer.FriendAfterImageLayer;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class SoraEntityRenderer extends FriendRenderer<Sora, SoraEntityModel> {
    private static final ResourceLocation SORA_NEUTRAL = new ResourceLocation(MODID, "textures/entities/sora/neutral.png");
    private static final ResourceLocation SORA_NARROW = new ResourceLocation(MODID, "textures/entities/sora/half.png");
    private static final ResourceLocation SORA_CLOSED = new ResourceLocation(MODID, "textures/entities/sora/closed.png");
    private static final ResourceLocation GLOW_OPEN = new ResourceLocation(MODID, "textures/entities/sora/glowopen.png");
    private static final ResourceLocation GLOW_NARROW = new ResourceLocation(MODID, "textures/entities/sora/glowhalf.png");
    private static final ResourceLocation SORA_ENERGYLAYER = new ResourceLocation(MODID, "textures/entities/sora/energy.png");
    private static final Logger LOGGER = LogUtils.getLogger();
    FriendEyeLayer<Sora, SoraEntityModel> energylayer;
    FriendEyeLayer<Sora, SoraEntityModel> openlayer;
    FriendEyeLayer<Sora, SoraEntityModel> narrowlayer;
    FriendAfterImageLayer<Sora, SoraEntityModel> afterimagelayer1;

    public SoraEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SoraEntityModel(pContext.bakeLayer(SoraEntityModel.LAYER_LOCATION)), 0.5f);
        energylayer = new FriendEyeLayer<>(this, SORA_ENERGYLAYER);
        openlayer = new FriendEyeLayer<>(this, GLOW_OPEN);
        narrowlayer = new FriendEyeLayer<>(this, GLOW_NARROW);
        this.afterimagelayer1 = new FriendAfterImageLayer<>(this, 200);
        energylayer.visible = true;
        this.addLayer(afterimagelayer1);
        this.addLayer(energylayer);
        this.addLayer(openlayer);
        this.addLayer(narrowlayer);

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Sora pEntity) {
        if (pEntity.patCounter != 0 || pEntity.getPose() == Pose.SLEEPING || pEntity.blinkCounter <= 6 || (pEntity.shakeAnimO > 0 && pEntity.shakeAnimO < 2) || pEntity.shakeAnimO > 3 || pEntity.deathAnimState.isStarted()) {
            this.openlayer.visible = false;
            this.narrowlayer.visible = false;
            return SORA_CLOSED;
        } else if ((pEntity.getTimeSinceLastPat() > 3600 && !pEntity.getIsWandering() && !pEntity.isAggressive()) || pEntity.blinkCounter <= 8) {
            this.openlayer.visible = false;
            this.narrowlayer.visible = true;
            return SORA_NARROW;
        }
        this.openlayer.visible = true;
        this.narrowlayer.visible = false;
        return SORA_NEUTRAL;
    }

    private static final ResourceLocation CHARGE_0 = new ResourceLocation(MODID, "textures/entities/sora/charge_ring_0.png");
    private static final ResourceLocation CHARGE_1 = new ResourceLocation(MODID, "textures/entities/sora/charge_ring_1.png");
    private static final ResourceLocation CHARGE_2 = new ResourceLocation(MODID, "textures/entities/sora/charge_ring_2.png");
    private static final RenderType CHARGE_0_TYPE = RenderType.entityTranslucentEmissive(CHARGE_0);
    private static final RenderType CHARGE_1_TYPE = RenderType.entityTranslucentEmissive(CHARGE_1);
    private static final RenderType CHARGE_2_TYPE = RenderType.entityTranslucentEmissive(CHARGE_2);

    @Override
    public void render(@NotNull Sora pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        if (pEntity.isUsingHyper()) {
            int n = pEntity.getSyncInt(Sora.CHARGECOUNTER);
            if (n <= 55 && n >= 45) {
                int x = pEntity.tickCount % 6;

                pPoseStack.pushPose();
                normaliseToFrontFacing(pPoseStack, pEntity);
                drawFrontFacingPlane(pPoseStack, pBuffer.getBuffer(CHARGE_0_TYPE), 2F - x / 2.5F, 1);
                pPoseStack.popPose();
            }
        }
    }

}


