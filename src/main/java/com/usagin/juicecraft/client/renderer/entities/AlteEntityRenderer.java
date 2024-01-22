package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.usagin.juicecraft.client.models.alte.AlteEntityModel;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.client.renderer.FriendItemInHandLayer;
import com.usagin.juicecraft.client.renderer.FriendItemOnBackLayer;
import com.usagin.juicecraft.client.renderer.FriendRenderer;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class AlteEntityRenderer extends FriendRenderer<Alte, AlteEntityModel> {
    private static final ResourceLocation ALTE_NEUTRAL = new ResourceLocation(MODID, "textures/entities/alte/neutral.png");
    private static final ResourceLocation ALTE_NARROW = new ResourceLocation(MODID, "textures/entities/alte/half.png");
    private static final ResourceLocation ALTE_CLOSED = new ResourceLocation(MODID, "textures/entities/alte/closed.png");
    private static final ResourceLocation ALTE_WINK = new ResourceLocation(MODID, "textures/entities/alte/wink.png");
    private static final ResourceLocation GLOW_OPEN = new ResourceLocation(MODID, "textures/entities/alte/glowopen.png");
    private static final ResourceLocation GLOW_NARROW = new ResourceLocation(MODID, "textures/entities/alte/glownarrow.png");
    private static final ResourceLocation GLOW_WINK = new ResourceLocation(MODID, "textures/entities/alte/glowwink.png");
    private static final ResourceLocation ALTE_ENERGYLAYER = new ResourceLocation(MODID, "textures/entities/alte/lightlayerfinal.png");


    FriendEyeLayer<Alte, AlteEntityModel> energylayer;
    FriendEyeLayer<Alte, AlteEntityModel> openlayer;
    FriendEyeLayer<Alte, AlteEntityModel> narrowlayer;
    FriendEyeLayer<Alte, AlteEntityModel> winklayer;
    FriendItemInHandLayer<Alte, AlteEntityModel> pLayer;
    FriendItemOnBackLayer<Alte, AlteEntityModel> pBackLayer;

    public AlteEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AlteEntityModel(pContext.bakeLayer(AlteEntityModel.LAYER_LOCATION)), 0.5f);
        energylayer = new FriendEyeLayer<>(this, ALTE_ENERGYLAYER);
        openlayer = new FriendEyeLayer<>(this, GLOW_OPEN);
        narrowlayer = new FriendEyeLayer<>(this, GLOW_NARROW);
        winklayer = new FriendEyeLayer<>(this, GLOW_WINK);
        pLayer = new FriendItemInHandLayer<>(this, pContext.getItemInHandRenderer());
        pBackLayer = new FriendItemOnBackLayer<>(this, pContext.getItemInHandRenderer());
        energylayer.visible = true;
        this.addLayer(energylayer);
        this.addLayer(pBackLayer);
        this.addLayer(pLayer);
        this.addLayer(openlayer);
        this.addLayer(narrowlayer);
        this.addLayer(winklayer);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Alte pEntity) {
        if (pEntity.getPose() == Pose.SLEEPING || pEntity.blinkCounter <= 6 || (pEntity.shakeAnimO > 0 && pEntity.shakeAnimO < 2) || pEntity.shakeAnimO > 3) {
            this.openlayer.visible = false;
            this.narrowlayer.visible = false;
            this.winklayer.visible = false;
            return ALTE_CLOSED;
        } else if (pEntity.patCounter != 0 || (pEntity.getTimeSinceLastPat() > 3600 && !pEntity.getIsWandering() && !pEntity.isAggressive())) {
            this.openlayer.visible = false;
            this.narrowlayer.visible = false;
            this.winklayer.visible = true;
            return ALTE_WINK;
        } else if (pEntity.blinkCounter <= 8) {
            this.openlayer.visible = false;
            this.narrowlayer.visible = true;
            this.winklayer.visible = false;
            return ALTE_NARROW;
        }
        this.openlayer.visible = true;
        this.narrowlayer.visible = false;
        this.winklayer.visible = false;
        return ALTE_NEUTRAL;
    }

    @Override
    public void render(@NotNull Alte alte, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(alte, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

        int n = alte.getAlteAnimCounter(Alte.ALTE_SPARKCOUNTER);
        {
        float lookAngleX = (float) alte.getAlteLookAngle(Alte.ALTE_SPARKANGLEX);
        float lookAngleY = (float) alte.getAlteLookAngle(Alte.ALTE_SPARKANGLEY);
        LOGGER.info(lookAngleY +"");
        }
        //if (n >= 5 && n <= 15) {
            float lookAngleX = (float) alte.getAlteLookAngle(Alte.ALTE_SPARKANGLEX);
            float lookAngleY = (float) alte.getAlteLookAngle(Alte.ALTE_SPARKANGLEY);
            LOGGER.info(lookAngleY +"");
            float posX = (float) alte.getX();
            float posY = (float) alte.getEyeY();
            float posZ = (float) alte.getZ();
            float originradius = 1;
            float targetradius = 4;
            float effectmagnitude = (n - 10) * (n - 10) * -0.1F + 2.5F;
            float eyeheight = alte.getEyeHeight();

            float originX = posX + originradius * (float) Math.cos(lookAngleY);
            float originZ = posZ + originradius * (float) Math.sin(lookAngleY);
            float originY = posY + originradius * (float) Math.sin(lookAngleX);

            float targetX = posX + targetradius * (float) Math.cos(lookAngleY);
            float targetZ = posZ + targetradius * (float) Math.sin(lookAngleY);
            float targetY = posY + targetradius * (float) Math.sin(lookAngleX);

            pPoseStack.pushPose();
            pPoseStack.translate(0.0F, eyeheight, 0.0F);

            //define initial vectors
            Vec3 targetVec = new Vec3(targetX,targetY,targetZ);
            Vec3 originVec = new Vec3(originX,originY,originZ);
            Vec3 netVec = targetVec.subtract(originVec);

            //normalizes the netvec for some reason
            float f4 = (float) (netVec.length() + 1.0D);
            netVec = netVec.normalize();
            float f5 = (float) Math.acos(netVec.y);
            float f6 = (float) Math.atan2(netVec.z, netVec.x);

            //rotate posestack to match the look angle
            pPoseStack.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f5 * (180F / (float) Math.PI)));

            float f7 = 0.05F * -1.5F;
            float f8 = effectmagnitude * effectmagnitude;
            int j = 64 + (int) (f8 * 191.0F);
            int k = 32 + (int) (f8 * 191.0F);
            int l = 128 - (int) (f8 * 64.0F);
            float f11 = Mth.cos(f7 + 2.3561945F) * 0.282F;
            float f12 = Mth.sin(f7 + 2.3561945F) * 0.282F;
            float f13 = Mth.cos(f7 + ((float) Math.PI / 4F)) * 0.282F;
            float f14 = Mth.sin(f7 + ((float) Math.PI / 4F)) * 0.282F;
            float f15 = Mth.cos(f7 + 3.926991F) * 0.282F;
            float f16 = Mth.sin(f7 + 3.926991F) * 0.282F;
            float f17 = Mth.cos(f7 + 5.4977875F) * 0.282F;
            float f18 = Mth.sin(f7 + 5.4977875F) * 0.282F;
            float f19 = Mth.cos(f7 + (float) Math.PI) * 0.2F;
            float f20 = Mth.sin(f7 + (float) Math.PI) * 0.2F;
            float f21 = Mth.cos(f7 + 0.0F) * 0.2F;
            float f22 = Mth.sin(f7 + 0.0F) * 0.2F;
            float f23 = Mth.cos(f7 + ((float) Math.PI / 2F)) * 0.2F;
            float f24 = Mth.sin(f7 + ((float) Math.PI / 2F)) * 0.2F;
            float f25 = Mth.cos(f7 + ((float) Math.PI * 1.5F)) * 0.2F;
            float f26 = Mth.sin(f7 + ((float) Math.PI * 1.5F)) * 0.2F;
            float f27 = 0.0F;
            float f28 = 0.4999F;
            float f29 = -1.0F;
            float f30 = f4 * 2.5F + f29;
            VertexConsumer vertexconsumer = pBuffer.getBuffer(BEAM_RENDER_TYPE);
            PoseStack.Pose posestack$pose = pPoseStack.last();
            Matrix4f matrix4f = posestack$pose.pose();
            Matrix3f matrix3f = posestack$pose.normal();
            vertex(vertexconsumer, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
            vertex(vertexconsumer, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
            vertex(vertexconsumer, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
            vertex(vertexconsumer, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
            vertex(vertexconsumer, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
            float f31 = 0.0F;
            if (alte.tickCount % 2 == 0) {
                f31 = 0.5F;
            }

            vertex(vertexconsumer, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
            vertex(vertexconsumer, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
            vertex(vertexconsumer, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
            vertex(vertexconsumer, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
            pPoseStack.popPose();
        //}
    }
    private static void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(pPose, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }
    private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("textures/entity/guardian_beam.png");
    private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

    private static final Logger LOGGER = LogUtils.getLogger();
}

