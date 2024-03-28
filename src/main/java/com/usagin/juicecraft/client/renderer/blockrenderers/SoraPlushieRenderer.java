package com.usagin.juicecraft.client.renderer.blockrenderers;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class SoraPlushieRenderer extends PlushieRenderer{
    public static ModelLayerLocation SORA_PLUSHIE = new ModelLayerLocation(new ResourceLocation(MODID,"sora_plushie"),"main");
    @Override
    public void setPlushie(BlockEntityRendererProvider.Context pContext) {
        this.plushie=pContext.bakeLayer(SORA_PLUSHIE);
    }

    public SoraPlushieRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }
    public static LayerDefinition createLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(18, 22).addBox(-1.0F, -4.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
                .texOffs(15, 16).addBox(-1.5F, -6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F))
                .texOffs(23, 9).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F))
                .texOffs(33, 31).addBox(-1.0F, -3.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(13, 12).addBox(-1.5F, -6.2F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-1.71F, -4.7F, 0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(32, 33).addBox(-1.71F, -4.7F, -1.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(34, 15).addBox(-1.92F, -4.9F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
                .texOffs(5, 34).addBox(-1.92F, -4.9F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hair1_r1 = bb_main.addOrReplaceChild("hair1_r1", CubeListBuilder.create().texOffs(19, 5).addBox(-1.0F, 1.05F, -1.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(11, 19).addBox(-1.0F, 0.8F, -1.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(30, 18).addBox(-1.0F, -0.2F, -2.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(30, 29).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(26, 25).addBox(-1.0F, -0.3F, -2.2F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(19, 0).addBox(-0.5F, -0.3F, -1.3F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition hair1_r2 = bb_main.addOrReplaceChild("hair1_r2", CubeListBuilder.create().texOffs(4, 31).addBox(-0.75F, 1.95F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, 1.0F, 0.2182F, 0.0F, -0.3054F));

        PartDefinition hair1_r3 = bb_main.addOrReplaceChild("hair1_r3", CubeListBuilder.create().texOffs(24, 31).addBox(-0.75F, 1.95F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, -1.0F, -0.2182F, 0.0F, -0.3054F));

        PartDefinition hair1_r4 = bb_main.addOrReplaceChild("hair1_r4", CubeListBuilder.create().texOffs(20, 31).addBox(-1.3F, -1.95F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.75F, -6.0F, -1.0F, 2.4658F, -0.4847F, 0.8312F));

        PartDefinition hair1_r5 = bb_main.addOrReplaceChild("hair1_r5", CubeListBuilder.create().texOffs(30, 9).addBox(-0.8F, -1.2F, -0.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.75F, -5.75F, -1.0F, 2.372F, 0.2794F, 1.635F));

        PartDefinition hair1_r6 = bb_main.addOrReplaceChild("hair1_r6", CubeListBuilder.create().texOffs(31, 24).addBox(-1.3F, -1.95F, -0.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.75F, -6.0F, 1.0F, -2.4658F, 0.4847F, 0.8312F));

        PartDefinition hair1_r7 = bb_main.addOrReplaceChild("hair1_r7", CubeListBuilder.create().texOffs(0, 19).addBox(-0.75F, 1.95F, -0.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, -1.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition hair1_r8 = bb_main.addOrReplaceChild("hair1_r8", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 0.5F, -0.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.2559F, -0.056F, 0.3855F));

        PartDefinition hair1_r9 = bb_main.addOrReplaceChild("hair1_r9", CubeListBuilder.create().texOffs(18, 27).addBox(-1.5F, 0.5F, -0.9F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.2559F, 0.056F, 0.3855F));

        PartDefinition rightarm_r1 = bb_main.addOrReplaceChild("rightarm_r1", CubeListBuilder.create().texOffs(22, 13).addBox(-0.75F, 0.69F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(16, 22).addBox(-0.25F, 0.69F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(31, 4).addBox(-0.75F, 0.69F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(31, 16).addBox(-0.25F, 0.69F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(16, 31).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition rightarm_r2 = bb_main.addOrReplaceChild("rightarm_r2", CubeListBuilder.create().texOffs(9, 33).addBox(-0.25F, 0.69F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(33, 11).addBox(-0.75F, 0.69F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(13, 33).addBox(-0.25F, 0.69F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(26, 33).addBox(-0.75F, 0.69F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(31, 13).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition hair1_r10 = bb_main.addOrReplaceChild("hair1_r10", CubeListBuilder.create().texOffs(5, 19).addBox(-0.5F, -0.1F, -0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.75F, -5.75F, 1.0F, 0.5672F, 0.0F, -0.2618F));

        PartDefinition hair1_r11 = bb_main.addOrReplaceChild("hair1_r11", CubeListBuilder.create().texOffs(26, 5).addBox(-0.5F, -0.3F, -0.2F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.75F, -5.75F, 1.0F, 0.2618F, 0.0F, -0.2618F));

        PartDefinition hair1_r12 = bb_main.addOrReplaceChild("hair1_r12", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, 0.5F, -0.1F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.2559F, -0.056F, 0.211F));

        PartDefinition hair1_r13 = bb_main.addOrReplaceChild("hair1_r13", CubeListBuilder.create().texOffs(6, 27).addBox(-1.5F, 0.5F, -0.9F, 2.0F, 4.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.2559F, 0.056F, 0.211F));

        PartDefinition hair1_r14 = bb_main.addOrReplaceChild("hair1_r14", CubeListBuilder.create().texOffs(12, 27).addBox(-0.5F, -0.1F, -0.9F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.75F, -5.75F, -1.0F, -0.5672F, 0.0F, -0.2618F));

        PartDefinition hair1_r15 = bb_main.addOrReplaceChild("hair1_r15", CubeListBuilder.create().texOffs(26, 21).addBox(-0.5F, -0.3F, -0.8F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.75F, -5.75F, -1.0F, -0.2618F, 0.0F, -0.2618F));

        PartDefinition hair1_r16 = bb_main.addOrReplaceChild("hair1_r16", CubeListBuilder.create().texOffs(12, 30).addBox(-0.8F, -1.2F, -0.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.75F, -5.75F, 1.0F, -2.372F, -0.2794F, 1.635F));

        PartDefinition hair1_r17 = bb_main.addOrReplaceChild("hair1_r17", CubeListBuilder.create().texOffs(22, 27).addBox(-1.0F, -0.2F, -0.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(0, 31).addBox(-1.0F, -0.2F, -0.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition hair1_r18 = bb_main.addOrReplaceChild("hair1_r18", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.3F, -1.3F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, 1.0F, 0.0873F, 0.0F, -0.0436F));

        PartDefinition hair1_r19 = bb_main.addOrReplaceChild("hair1_r19", CubeListBuilder.create().texOffs(25, 13).addBox(0.0F, -0.3F, -0.7F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, -1.0F, -0.0873F, 0.0F, -0.0436F));

        PartDefinition hair1_r20 = bb_main.addOrReplaceChild("hair1_r20", CubeListBuilder.create().texOffs(8, 22).addBox(-1.5F, -0.3F, -0.7F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 12).addBox(-1.9F, 0.5F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.4F, -3.5F, 6.0F, 5.0F, 7.0F, new CubeDeformation(-1.85F)), PartPose.offsetAndRotation(1.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(29, 0).addBox(0.0F, 0.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, -0.1309F, 0.0F, 1.5708F));

        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(26, 29).addBox(0.0F, 0.0F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, 0.1309F, 0.0F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

}
