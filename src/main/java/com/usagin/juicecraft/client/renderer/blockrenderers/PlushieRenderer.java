package com.usagin.juicecraft.client.renderer.blockrenderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.usagin.juicecraft.blocks.plushies.PlushieBlock;
import com.usagin.juicecraft.blocks.plushies.AltePlushieBlockEntity;
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

public abstract class PlushieRenderer implements BlockEntityRenderer<AltePlushieBlockEntity> {
    public final ModelPart plushie;
    public static ModelLayerLocation PLUSHIE = new ModelLayerLocation(new ResourceLocation(MODID,"plushie"),"main");
    public PlushieRenderer(BlockEntityRendererProvider.Context pContext){
        this.plushie=pContext.bakeLayer(PLUSHIE);
    }
    //define layer through a non abstract method
    @Override
    public void render(AltePlushieBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Level level=pBlockEntity.getLevel();
        Material mat;
        if(pBlockEntity.getBlockState().getBlock() instanceof PlushieBlock block){
            mat=block.getMaterial();
        }else{return;}
        if(level!=null){
            Direction direction = pBlockEntity.getBlockState().getBedDirection(level,pBlockEntity.getBlockPos());
            this.renderPlushie(pPoseStack,pBuffer,this.plushie,direction,mat,pPackedLight,pPackedOverlay);
        }
        this.renderPlushie(pPoseStack,pBuffer,this.plushie,Direction.SOUTH,mat,pPackedLight,pPackedOverlay);
    }
    public void renderPlushie(PoseStack pPoseStack, MultiBufferSource pBufferSource, ModelPart pModelPart, Direction pDirection, Material pMaterial, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        pPoseStack.translate(0.5F, 0.5F, 0.5F);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F + pDirection.toYRot()));
        pPoseStack.translate(-0.5F, -0.5F, -0.5F);
        VertexConsumer vertexconsumer = pMaterial.buffer(pBufferSource, RenderType::entitySolid);
        pModelPart.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }
}
