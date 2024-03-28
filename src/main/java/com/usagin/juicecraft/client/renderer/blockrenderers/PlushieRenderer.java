package com.usagin.juicecraft.client.renderer.blockrenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.usagin.juicecraft.blocks.plushies.PlushieBlock;
import com.usagin.juicecraft.blocks.plushies.AltePlushieBlockEntity;
import com.usagin.juicecraft.blocks.plushies.PlushieBlockEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;


import static com.usagin.juicecraft.JuiceCraft.MODID;
import static com.usagin.juicecraft.particles.AlteLightningParticle.LOGGER;

public abstract class PlushieRenderer implements BlockEntityRenderer<PlushieBlockEntity> {

    public ModelPart plushie;
    public abstract void setPlushie(BlockEntityRendererProvider.Context pContext);
    public PlushieRenderer(BlockEntityRendererProvider.Context pContext){
        this.setPlushie(pContext);
    }
    //define layer through a non abstract method
    @Override
    public void render(PlushieBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Level level=pBlockEntity.getLevel();
        ResourceLocation mat;
        if(pBlockEntity.getBlockState().getBlock() instanceof PlushieBlock block){
            mat=block.getTexture();
        }else{return;}
        if(level!=null){
            int rot = (int)(pBlockEntity.getBlockState().getValue(PlushieBlock.ROTATION)*22.5F);
            this.renderPlushie(pPoseStack,pBuffer,this.plushie,rot,mat,pPackedLight,pPackedOverlay);
        }else{
        this.renderPlushie(pPoseStack,pBuffer,this.plushie,0,mat,pPackedLight,pPackedOverlay);}
    }
    public void renderPlushie(PoseStack pPoseStack, MultiBufferSource pBufferSource, ModelPart pModelPart, int angle, ResourceLocation texture, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0, 0.5F);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        pPoseStack.translate(0, -1.5, 0);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(111 + angle));

        VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.entitySolid(texture));

        pModelPart.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }


}
