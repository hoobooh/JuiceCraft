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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
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
        if (n >= 5 && n <= 15) {
            float lookAngleX = alte.getAlteLookAngle(Alte.ALTE_SPARKANGLEX) + (float) -Math.toRadians(30);
            float lookAngleY = alte.getAlteLookAngle(Alte.ALTE_SPARKANGLEY);

            float posX = (float) alte.getX();
            float posY = (float) alte.getEyeY() - 0.5F;
            float posZ = (float) alte.getZ();
            float originradius = 1;
            float targetradius = 2;
            float effectmagnitude = (n - 10) * (n - 10) * -0.1F + 2.5F;
            int effectcount = (int) ((15 - n) / 3) + 1;
            float eyeheight = alte.getEyeHeight() - 0.5F;

            float originX = posX + originradius * (float) Math.cos(lookAngleY);
            float originZ = posZ + originradius * (float) Math.sin(lookAngleY);
            float originY = posY + originradius * (float) Math.sin(lookAngleX);

            float targetX = posX + targetradius * (float) Math.cos(lookAngleY);
            float targetZ = posZ + targetradius * (float) Math.sin(lookAngleY);
            float targetY = posY + targetradius * (float) Math.sin(lookAngleX);
            Vec3 targetVec = new Vec3(targetX, targetY, targetZ);
            Vec3 originVec = new Vec3(originX, originY, originZ);
            Vec3 netVec = targetVec.subtract(originVec);
            float scale = 1;
            for (int i = 0; i < effectcount; i++) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.0F, eyeheight, 0.0F);

                //define initial vectors


                //normalizes the netvec for some reason
                float dist = (float) (netVec.length()) + i * 0.2F;
                netVec = netVec.normalize();
                float f5 = (float) Math.acos(netVec.y);
                float f6 = (float) Math.atan2(netVec.z, netVec.x);

            /*float offsetradius = i*0.2F;
            float offsetX = posX + offsetradius * (float) Math.cos(lookAngleY);
            float offsetZ = posZ + offsetradius * (float) Math.sin(lookAngleY);
            float offsetY = posY + offsetradius * (float) Math.sin(lookAngleX);

            Vec3 offsetVec = new Vec3(offsetX,offsetY,offsetZ);
            Vec3 tempVec = offsetVec.subtract(originVec);

            pPoseStack.translate(tempVec.x,tempVec.y,tempVec.z);*/


                //rotate posestack to match the look angle
                pPoseStack.mulPose(Axis.YP.rotationDegrees((((float) Math.PI / 2F) - f6) * (180F / (float) Math.PI)));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(f5 * (135F / (float) Math.PI)));

                VertexConsumer vertexconsumer = pBuffer.getBuffer(SPARK_RENDER_TYPE);
                PoseStack.Pose posestack$pose = pPoseStack.last();

                Matrix4f matrix4f = posestack$pose.pose();
                Matrix3f matrix3f = posestack$pose.normal();

                float sparkradius = 0.7F * effectmagnitude * scale;


                vertex(vertexconsumer, matrix4f, matrix3f, -sparkradius, dist, -sparkradius, 255, 255, 255, 0, 0);
                vertex(vertexconsumer, matrix4f, matrix3f, sparkradius, dist, -sparkradius, 255, 255, 255, 1, 0);
                vertex(vertexconsumer, matrix4f, matrix3f, sparkradius, dist, sparkradius, 255, 255, 255, 1, 1);
                vertex(vertexconsumer, matrix4f, matrix3f, -sparkradius, dist, sparkradius, 255, 255, 255, 0, 1);


                scale *= 0.8;
                pPoseStack.popPose();
            }

        }
    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(pPose, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static final ResourceLocation SPARK_BASE = new ResourceLocation(MODID, "textures/entities/alte/spark_0.png");
    private static final ResourceLocation SPARK_BG0 = new ResourceLocation(MODID, "textures/entities/alte/spark_1.png");
    private static final ResourceLocation SPARK_BG1 = new ResourceLocation(MODID, "textures/entities/alte/spark_2.png");
    private static final ResourceLocation SPARK_BG2 = new ResourceLocation(MODID, "textures/entities/alte/spark_3.png");
    private static final ResourceLocation SPARK_BG3 = new ResourceLocation(MODID, "textures/entities/alte/spark_4.png");
    private static final RenderType SPARK_RENDER_TYPE = RenderType.entityCutoutNoCull(SPARK_BASE);
    private static final RenderType SPARK_BG0_TYPE = RenderType.entityCutoutNoCull(SPARK_BG0);
    private static final RenderType SPARK_BG1_TYPE = RenderType.entityCutoutNoCull(SPARK_BG1);
    private static final RenderType SPARK_BG2_TYPE = RenderType.entityCutoutNoCull(SPARK_BG2);
    private static final RenderType SPARK_BG3_TYPE = RenderType.entityCutoutNoCull(SPARK_BG3);


    private static final Logger LOGGER = LogUtils.getLogger();
}