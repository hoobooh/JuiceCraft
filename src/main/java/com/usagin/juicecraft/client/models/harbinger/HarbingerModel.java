package com.usagin.juicecraft.client.models.harbinger;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.usagin.juicecraft.client.animation.HarbingerAnimation;
import com.usagin.juicecraft.enemies.Harbinger;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HarbingerModel<T extends Harbinger> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "harbinger"), "main");
    private final ModelPart root;

    public record ModelParts(ModelPart head, ModelPart rightarm, ModelPart leftarm) {
    }

    public ModelParts parts;

    public HarbingerModel(ModelPart root) {
        this.root = root.getChild("root");
        ModelPart chest = this.root.getChild("hip").getChild("waist").getChild("chest");
        ModelPart head = chest.getChild("neck").getChild("head");
        ModelPart leftarm = chest.getChild("leftarm");
        ModelPart rightarm = chest.getChild("rightarm");
        this.parts = new ModelParts(head, rightarm, leftarm);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition waist = hip.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(112, 15).addBox(-4.0F, -2.5F, -3.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.3F)).texOffs(80, 115).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.0F, 0.0F));

        PartDefinition cube_r1 = waist.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(144, 38).addBox(-5.0F, 1.85F, -2.5F, 10.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 5.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r2 = waist.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(148, 104).addBox(-5.0F, 1.85F, 1.5F, 10.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -5.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r3 = waist.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(53, 149).addBox(0.5F, 1.7F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition cube_r4 = waist.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(130, 150).addBox(-1.5F, 1.7F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition chest = waist.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 82).addBox(-7.0F, -7.0F, -5.0F, 14.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(41, 95).addBox(-6.0F, -1.0F, -4.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition cloakupper = chest.addOrReplaceChild("cloakupper", CubeListBuilder.create().texOffs(82, 0).addBox(-6.0F, -1.0F, -5.5F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(17, 68).addBox(-5.75F, 1.0F, -6.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 75).addBox(2.5F, 1.0F, -6.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition cloakmid = cloakupper.addOrReplaceChild("cloakmid", CubeListBuilder.create().texOffs(123, 84).addBox(-6.0F, 1.0F, -0.5F, 12.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(39, 72).addBox(-7.0F, 5.0F, -0.5F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cloakmid2 = cloakmid.addOrReplaceChild("cloakmid2", CubeListBuilder.create().texOffs(0, 99).addBox(-9.0F, 1.0F, -0.5F, 18.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(94, 93).addBox(-11.0F, 5.0F, -0.5F, 22.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cloaklower = cloakmid2.addOrReplaceChild("cloaklower", CubeListBuilder.create().texOffs(97, 37).addBox(-12.0F, 1.0F, -0.5F, 21.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(97, 51).addBox(-6.0F, 5.0F, -0.5F, 18.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 153).addBox(-13.0F, 5.0F, -0.5F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cloaklower2 = cloaklower.addOrReplaceChild("cloaklower2", CubeListBuilder.create().texOffs(76, 87).addBox(-12.0F, 3.0F, -0.5F, 21.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 31).addBox(6.0F, 1.0F, -0.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(138, 53).addBox(-5.0F, 1.0F, -0.5F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(150, 0).addBox(-15.0F, 1.0F, -0.5F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(97, 44).addBox(-6.0F, 5.0F, -0.5F, 19.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(150, 109).addBox(-14.0F, 5.0F, -0.5F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cloakend = cloaklower2.addOrReplaceChild("cloakend", CubeListBuilder.create().texOffs(104, 107).addBox(8.0F, 3.0F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(6.0F, 1.0F, -0.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(68, 64).addBox(-5.0F, 1.0F, -0.5F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(32, 121).addBox(-15.0F, 1.0F, -0.5F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(66, 30).addBox(10.0F, 5.0F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(152, 11).addBox(-16.0F, 3.0F, -0.5F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(53, 106).addBox(-1.0F, 6.0F, -0.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(150, 5).addBox(-4.0F, 3.0F, -0.5F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leftarm = chest.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(63, 137).addBox(0.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(39, 45).addBox(-0.25F, 2.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(6.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r5 = leftarm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(133, 110).addBox(0.5F, -1.2F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r6 = leftarm.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(133, 141).addBox(0.25F, -1.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition cube_r7 = leftarm.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(135, 11).addBox(0.75F, 1.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition leftlowerarm = leftarm.addOrReplaceChild("leftlowerarm", CubeListBuilder.create().texOffs(17, 60).addBox(-1.5F, -0.1F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F)).texOffs(19, 145).addBox(-1.5F, 3.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(123, 124).addBox(-2.5F, 2.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(-0.5F)), PartPose.offset(1.5F, 9.0F, 0.0F));

        PartDefinition cube_r8 = leftlowerarm.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(81, 41).addBox(4.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(1.5F, 4.0F, 0.0F, 0.0F, 0.0F, 1.789F));

        PartDefinition cube_r9 = leftlowerarm.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(43, 0).addBox(3.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(1.5F, 4.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r10 = leftlowerarm.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(104, 157).mirror().addBox(1.75F, -4.0F, -2.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(1.5F, 4.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition rightarm = chest.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(100, 138).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(39, 57).addBox(-4.75F, 2.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-6.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r11 = rightarm.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(136, 45).addBox(-5.5F, -1.2F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r12 = rightarm.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(142, 29).addBox(-5.25F, -1.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r13 = rightarm.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(138, 133).addBox(-5.75F, 1.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition rightlowerarm = rightarm.addOrReplaceChild("rightlowerarm", CubeListBuilder.create().texOffs(68, 110).addBox(-2.5F, -0.1F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(-0.1F)).texOffs(117, 146).addBox(-3.5F, 3.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(64, 128).addBox(-4.5F, 2.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(-0.5F)), PartPose.offset(-1.5F, 9.0F, 0.0F));

        PartDefinition cube_r14 = rightlowerarm.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(87, 96).addBox(-5.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(-1.5F, 4.0F, 0.0F, 0.0F, 0.0F, -1.789F));

        PartDefinition cube_r15 = rightlowerarm.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(94, 152).addBox(-2.75F, -4.0F, -2.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-1.5F, 4.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition cube_r16 = rightlowerarm.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(43, 15).addBox(-4.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-1.5F, 4.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition sword = rightlowerarm.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(116, 23).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(-0.1F)), PartPose.offset(-0.5F, 7.0F, -6.0F));

        PartDefinition upperhandle = sword.addOrReplaceChild("upperhandle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition handle_r1 = upperhandle.addOrReplaceChild("handle_r1", CubeListBuilder.create().texOffs(0, 143).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition blade = upperhandle.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, -40.0F, 1.0F, 4.0F, 40.0F, new CubeDeformation(0.0F)).texOffs(21, 0).addBox(-1.5F, -2.0F, -28.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(68, 151).addBox(-0.5F, -1.0F, -46.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(76, 64).addBox(-0.5F, -4.0F, -34.0F, 1.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(53, 109).addBox(-0.5F, -6.0F, -30.0F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(53, 72).addBox(-0.5F, 2.0F, -34.0F, 1.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(38, 106).addBox(-0.5F, 4.0F, -30.0F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(152, 89).addBox(-0.5F, -6.0F, -6.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(152, 79).addBox(-0.5F, 2.0F, -6.0F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(9, 20).addBox(-0.5F, 2.0F, -8.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(9, 0).addBox(-0.5F, -4.0F, -8.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, -16.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition guard1 = blade.addOrReplaceChild("guard1", CubeListBuilder.create().texOffs(60, 0).addBox(-1.0F, -4.0F, -14.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 106).addBox(-1.0F, -6.0F, -18.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(104, 103).addBox(-1.0F, -6.0F, -42.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(87, 100).addBox(-1.0F, -4.0F, -46.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(70, 95).addBox(-1.0F, -2.0F, -52.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 45).addBox(-1.0F, -8.0F, -40.0F, 2.0F, 2.0F, 34.0F, new CubeDeformation(0.0F)).texOffs(68, 37).addBox(-1.0F, -10.0F, -36.0F, 2.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 20).addBox(-1.0F, -13.0F, -32.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition guard2 = blade.addOrReplaceChild("guard2", CubeListBuilder.create().texOffs(17, 45).addBox(-1.0F, 2.0F, -14.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 60).addBox(-1.0F, 4.0F, -18.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 45).addBox(-1.0F, 4.0F, -42.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(43, 15).addBox(-1.0F, 2.0F, -46.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(43, 0).addBox(-1.0F, 0.0F, -52.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(43, 0).addBox(-1.0F, 6.0F, -40.0F, 2.0F, 2.0F, 34.0F, new CubeDeformation(0.0F)).texOffs(39, 45).addBox(-1.0F, 8.0F, -36.0F, 2.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, 10.0F, -32.0F, 2.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lowerhandle = sword.addOrReplaceChild("lowerhandle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 11.0F));

        PartDefinition handle_r2 = lowerhandle.addOrReplaceChild("handle_r2", CubeListBuilder.create().texOffs(144, 118).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(60, 15).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(82, 15).addBox(-5.0F, -6.5F, -4.0F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(39, 82).addBox(-4.0F, -7.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(21, 20).addBox(-5.0F, -1.5F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 0).addBox(3.0F, -1.5F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(43, 30).addBox(-5.0F, -6.5F, -5.0F, 10.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(17, 106).addBox(-4.0F, -2.5F, -5.5F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(99, 73).addBox(-4.0F, -6.0F, -5.5F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(83, 129).addBox(-6.0F, -5.5F, -5.5F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(68, 45).addBox(4.0F, -5.5F, -5.5F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition cube_r17 = head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(132, 78).addBox(-5.5F, 0.25F, 0.0F, 11.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0F, 4.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r18 = head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(18, 116).addBox(-1.0F, 0.25F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r19 = head.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(98, 118).addBox(0.0F, 0.25F, -5.5F, 1.0F, 4.0F, 11.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r20 = head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(32, 4).addBox(-2.5F, -0.2823F, -2.0723F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-6.0F, -8.5F, 9.0F, 0.2233F, -0.2129F, -0.0479F));

        PartDefinition cube_r21 = head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 106).addBox(-2.5F, -2.5F, -6.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -6.5F, 10.0F, 0.2657F, -0.1685F, -0.0456F));

        PartDefinition cube_r22 = head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -4.5F, 2.0F, 0.3518F, 0.123F, 0.045F));

        PartDefinition cube_r23 = head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(104, 100).addBox(1.5F, -2.5F, -6.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -6.5F, 10.0F, 0.2657F, 0.1685F, 0.0456F));

        PartDefinition cube_r24 = head.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(32, 0).addBox(1.5F, -0.2823F, -2.0723F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(6.0F, -8.5F, 9.0F, 0.2233F, 0.2129F, 0.0479F));

        PartDefinition cube_r25 = head.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 60).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -4.5F, 2.0F, 0.3518F, -0.123F, -0.045F));

        PartDefinition skirtlower = waist.addOrReplaceChild("skirtlower", CubeListBuilder.create().texOffs(131, 23).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 121).addBox(-6.0F, 0.0F, 3.0F, 12.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 20).addBox(-7.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(6.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition skirtlower2 = skirtlower.addOrReplaceChild("skirtlower2", CubeListBuilder.create().texOffs(17, 45).addBox(6.0F, -1.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 20).addBox(6.0F, -1.0F, 3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 20).addBox(-7.0F, -1.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-7.0F, -1.0F, 3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(119, 0).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(112, 118).addBox(-7.0F, 0.0F, 4.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(25, 132).addBox(-8.0F, 0.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(21, 20).addBox(7.0F, 0.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition skirtlower3 = skirtlower2.addOrReplaceChild("skirtlower3", CubeListBuilder.create().texOffs(121, 58).addBox(-7.0F, 0.0F, -6.0F, 14.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(119, 6).addBox(-7.0F, 0.0F, 5.0F, 14.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(141, 92).addBox(-9.0F, 0.0F, -4.0F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(76, 72).addBox(8.0F, 0.0F, -4.0F, 1.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(56, 45).addBox(7.0F, 0.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(29, 54).addBox(7.0F, 0.0F, 4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(39, 45).addBox(-8.0F, 0.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 45).addBox(-8.0F, 0.0F, 4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition butt = hip.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(99, 64).addBox(-6.0F, 2.0F, -3.0F, 12.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -22.0F, 0.0F));

        PartDefinition leftleg = butt.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(119, 133).addBox(-3.0F, -1.0F, -2.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition leftlowerleg = leftleg.addOrReplaceChild("leftlowerleg", CubeListBuilder.create().texOffs(149, 59).addBox(-2.5F, -0.1F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(130, 67).addBox(-3.0F, 4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.3F)).texOffs(53, 112).addBox(-2.0F, 3.0F, -4.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).texOffs(21, 5).addBox(-1.0F, 2.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).texOffs(38, 147).addBox(-2.5F, 8.0F, -2.25F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.3F)).texOffs(121, 100).addBox(-3.0F, 10.0F, -4.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.3F)).texOffs(21, 13).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.5F, 7.0F, 0.0F));

        PartDefinition rightleg = butt.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(44, 134).addBox(-3.0F, -1.0F, -2.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition rightlowerleg = rightleg.addOrReplaceChild("rightlowerleg", CubeListBuilder.create().texOffs(80, 142).addBox(-2.5F, -0.1F, -2.0F, 5.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)).texOffs(0, 132).addBox(-3.0F, 4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.3F)).texOffs(19, 132).addBox(-2.0F, 3.0F, -4.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.3F)).texOffs(21, 25).addBox(-1.0F, 2.0F, -4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)).texOffs(149, 145).addBox(-2.5F, 8.0F, -2.25F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.3F)).texOffs(43, 124).addBox(-3.0F, 10.0F, -4.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.3F)).texOffs(21, 33).addBox(-2.0F, 9.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-0.5F, 7.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.getSyncBoolean(Harbinger.PEACEFUL)) {
            this.animate(entity.idleAnimState, HarbingerAnimation.PreFightIdle, ageInTicks);
            this.animate(entity.walkAnimState, HarbingerAnimation.PreFightIdle, ageInTicks);
            if(entity.otherAnimState.isStarted()){
                root().getAllParts().forEach(ModelPart::resetPose);
                this.animate(entity.otherAnimState, HarbingerAnimation.IdleTransition, ageInTicks);
            }

        } else {
            boolean b = entity.isUsingSword();
            double x = entity.getX() - entity.xOld;
            double z = entity.getZ() - entity.zOld;
            double speed = 20 * Math.sqrt(x*x+z*z);
            if (b) {
                this.animate(entity.walkAnimState, HarbingerAnimation.SwordWalk, ageInTicks, (float) speed);
                this.animate(entity.idleAnimState, HarbingerAnimation.SwordIdle, ageInTicks);
            } else {
                this.animate(entity.walkAnimState, HarbingerAnimation.ShieldWalk, ageInTicks, (float) speed);
                this.animate(entity.idleAnimState, HarbingerAnimation.ShieldIdle, ageInTicks);
            }
            if(entity.otherAnimState.isStarted() && entity.getSyncInt(Harbinger.ANIMTYPE)==1){
                if (entity.shouldResetRightArm()) {
                    this.parts.rightarm().resetPose();
                    this.parts.rightarm().getAllParts().forEach(ModelPart::resetPose);
                }
                this.animate(entity.otherAnimState, HarbingerAnimation.SwordUnsheathe, ageInTicks);
            }
            else if(entity.attackAnimState.isStarted() || entity.counterAnimState.isStarted()){
                if (entity.shouldResetRightArm()) {
                    this.parts.rightarm().resetPose();
                    this.parts.rightarm().getAllParts().forEach(ModelPart::resetPose);
                }
                this.parts.leftarm().resetPose();
                int n = entity.getSyncInt(Harbinger.ATTACKTYPE);
                if(b){
                    if(n==1){
                    this.animate(entity.attackAnimState,HarbingerAnimation.SwordSwing,ageInTicks);}
                    else if(n==2){
                        this.animate(entity.attackAnimState,HarbingerAnimation.SwordUppercut,ageInTicks);}
                    else{
                        this.animate(entity.attackAnimState,HarbingerAnimation.SwordSlam,ageInTicks);
                    }
                    this.animate(entity.counterAnimState,HarbingerAnimation.SwordCounter,ageInTicks);

                }else{
                    if(n==1){
                        this.animate(entity.attackAnimState,HarbingerAnimation.ShieldPush,ageInTicks);}
                    else if(n==2){
                        this.animate(entity.attackAnimState,HarbingerAnimation.ShieldSlam,ageInTicks);}
                    else{
                        this.animate(entity.attackAnimState,HarbingerAnimation.ShieldSwing,ageInTicks);
                    }
                    this.animate(entity.counterAnimState,HarbingerAnimation.ShieldCounter,ageInTicks);
                }
            }
            if (!entity.shouldLockHead()) {
                this.parts.head().resetPose();
                this.parts.head().yRot = (netHeadYaw * (float) Math.PI / 180f);
                this.parts.head().xRot = (headPitch * (float) Math.PI / 180f);
            }
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

}