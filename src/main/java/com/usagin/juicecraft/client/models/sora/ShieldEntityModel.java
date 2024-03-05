package com.usagin.juicecraft.client.models.sora;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.miscentities.SoraShieldEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ShieldEntityModel extends EntityModel<SoraShieldEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JuiceCraft.MODID, "sorashieldentity"), "main");
    private final ModelPart bb_main;

    public ShieldEntityModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(111, 28).addBox(-6.0F, -41.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(111, 14).addBox(-6.0F, -1.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(15, 128).addBox(-4.0F, -41.0F, 4.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(123, 38).addBox(-4.0F, -41.0F, -6.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(123, 24).addBox(-4.0F, -1.0F, 4.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(123, 10).addBox(-4.0F, -1.0F, -6.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(74, 60).addBox(-7.0F, -14.5F, -9.75F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(119, 64).addBox(-4.0F, -14.5F, -12.75F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(83, 128).addBox(-4.0F, -14.5F, -1.75F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(111, 52).addBox(4.0F, -20.25F, -12.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(72, 72).addBox(-4.0F, -20.25F, -15.0F, 8.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(37, 114).addBox(-7.0F, -20.25F, -12.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 116).addBox(-7.0F, -13.75F, -38.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(52, 116).addBox(4.0F, -13.75F, -38.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(74, 44).addBox(-4.0F, -13.75F, -41.0F, 8.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(75, 116).addBox(-7.0F, -13.75F, 30.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(15, 118).addBox(4.0F, -13.75F, 30.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 84).addBox(-4.0F, -13.75F, 27.0F, 8.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(90, 118).addBox(4.0F, -20.25F, 4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(113, 118).addBox(-7.0F, -20.25F, 4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(31, 86).addBox(-4.0F, -20.25F, 1.0F, 8.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(106, 128).addBox(-4.0F, -14.5F, 9.75F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(103, 70).addBox(-7.0F, -14.5F, 1.75F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(128, 118).addBox(-4.0F, -14.5F, -1.25F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(37, 30).addBox(-3.9216F, 28.1911F, -4.6911F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -21.0F, -1.75F, 0.5299F, 0.147F, -0.5299F));

        PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0555F, -1.2181F, -6.2181F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.75F, -37.0F, 13.25F, -1.8557F, 1.0228F, -1.2859F));

        PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 42).addBox(-14.0F, -26.25F, -1.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -29.0F, -1.75F, -2.1863F, 0.5236F, -0.9553F));

        PartDefinition cube_r10 = bb_main.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(37, 44).addBox(2.0F, -26.25F, -1.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -29.0F, -1.75F, -2.1863F, -0.5236F, 0.9553F));

        PartDefinition cube_r11 = bb_main.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 14).addBox(-5.9445F, -1.2181F, -6.2181F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.75F, -37.0F, 13.25F, -1.8557F, -1.0228F, 1.2859F));

        PartDefinition cube_r12 = bb_main.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 56).addBox(-8.0784F, 28.1911F, -4.6911F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -21.0F, -1.75F, 0.5299F, -0.147F, 0.5299F));

        PartDefinition cube_r13 = bb_main.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(62, 88).addBox(1.75F, -14.5F, -7.0F, 8.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(30, 124).addBox(9.75F, -14.5F, -4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(124, 90).addBox(-1.25F, -14.5F, -4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r14 = bb_main.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(31, 104).addBox(27.0F, -13.75F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(129, 128).addBox(30.0F, -13.75F, -7.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(88, 133).addBox(30.0F, -13.75F, 4.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r15 = bb_main.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(105, 42).addBox(1.0F, -20.25F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(111, 133).addBox(4.0F, -20.25F, -7.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(20, 134).addBox(4.0F, -20.25F, 4.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r16 = bb_main.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(37, 58).addBox(-3.9216F, 28.1911F, -7.3089F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -21.0F, 1.75F, -0.5299F, -0.147F, -0.5299F));

        PartDefinition cube_r17 = bb_main.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0555F, -1.2181F, -5.7819F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.75F, -37.0F, -13.25F, 1.8557F, -1.0228F, -1.2859F));

        PartDefinition cube_r18 = bb_main.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 70).addBox(-14.0F, -26.25F, -11.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -29.0F, 1.75F, 2.1863F, -0.5236F, -0.9553F));

        PartDefinition cube_r19 = bb_main.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(63, 136).addBox(-38.0F, -13.75F, 4.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(136, 123).addBox(-38.0F, -13.75F, -7.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(111, 0).addBox(-41.0F, -13.75F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 27.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r20 = bb_main.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(83, 138).addBox(-12.0F, -20.25F, 4.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(106, 138).addBox(-12.0F, -20.25F, -7.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(109, 80).addBox(-15.0F, -20.25F, -4.0F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r21 = bb_main.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(126, 54).addBox(-1.75F, -14.5F, -4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(68, 126).addBox(-12.75F, -14.5F, -4.0F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 100).addBox(-9.75F, -14.5F, -7.0F, 8.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r22 = bb_main.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(74, 14).addBox(-8.0784F, 28.1911F, -7.3089F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -21.0F, 1.75F, -0.5299F, 0.147F, 0.5299F));

        PartDefinition cube_r23 = bb_main.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(37, 16).addBox(-5.9445F, -1.2181F, -5.7819F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.75F, -37.0F, -13.25F, 1.8557F, 1.0228F, 1.2859F));

        PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(74, 28).addBox(2.0F, -26.25F, -11.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -29.0F, 1.75F, 2.1863F, 0.5236F, 0.9553F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(SoraShieldEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }
}
