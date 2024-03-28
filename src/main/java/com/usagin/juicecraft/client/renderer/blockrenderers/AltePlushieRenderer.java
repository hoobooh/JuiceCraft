package com.usagin.juicecraft.client.renderer.blockrenderers;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class AltePlushieRenderer extends PlushieRenderer{
    public static ModelLayerLocation ALTE_PLUSHIE = new ModelLayerLocation(new ResourceLocation(MODID,"alte_plushie"),"main");
    @Override
    public void setPlushie(BlockEntityRendererProvider.Context pContext) {
        this.plushie=pContext.bakeLayer(ALTE_PLUSHIE);
    }

    public AltePlushieRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public static LayerDefinition createLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -4.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
                .texOffs(20, 0).addBox(-1.5F, -6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(8, 24).addBox(-1.0F, -2.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.05F))
                .texOffs(19, 13).addBox(-1.0F, -3.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(20, 0).addBox(-1.5F, -6.2F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 13).addBox(-1.71F, -4.7F, 0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(23, 13).addBox(-1.71F, -4.7F, -1.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(27, 9).addBox(-1.92F, -4.9F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
                .texOffs(27, 9).addBox(-1.92F, -4.9F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rightarm_r1 = bb_main.addOrReplaceChild("rightarm_r1", CubeListBuilder.create().texOffs(28, 15).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(-0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 15).addBox(-0.1F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 15).addBox(0.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(-1.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(-0.5F, 0.0F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(0.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-1.0F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(0.0F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-1.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-1.0F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 15).addBox(-0.5F, 0.0F, 0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(19, 24).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition rightarm_r2 = bb_main.addOrReplaceChild("rightarm_r2", CubeListBuilder.create().texOffs(28, 15).addBox(-0.5F, 0.0F, -0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 15).addBox(0.0F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(0.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(-0.1F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 15).addBox(-0.9F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 15).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(28, 15).addBox(-0.5F, 0.0F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(0.0F, 0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-1.0F, 0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-0.5F, 0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-0.5F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(0.0F, 0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-1.0F, 0.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(0.0F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 18).addBox(-1.0F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(14, 13).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition hair1_r1 = bb_main.addOrReplaceChild("hair1_r1", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -0.1F, -1.1F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition hair1_r2 = bb_main.addOrReplaceChild("hair1_r2", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -0.3F, -1.2F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition hair1_r3 = bb_main.addOrReplaceChild("hair1_r3", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, 0.5F, -1.1F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition hair1_r4 = bb_main.addOrReplaceChild("hair1_r4", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, 0.5F, -0.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition hair1_r5 = bb_main.addOrReplaceChild("hair1_r5", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -0.1F, -0.9F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition hair1_r6 = bb_main.addOrReplaceChild("hair1_r6", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -0.3F, -0.8F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition hair1_r7 = bb_main.addOrReplaceChild("hair1_r7", CubeListBuilder.create().texOffs(23, 27).addBox(-0.8F, -1.2F, -0.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 1.0F, -2.3369F, -0.0934F, 1.4472F));

        PartDefinition hair1_r8 = bb_main.addOrReplaceChild("hair1_r8", CubeListBuilder.create().texOffs(23, 27).addBox(-1.0F, -0.2F, -0.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(23, 27).addBox(-1.0F, -0.2F, -0.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 1.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition hair1_r9 = bb_main.addOrReplaceChild("hair1_r9", CubeListBuilder.create().texOffs(23, 27).addBox(-1.0F, -0.2F, -2.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(23, 27).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(22, 26).addBox(-1.0F, -0.3F, -2.2F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(22, 26).addBox(-0.5F, -0.3F, -1.3F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition hair1_r10 = bb_main.addOrReplaceChild("hair1_r10", CubeListBuilder.create().texOffs(22, 26).addBox(0.0F, -0.3F, -1.3F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, 1.0F, 0.0873F, 0.0F, -0.0436F));

        PartDefinition hair1_r11 = bb_main.addOrReplaceChild("hair1_r11", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -0.3F, -0.7F, 1.0F, 4.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -6.0F, -1.0F, -0.0873F, 0.0F, -0.0436F));

        PartDefinition hair1_r12 = bb_main.addOrReplaceChild("hair1_r12", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -0.3F, -0.7F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -6.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -0.5F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 16).addBox(-1.9F, 0.5F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.4F, -3.5F, 6.0F, 5.0F, 7.0F, new CubeDeformation(-1.85F)), PartPose.offsetAndRotation(1.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, -0.1309F, 0.0F, 1.5708F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, 0.0F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -1.0F, 0.0F, 0.1309F, 0.0F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

}
