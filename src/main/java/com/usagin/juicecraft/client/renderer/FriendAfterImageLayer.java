package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.client.renderer.entities.FriendRenderer;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import org.joml.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FriendAfterImageLayer<T extends Friend, M extends FriendEntityModel<T>> extends RenderLayer<T, M> {
    private final M model;
    private final FriendRenderer<T,M> renderer;
    public final int delay;

    public FriendAfterImageLayer(RenderLayerParent<T, M> pRenderer, int n) {
        super(pRenderer);
        this.model = pRenderer.getModel();
        this.renderer = (FriendRenderer<T,M>) pRenderer;
        this.delay = n;
    }
    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T friend, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (friend.shouldAfterImage()) {

            double xOld = friend.xOld;
            double yOld = friend.yOld;
            double zOld = friend.zOld;

            double xOldOld = friend.xOldOld;
            double yOldOld = friend.yOldOld;
            double zOldOld = friend.zOldOld;

            double xOldOldOld = friend.xOldOldOld ;
            double yOldOldOld  = friend.yOldOldOld ;
            double zOldOldOld  = friend.zOldOldOld ;

            float x = (float) (friend.getX() - friend.xOld);
            float y = (float) (friend.getY() - friend.yOld);
            float z = (float) (friend.getZ() - friend.zOld);
            float f8 = friend.walkAnimation.speed(pPartialTick-delay/300F);
            float f4 = friend.walkAnimation.position(pPartialTick-delay/300F);
            this.renderForPosition(pPoseStack,pBuffer,pPackedLight,friend,f4,f8,pPartialTick,pAgeInTicks,pNetHeadYaw,pHeadPitch,x,y,z,this.delay,0);

             friend.xOld=xOldOld;
             friend.yOld=yOldOld;
             friend.zOld=zOldOld;

            x = (float) (friend.getX() - friend.xOldOld);
            y = (float) (friend.getY() - friend.yOldOld);
            z = (float) (friend.getZ() - friend.zOldOld);
            f8 = friend.walkAnimation.speed(pPartialTick-delay*2/300F);
            f4 = friend.walkAnimation.position(pPartialTick-delay*2/300F);
            this.renderForPosition(pPoseStack,pBuffer,pPackedLight,friend,f4,f8,pPartialTick,pAgeInTicks,pNetHeadYaw,pHeadPitch,x,y,z,this.delay*2,3);

            friend.xOld=xOldOldOld;
            friend.yOld=yOldOldOld;
            friend.zOld=zOldOldOld;

            x = (float) (friend.getX() - friend.xOldOldOld);
            y = (float) (friend.getY() - friend.yOldOldOld);
            z = (float) (friend.getZ() - friend.zOldOldOld);
            f8 = friend.walkAnimation.speed(pPartialTick-delay*3/300F);
            f4 = friend.walkAnimation.position(pPartialTick-delay*3/300F);
            this.renderForPosition(pPoseStack,pBuffer,pPackedLight,friend,f4,f8,pPartialTick,pAgeInTicks,pNetHeadYaw,pHeadPitch,x,y,z,this.delay*3,6);

            friend.xOld=xOld;
            friend.yOld=yOld;
            friend.zOld=zOld;


        }
    }
    public void renderForPosition(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T friend, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, float x, float y , float z, int delay, int timeoffset){
        float n = 0.2F;
        //n=0.5F;
        M entitymodel = this.getParentModel();
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(this.renderer.getTextureLocation(friend), true));
        float f = Mth.rotLerp(pPartialTick, friend.yBodyRotO, friend.yBodyRot);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(- f));
        pPoseStack.translate(-x,y,z);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f));
        //pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f));
        entitymodel.offset=-delay;
        pPoseStack.scale(0.99F,0.99F,0.99F);
        entitymodel.setupAnim(friend,pLimbSwing,pLimbSwingAmount,pAgeInTicks,pNetHeadYaw,pHeadPitch);
        entitymodel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, n);
        entitymodel.offset=0;
        pPoseStack.popPose();
        entitymodel.setupAnim(friend,pLimbSwing,pLimbSwingAmount,pAgeInTicks,pNetHeadYaw,pHeadPitch);
    }

    protected M model() {
        return this.model;
    }

}
