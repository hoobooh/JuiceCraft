package com.usagin.juicecraft.client.models.alte;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

import static com.usagin.juicecraft.client.animation.AlteAnimation1.*;
import static com.usagin.juicecraft.client.animation.AlteAnimation2.*;
import static com.usagin.juicecraft.client.animation.AlteAnimation3.*;
import static com.usagin.juicecraft.client.animation.AlteAnimation4.*;

public class AlteEntityModel extends FriendEntityModel<Alte> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JuiceCraft.MODID, "alteentitymodel"), "main");

    public AlteEntityModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition customroot = partdefinition.addOrReplaceChild("customroot", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hip = customroot.addOrReplaceChild("hip", CubeListBuilder.create().texOffs(342, 217).addBox(-16.0F, -84.0F, -7.0F, 32.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(296, 313).addBox(-17.0F, -84.0F, -4.0F, 34.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition butt = hip.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(290, 92).addBox(-19.0F, 0.0F, 2.0F, 38.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -71.0F, -7.0F));

        PartDefinition rightleg = butt.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(869, 428).addBox(-8.0F, 0.0F, -7.0F, 16.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 11.0F, 11.0F));

        PartDefinition lowerleg2 = rightleg.addOrReplaceChild("lowerleg2", CubeListBuilder.create().texOffs(286, 184).addBox(-4.0F, -1.0F, -8.0F, 8.0F, 21.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(134, 608).addBox(-8.0F, 1.0F, -4.0F, 16.0F, 19.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(342, 570).addBox(-8.0F, 30.0F, -9.0F, 16.0F, 6.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(488, 38).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 34.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(418, 600).addBox(1.0F, 0.0F, -9.0F, 8.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(696, 62).addBox(-1.0F, 0.0F, -7.0F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(354, 599).addBox(-9.0F, 0.0F, -9.0F, 8.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leftleg = butt.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(869, 428).addBox(-8.0F, 0.0F, -7.0F, 16.0F, 24.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, 11.0F, 11.0F));

        PartDefinition lowerleg = leftleg.addOrReplaceChild("lowerleg", CubeListBuilder.create().texOffs(290, 0).addBox(-4.0F, -1.0F, -8.0F, 8.0F, 21.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(608, 610).addBox(-8.0F, 1.0F, -4.0F, 16.0F, 19.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(696, 40).addBox(-1.0F, 0.0F, -7.0F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(276, 570).addBox(-8.0F, 30.0F, -9.0F, 16.0F, 6.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(74, 499).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 34.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(590, 583).addBox(1.0F, 0.0F, -9.0F, 8.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(320, 593).addBox(-9.0F, 0.0F, -9.0F, 8.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upperdress = hip.addOrReplaceChild("upperdress", CubeListBuilder.create().texOffs(0, 496).addBox(-20.0F, 1.0F, -14.0F, 40.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(284, 491).addBox(-20.0F, 1.0F, 10.0F, 40.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(514, 266).addBox(20.0F, -2.0F, -12.0F, 2.0F, 16.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(510, 472).addBox(-22.0F, -2.0F, -12.0F, 2.0F, 16.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(148, 88).addBox(-20.0F, -6.0F, -12.0F, 40.0F, 16.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -74.0F, 4.0F));

        PartDefinition middledress = upperdress.addOrReplaceChild("middledress", CubeListBuilder.create().texOffs(552, 556).addBox(-20.0F, 11.0F, -18.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(552, 544).addBox(-20.0F, 11.0F, 14.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(514, 339).addBox(22.0F, 5.0F, -12.0F, 3.0F, 9.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(514, 306).addBox(-25.0F, 5.0F, -12.0F, 3.0F, 9.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(668, 532).addBox(20.0F, 8.0F, 12.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(668, 400).addBox(-23.0F, 8.0F, 12.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(544, 664).addBox(-23.0F, 8.0F, -15.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(524, 662).addBox(20.0F, 8.0F, -15.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition middletwodress = middledress.addOrReplaceChild("middletwodress", CubeListBuilder.create().texOffs(284, 512).addBox(-20.0F, 10.0F, -22.0F, 40.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(142, 507).addBox(-20.0F, 10.0F, 18.0F, 40.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(552, 568).addBox(25.0F, 4.0F, -12.0F, 4.0F, 9.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(482, 568).addBox(-29.0F, 4.0F, -12.0F, 4.0F, 9.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(24, 682).addBox(20.0F, 7.0F, 12.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(376, 670).addBox(-26.0F, 7.0F, 12.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(336, 670).addBox(-26.0F, 7.0F, -18.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(568, 664).addBox(20.0F, 7.0F, -18.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition lowerdress = middletwodress.addOrReplaceChild("lowerdress", CubeListBuilder.create().texOffs(552, 532).addBox(-20.0F, 4.0F, -26.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(142, 520).addBox(-20.0F, 4.0F, 22.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(572, 432).addBox(29.0F, -2.0F, -12.0F, 4.0F, 8.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(572, 400).addBox(-33.0F, -2.0F, -12.0F, 4.0F, 8.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(688, 186).addBox(26.0F, -2.0F, 12.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(350, 692).addBox(20.0F, 1.0F, 18.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(622, 701).addBox(-26.0F, 1.0F, 18.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(386, 686).addBox(-30.0F, -2.0F, 12.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(688, 157).addBox(-30.0F, -2.0F, -18.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(314, 692).addBox(-26.0F, 1.0F, -22.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(222, 699).addBox(20.0F, 1.0F, -22.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(530, 38).addBox(26.0F, -2.0F, -18.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

        PartDefinition weaponholster = hip.addOrReplaceChild("weaponholster", CubeListBuilder.create(), PartPose.offset(-23.0F, -68.0F, 32.0F));

        PartDefinition waist = hip.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(438, 496).addBox(-14.0F, -18.0F, -13.0F, 28.0F, 22.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(580, 360).addBox(-11.0F, -18.0F, -5.0F, 22.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(134, 582).addBox(-11.0F, -18.0F, -17.0F, 22.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(177, 711).addBox(-9.0F, -15.0F, -1.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(139, 709).addBox(-4.0F, -14.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(275, 707).addBox(4.0F, -15.0F, -1.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(572, 709).addBox(-9.0F, -15.0F, -19.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(630, 715).addBox(4.0F, -15.0F, -19.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -84.0F, 13.0F));

        PartDefinition jacketlower = waist.addOrReplaceChild("jacketlower", CubeListBuilder.create().texOffs(584, 568).addBox(-14.0F, -8.0F, -11.0F, 28.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(580, 248).addBox(-14.0F, -8.0F, 7.0F, 28.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(99, 142).addBox(13.0F, -8.0F, -8.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(99, 0).addBox(-17.0F, -8.0F, -8.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(626, 12).addBox(-14.0F, -12.0F, -8.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(354, 625).addBox(-14.0F, -12.0F, 4.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(448, 624).addBox(11.0F, -12.0F, 4.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(418, 624).addBox(11.0F, -12.0F, -8.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -9.0F));

        PartDefinition jacketlower2 = jacketlower.addOrReplaceChild("jacketlower2", CubeListBuilder.create().texOffs(552, 607).addBox(-14.0F, -8.0F, -14.0F, 28.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(604, 443).addBox(-14.0F, -8.0F, 10.0F, 28.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(584, 664).addBox(16.0F, -8.0F, -8.0F, 4.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(544, 664).addBox(-20.0F, -8.0F, -8.0F, 4.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(380, 313).addBox(-18.0F, -8.0F, -12.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(322, 0).addBox(-18.0F, -8.0F, 8.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(318, 184).addBox(14.0F, -8.0F, 8.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(217, 308).addBox(14.0F, -8.0F, -12.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition jacketlower3 = jacketlower2.addOrReplaceChild("jacketlower3", CubeListBuilder.create().texOffs(694, 731).addBox(-14.0F, -8.0F, -18.0F, 28.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(604, 411).addBox(-14.0F, -8.0F, 14.0F, 28.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(664, 192).addBox(20.0F, -8.0F, -8.0F, 4.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(664, 163).addBox(-24.0F, -8.0F, -8.0F, 4.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 704).addBox(-19.0F, -8.0F, -15.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(544, 703).addBox(-21.0F, -8.0F, -13.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F))
                .texOffs(702, 666).addBox(-19.0F, -8.0F, 11.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(692, 374).addBox(-21.0F, -8.0F, 8.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F))
                .texOffs(520, 689).addBox(14.0F, -8.0F, 10.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(163, 724).addBox(17.0F, -8.0F, 8.0F, 4.0F, 8.0F, 5.0F, new CubeDeformation(-0.01F))
                .texOffs(684, 400).addBox(14.0F, -8.0F, -15.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(684, 486).addBox(17.0F, -8.0F, -13.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition jackethem = jacketlower3.addOrReplaceChild("jackethem", CubeListBuilder.create().texOffs(636, 179).addBox(5.0F, -8.0F, -22.0F, 9.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(452, 600).addBox(-14.0F, -8.0F, -22.0F, 9.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(684, 134).addBox(-5.0F, -11.0F, -22.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(552, 901).addBox(2.0F, -11.0F, -22.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(604, 400).addBox(-14.0F, -8.0F, 18.0F, 28.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(174, 682).addBox(24.0F, -11.0F, -8.0F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(134, 682).addBox(-28.0F, -11.0F, -8.0F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(245, 682).addBox(-19.0F, -9.0F, -19.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(664, 555).addBox(-24.0F, -10.0F, -13.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(659, 268).addBox(-19.0F, -9.0F, 15.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(485, 647).addBox(-24.0F, -10.0F, 8.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(652, 664).addBox(14.0F, -9.0F, 13.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(643, 638).addBox(20.0F, -10.0F, 8.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(552, 618).addBox(14.0F, -9.0F, -19.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(340, 618).addBox(20.0F, -10.0F, -13.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(276, 313).addBox(-21.0F, -10.0F, 13.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(266, 254).addBox(-21.0F, -10.0F, -15.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(99, 166).addBox(19.0F, -10.0F, -15.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(99, 24).addBox(19.0F, -10.0F, 13.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition chest = waist.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(572, 508).addBox(-16.0F, -16.0F, -20.0F, 32.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(296, 274).addBox(-16.0F, -18.0F, -19.0F, 32.0F, 18.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(384, 103).addBox(-14.0F, -19.0F, -18.0F, 28.0F, 1.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(552, 643).addBox(-11.0F, -23.0F, -14.0F, 2.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(166, 325).addBox(-5.0F, -21.0F, 3.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(290, 52).addBox(-7.0F, -22.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(386, 132).addBox(-9.0F, -22.0F, -2.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(568, 643).addBox(7.0F, -22.0F, -21.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(320, 375).addBox(7.0F, -22.0F, -2.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 286).addBox(5.0F, -22.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(643, 268).addBox(9.0F, -23.0F, -14.0F, 2.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(24, 98).addBox(4.0F, -21.0F, -22.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(266, 263).addBox(-7.0F, -21.0F, -22.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(395, 646).addBox(-9.0F, -22.0F, -21.0F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(470, 601).addBox(-6.0F, -16.0F, -6.0F, 12.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -9.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 550).addBox(-3.0F, -41.0F, -12.0F, 6.0F, 9.0F, 23.0F, new CubeDeformation(-0.01F))
                .texOffs(428, 211).addBox(-10.0F, -38.0F, -13.0F, 20.0F, 6.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(648, 491).addBox(-13.0F, -39.0F, -8.0F, 12.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(241, 286).addBox(-22.0F, -28.0F, 0.0F, 6.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(686, 534).addBox(-19.0F, -30.0F, -6.0F, 4.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(580, 192).addBox(17.0F, -29.0F, -4.0F, 5.0F, 13.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(145, 231).addBox(-15.0F, -35.0F, -15.0F, 30.0F, 7.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(727, 161).addBox(-2.0F, -32.0F, -17.0F, 19.0F, 9.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(428, 175).addBox(-10.0F, -29.0F, -17.0F, 8.0F, 2.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(733, 462).addBox(-17.0F, -32.0F, -17.0F, 7.0F, 9.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(562, 0).addBox(-20.0F, -16.0F, 4.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(284, 528).addBox(-20.0F, -16.0F, 0.0F, 40.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 71).addBox(-16.0F, -28.0F, -16.0F, 32.0F, 28.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(594, 876).addBox(-4.0F, -5.0F, -5.5F, 8.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -32.0F, -7.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(235, 230).addBox(-1.0F, -15.0F, -5.0F, 6.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -32.0F, -7.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(457, 719).addBox(8.0F, 2.0F, 2.0F, 5.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.2062F, -0.0704F, -1.1532F));

        PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(428, 211).addBox(19.0F, 9.0F, 9.0F, 3.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0864F, -0.0691F, -0.3142F));

        PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(11.0F, 8.0F, 3.0F, 5.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.176F, -0.1289F, -0.8519F));

        PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(214, 516).addBox(9.0F, -13.0F, 2.0F, 8.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0F, 0.0F, 0.7418F));

        PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(88, 484).addBox(-18.0F, -15.0F, 6.0F, 11.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, -0.0229F, 0.1289F, -0.8305F));

        PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(543, 878).addBox(-11.0F, -13.0F, 5.0F, 11.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, -0.0643F, 0.1251F, -0.4784F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(656, 102).addBox(-12.0F, 0.0F, -5.0F, 7.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0F, 0.0F, 1.2654F));

        PartDefinition cube_r10 = head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(640, 532).addBox(-13.0F, 7.0F, -6.0F, 7.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0F, 0.0F, 1.1345F));

        PartDefinition cube_r11 = head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(632, 359).addBox(-8.0F, 7.0F, 8.8F, 16.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r12 = head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(777, 386).addBox(-9.0F, -11.5F, -5.0F, 20.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r13 = head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(616, 134).addBox(-11.0F, -12.0F, -10.0F, 24.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 1.0472F, 0.0F, 0.0F));

        PartDefinition cube_r14 = head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(198, 308).addBox(6.0F, -8.5F, 3.0F, 4.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.065F, -0.1705F, -0.3533F));

        PartDefinition cube_r15 = head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(770, 304).addBox(-8.0F, -9.0F, -4.0F, 4.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r16 = head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(751, 427).addBox(4.0F, -9.0F, -7.0F, 5.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r17 = head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(232, 394).addBox(-9.5F, -9.0F, 5.0F, 5.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.0F, -4.0F, 0.0285F, 0.1278F, 0.3945F));

        PartDefinition backhairupper = head.addOrReplaceChild("backhairupper", CubeListBuilder.create().texOffs(580, 224).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 682).addBox(-10.0F, 4.0F, 4.0F, 20.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(246, 507).addBox(-18.0F, 4.0F, -9.0F, 2.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(99, 0).addBox(-16.0F, 4.0F, 0.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(296, 274).addBox(12.0F, 4.0F, 0.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(386, 180).addBox(16.0F, 4.0F, -9.0F, 2.0F, 14.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -28.0F, 16.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r18 = backhairupper.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(286, 171).addBox(-9.0F, 0.5F, 3.0F, 18.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition backhairlower = backhairupper.addOrReplaceChild("backhairlower", CubeListBuilder.create().texOffs(580, 134).addBox(13.0F, 0.0F, 1.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(123, 0).addBox(-17.0F, 0.0F, 1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(128, 103).addBox(16.0F, 5.0F, 2.0F, 2.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(258, 469).addBox(-18.0F, 6.0F, 3.0F, 4.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 79).addBox(-19.0F, 11.0F, 5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(142, 382).addBox(-12.0F, 4.0F, 0.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-2.0F, 4.0F, 4.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(299, 670).addBox(4.0F, 8.0F, 0.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(198, 286).addBox(6.0F, 6.0F, 4.0F, 3.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(518, 479).addBox(0.0F, 8.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(556, 458).addBox(0.0F, 6.0F, 4.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(168, 461).addBox(8.0F, 16.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(584, 580).addBox(-12.0F, 8.0F, 0.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(449, 132).addBox(-9.0F, 4.0F, 4.0F, 5.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r19 = backhairlower.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(252, 88).addBox(9.0F, 32.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, -4.0F, 0.1309F, 0.0F, -0.3927F));

        PartDefinition cube_r20 = backhairlower.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(402, 295).addBox(12.0F, 20.0F, 4.0F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, -4.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r21 = backhairlower.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(320, 361).addBox(-15.0F, 19.0F, 2.0F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, -4.0F, 0.0436F, 0.0F, 0.2182F));

        PartDefinition cube_r22 = backhairlower.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 213).addBox(-12.0F, 31.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, -4.0F, 0.1745F, 0.0F, 0.3491F));

        PartDefinition leftsidehair = head.addOrReplaceChild("leftsidehair", CubeListBuilder.create().texOffs(386, 132).addBox(0.0F, -4.0F, -18.0F, 4.0F, 14.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(148, 88).addBox(0.0F, -1.0F, -9.0F, 5.0F, 15.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(158, 682).addBox(2.0F, 6.0F, 5.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(580, 163).addBox(4.0F, 9.0F, 8.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(418, 600).addBox(7.0F, 15.0F, 9.0F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(416, 431).addBox(10.0F, 20.0F, 10.0F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(134, 193).addBox(12.0F, 28.0F, 9.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -25.0F, 2.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition cube_r23 = leftsidehair.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(354, 599).addBox(8.5758F, 2.811F, 0.0F, 5.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(672, 22).addBox(5.5758F, -1.189F, -1.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(396, 498).addBox(15.5758F, 9.811F, 3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(276, 570).addBox(12.5758F, 6.811F, 1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5758F, 6.189F, 12.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition rightsidehair = head.addOrReplaceChild("rightsidehair", CubeListBuilder.create().texOffs(252, 88).addBox(-5.0F, 0.0F, -8.0F, 4.0F, 13.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(514, 266).addBox(-5.0F, 0.0F, -16.0F, 4.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(252, 606).addBox(-7.0F, 5.0F, 7.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(120, 71).addBox(-10.0F, 8.0F, 10.0F, 5.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(290, 92).addBox(-12.0F, 13.0F, 11.0F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(318, 132).addBox(-14.0F, 18.0F, 12.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 142).addBox(-15.0F, 27.0F, 11.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -24.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition cube_r24 = rightsidehair.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(474, 702).addBox(-13.5758F, 3.811F, 0.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(640, 555).addBox(-10.5758F, -1.189F, -1.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(276, 580).addBox(-16.5758F, 10.811F, 4.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(580, 626).addBox(-15.5758F, 7.811F, 1.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5758F, 5.189F, 14.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition bangs = head.addOrReplaceChild("bangs", CubeListBuilder.create().texOffs(314, 71).addBox(-12.0F, -4.0F, -3.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(144, 186).addBox(-4.0F, -4.0F, -3.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(322, 11).addBox(-8.0F, -4.0F, -3.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(198, 308).addBox(-10.0F, -4.0F, -3.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(412, 369).addBox(-16.0F, -4.0F, -3.0F, 4.0F, 18.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(222, 286).addBox(-15.0F, 14.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(394, 44).addBox(-14.0F, 18.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(416, 418).addBox(-16.0F, -4.0F, -4.0F, 4.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(96, 71).addBox(-21.0F, 0.0F, -4.0F, 5.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(290, 0).addBox(-24.0F, 8.0F, -3.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(296, 313).addBox(0.0F, -4.0F, -4.0F, 2.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(109, 142).addBox(-5.0F, 12.0F, -3.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(134, 33).addBox(-4.0F, 6.0F, -4.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(134, 134).addBox(-3.0F, 12.0F, -4.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(118, 582).addBox(-3.0F, -4.0F, -4.0F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(686, 647).addBox(2.0F, -4.0F, -4.0F, 14.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(235, 254).addBox(5.0F, 4.0F, -4.0F, 12.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(498, 388).addBox(9.0F, 8.0F, -4.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(380, 324).addBox(11.0F, 12.0F, -4.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -24.0F, -16.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leftahoge = head.addOrReplaceChild("leftahoge", CubeListBuilder.create().texOffs(0, 142).addBox(3.0F, 31.0F, -3.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(241, 286).addBox(3.0F, 24.0F, -6.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(410, 268).addBox(4.0F, 13.0F, -9.0F, 2.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(550, 372).addBox(5.0F, 5.0F, -6.0F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(134, 44).addBox(2.0F, 33.0F, 0.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(644, 690).addBox(2.0F, 4.0F, -10.0F, 3.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(683, 312).addBox(2.0F, 12.0F, -13.0F, 3.0F, 16.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(391, 570).addBox(1.0F, 23.0F, -10.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(268, 286).addBox(1.0F, 30.0F, -7.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(130, 123).addBox(0.0F, 30.0F, -5.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -32.0F, -7.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition cube_r25 = leftahoge.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(123, 142).addBox(9.0F, 16.0F, 9.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(148, 0).addBox(7.0F, 13.0F, 7.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(268, 88).addBox(6.0F, 11.0F, 5.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(286, 184).addBox(4.0F, 7.0F, 2.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, -0.0435F, -0.3529F));

        PartDefinition cube_r26 = leftahoge.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(636, 152).addBox(3.0F, -3.0F, 1.0F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.7854F));

        PartDefinition cube_r27 = leftahoge.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(404, 406).addBox(2.0F, 17.0F, -1.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 161).addBox(2.0F, 27.0F, 4.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(126, 24).addBox(2.0F, 23.0F, 2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(526, 613).addBox(3.0F, 8.0F, -3.0F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r28 = leftahoge.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(657, 643).addBox(0.0F, 4.0F, -5.0F, 4.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition topahoge = head.addOrReplaceChild("topahoge", CubeListBuilder.create().texOffs(491, 225).addBox(-11.0F, -7.0F, -3.0F, 9.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(250, 394).addBox(-16.0F, -8.0F, -4.0F, 11.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(228, 455).addBox(-19.0F, -7.0F, -5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(116, 217).addBox(-21.0F, -7.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(120, 89).addBox(-23.0F, -7.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -36.0F, -8.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition rightahoge = head.addOrReplaceChild("rightahoge", CubeListBuilder.create().texOffs(198, 656).addBox(-19.0F, 10.0F, 4.0F, 5.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(73, 628).addBox(-20.0F, 14.0F, 6.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(226, 338).addBox(-19.0F, 18.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(126, 166).addBox(-18.0F, 22.0F, 8.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(148, 44).addBox(-16.0F, 26.0F, 9.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(518, 462).addBox(-12.0F, 1.0F, -4.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(532, 247).addBox(-14.0F, 4.0F, -6.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(752, 349).addBox(-16.0F, 8.0F, -6.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(582, 650).addBox(-17.0F, 12.0F, -5.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(474, 530).addBox(-16.0F, 16.0F, -4.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(386, 184).addBox(-14.0F, 20.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(99, 142).addBox(-12.0F, 22.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -32.0F, -7.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r29 = rightahoge.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(110, 71).addBox(-19.0F, 20.0F, 4.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(318, 195).addBox(-19.0F, 18.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(322, 174).addBox(-18.0F, 16.0F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(260, 142).addBox(-18.0F, 13.0F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(260, 186).addBox(-17.0F, 10.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r30 = rightahoge.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(68, 550).addBox(-12.0F, 4.0F, -2.0F, 6.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r31 = rightahoge.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(198, 286).addBox(-13.5F, 4.0F, 3.0F, 7.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition collarback = chest.addOrReplaceChild("collarback", CubeListBuilder.create().texOffs(506, 166).addBox(-14.0F, 0.0446F, 0.0427F, 28.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 97).addBox(-10.0F, 7.0446F, 0.0427F, 20.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 2.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition collarfront = chest.addOrReplaceChild("collarfront", CubeListBuilder.create().texOffs(132, 279).addBox(-1.0F, 12.0F, -4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(222, 293).addBox(-2.0F, 1.0F, -5.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(268, 297).addBox(-3.0F, 9.0F, -5.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(232, 268).addBox(-4.0F, 10.0F, -4.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(99, 154).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(300, 648).addBox(-4.0F, 3.0F, -6.0F, 8.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(276, 648).addBox(-5.0F, 2.0F, -4.0F, 10.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(290, 123).addBox(-14.0F, 0.0F, -2.0F, 28.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(230, 507).addBox(2.0F, 10.0F, -2.0F, 11.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(372, 528).addBox(-13.0F, 10.0F, -2.0F, 11.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(644, 385).addBox(-12.0F, 5.0F, -2.0F, 24.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, -18.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition collarfrontlower = collarfront.addOrReplaceChild("collarfrontlower", CubeListBuilder.create().texOffs(68, 567).addBox(6.0F, 0.0F, -2.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(508, 266).addBox(10.0F, 4.0F, -2.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(326, 221).addBox(13.0F, 8.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(306, 52).addBox(-15.0F, 8.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(430, 0).addBox(-15.0F, 4.0F, -2.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(594, 521).addBox(-14.0F, 0.0F, -2.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition leftarm = chest.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(859, 147).addBox(0.0F, 0.0F, -6.0F, 11.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -18.0F, -7.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition lowerarm2 = leftarm.addOrReplaceChild("lowerarm2", CubeListBuilder.create().texOffs(861, 281).addBox(-2.0F, 13.0F, -6.0F, 6.0F, 15.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(861, 281).addBox(-2.0F, 0.0F, -6.0F, 3.0F, 13.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(384, 406).addBox(-6.0F, 0.0F, -6.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 28.0F, 0.0F));

        PartDefinition lowersleeve4 = lowerarm2.addOrReplaceChild("lowersleeve4", CubeListBuilder.create().texOffs(580, 134).addBox(-6.0F, 0.0F, -10.0F, 8.0F, 9.0F, 20.0F, new CubeDeformation(0.001F))
                .texOffs(142, 286).addBox(5.0F, 2.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(238, 469).addBox(1.0F, 1.0F, -6.0F, 4.0F, 9.0F, 12.0F, new CubeDeformation(0.001F))
                .texOffs(648, 555).addBox(-8.0F, 0.0F, -6.0F, 2.0F, 9.0F, 12.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition lowersleeve5 = lowersleeve4.addOrReplaceChild("lowersleeve5", CubeListBuilder.create().texOffs(342, 132).addBox(-6.0F, 0.0F, -14.0F, 8.0F, 5.0F, 28.0F, new CubeDeformation(0.001F))
                .texOffs(185, 448).addBox(-6.0F, 5.0F, 10.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(144, 256).addBox(-6.0F, 5.0F, -14.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(224, 656).addBox(8.0F, 2.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(656, 70).addBox(5.0F, 1.0F, 2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(0, 650).addBox(5.0F, 1.0F, -6.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(642, 594).addBox(2.0F, 0.0F, -10.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(640, 532).addBox(2.0F, 0.0F, 6.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(96, 71).addBox(-8.0F, -1.0F, -10.0F, 2.0F, 6.0F, 20.0F, new CubeDeformation(0.001F))
                .texOffs(682, 268).addBox(-10.0F, -2.0F, -6.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 9.0F, 0.0F));

        PartDefinition lowersleeve6 = lowersleeve5.addOrReplaceChild("lowersleeve6", CubeListBuilder.create().texOffs(674, 253).addBox(-6.0F, 0.0F, -18.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(418, 647).addBox(-6.0F, 0.0F, 14.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(257, 418).addBox(11.0F, 3.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(163, 418).addBox(8.0F, 2.0F, -6.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(116, 396).addBox(8.0F, 2.0F, 2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(493, 629).addBox(-8.0F, 0.0F, 10.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(472, 629).addBox(-10.0F, -1.0F, 6.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(626, 48).addBox(-8.0F, 0.0F, -14.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(404, 132).addBox(-10.0F, -1.0F, -10.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(381, 274).addBox(5.0F, 1.0F, -10.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(0, 628).addBox(2.0F, 1.0F, -14.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(401, 180).addBox(2.0F, 1.0F, 10.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(259, 230).addBox(5.0F, 1.0F, 6.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(98, 396).addBox(-13.0F, -2.0F, -6.0F, 3.0F, 6.0F, 12.0F, new CubeDeformation(0.004F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition grabber2 = lowerarm2.addOrReplaceChild("grabber2", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition rightarm = chest.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(861, 202).addBox(-11.0F, 0.0F, -6.0F, 11.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -18.0F, -7.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition lowerarm = rightarm.addOrReplaceChild("lowerarm", CubeListBuilder.create().texOffs(880, 855).addBox(-4.0F, 13.0F, -6.0F, 6.0F, 15.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(880, 855).addBox(-1.0F, 0.0F, -6.0F, 3.0F, 13.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(237, 418).addBox(2.0F, 0.0F, -6.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 28.0F, 0.0F));

        PartDefinition lowersleeve = lowerarm.addOrReplaceChild("lowersleeve", CubeListBuilder.create().texOffs(580, 163).addBox(-2.0F, 0.0F, -10.0F, 8.0F, 9.0F, 20.0F, new CubeDeformation(0.001F))
                .texOffs(174, 286).addBox(-8.0F, 2.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(514, 568).addBox(-5.0F, 1.0F, -6.0F, 4.0F, 9.0F, 12.0F, new CubeDeformation(0.001F))
                .texOffs(668, 400).addBox(6.0F, 0.0F, -6.0F, 2.0F, 9.0F, 12.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition lowersleeve2 = lowersleeve.addOrReplaceChild("lowersleeve2", CubeListBuilder.create().texOffs(342, 184).addBox(-2.0F, 0.0F, -14.0F, 8.0F, 5.0F, 28.0F, new CubeDeformation(0.001F))
                .texOffs(103, 568).addBox(-2.0F, 5.0F, 10.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(542, 284).addBox(-2.0F, 5.0F, -14.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(98, 660).addBox(-11.0F, 2.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(474, 662).addBox(-8.0F, 1.0F, 2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(148, 658).addBox(-8.0F, 1.0F, -6.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(500, 662).addBox(-5.0F, 0.0F, -10.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(134, 658).addBox(-5.0F, 0.0F, 6.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(162, 617).addBox(6.0F, -1.0F, -10.0F, 2.0F, 6.0F, 20.0F, new CubeDeformation(0.001F))
                .texOffs(236, 699).addBox(8.0F, -2.0F, -6.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 9.0F, 0.0F));

        PartDefinition lowersleeve3 = lowersleeve2.addOrReplaceChild("lowersleeve3", CubeListBuilder.create().texOffs(682, 229).addBox(-2.0F, 0.0F, -18.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(198, 682).addBox(-2.0F, 0.0F, 14.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(205, 426).addBox(-14.0F, 3.0F, -2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(534, 568).addBox(-11.0F, 2.0F, -6.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(116, 499).addBox(-11.0F, 2.0F, 2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(276, 658).addBox(6.0F, 0.0F, 10.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(643, 268).addBox(8.0F, -1.0F, 6.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(654, 468).addBox(6.0F, 0.0F, -14.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(552, 643).addBox(8.0F, -1.0F, -10.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(258, 621).addBox(-8.0F, 1.0F, -10.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(224, 637).addBox(-5.0F, 1.0F, -14.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(134, 635).addBox(-5.0F, 1.0F, 10.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(534, 597).addBox(-8.0F, 1.0F, 6.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.004F))
                .texOffs(678, 356).addBox(10.0F, -2.0F, -6.0F, 3.0F, 6.0F, 12.0F, new CubeDeformation(0.004F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition grabber = lowerarm.addOrReplaceChild("grabber", CubeListBuilder.create().texOffs(282, 135).addBox(-2.0F, -2.0F, 24.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(636, 491).addBox(-4.0F, -4.0F, 27.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(676, 298).addBox(-3.0F, -3.0F, 25.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 71).addBox(-3.0F, -3.0F, 9.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(650, 0).addBox(-4.0F, -4.0F, -60.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(608, 664).addBox(-4.0F, -4.0F, -66.0F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(686, 595).addBox(-3.0F, -3.0F, -69.0F, 6.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(82, 849).addBox(-2.0F, -1.0F, -116.0F, 4.0F, 2.0F, 140.0F, new CubeDeformation(0.0F))
                .texOffs(680, 831).addBox(-1.0F, -2.0F, -116.0F, 2.0F, 4.0F, 140.0F, new CubeDeformation(0.0F))
                .texOffs(414, 444).addBox(-2.0F, -2.0F, -119.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 713).addBox(-1.0F, -1.0F, -134.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 28.0F, 0.0F));

        PartDefinition innermetal = grabber.addOrReplaceChild("innermetal", CubeListBuilder.create().texOffs(103, 550).addBox(-1.0129F, 7.0143F, 43.2957F, 2.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(257, 490).addBox(-1.0129F, 10.0143F, 54.2957F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(320, 593).addBox(-1.0129F, 14.0143F, 37.2957F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(286, 136).addBox(-1.0129F, 21.0143F, 31.2957F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(268, 373).addBox(-1.0129F, 24.0143F, 25.2957F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(696, 0).addBox(-1.0129F, 21.0143F, 9.2957F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(366, 692).addBox(-1.0129F, 15.0143F, -0.7043F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(330, 692).addBox(-1.0129F, 9.0143F, -6.7043F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(294, 692).addBox(-1.0129F, 3.0143F, -12.7043F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(698, 694).addBox(-1.0129F, 1.0143F, -16.7043F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(648, 509).addBox(-2.0129F, 8.0143F, 44.2957F, 4.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(472, 629).addBox(-2.0129F, 11.0143F, 44.2957F, 4.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(428, 175).addBox(-2.0129F, 15.0143F, 38.2957F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(142, 418).addBox(-2.0129F, 22.0143F, 32.2957F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(314, 37).addBox(-2.0129F, 26.0143F, 24.2957F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(446, 629).addBox(-2.0129F, 29.0143F, 18.2957F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(478, 733).addBox(-2.0129F, 22.0143F, 8.2957F, 4.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(678, 666).addBox(-2.0129F, 16.0143F, -1.7043F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 682).addBox(-2.0129F, 10.0143F, -7.7043F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(40, 682).addBox(-2.0129F, 4.0143F, -13.7043F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(698, 24).addBox(-2.0129F, 2.0143F, -15.7043F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0129F, -0.0143F, -118.2957F));

        PartDefinition outershell = grabber.addOrReplaceChild("outershell", CubeListBuilder.create().texOffs(544, 687).addBox(-4.0129F, 6.0143F, -14.7043F, 9.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(611, 684).addBox(-4.0129F, 12.0143F, -10.7043F, 9.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(506, 601).addBox(-4.0129F, 10.0143F, -11.7043F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(99, 166).addBox(-4.0129F, 16.0143F, -6.7043F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(378, 387).addBox(-4.0129F, 23.0143F, 2.2957F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(384, 264).addBox(-4.0129F, 30.0143F, 15.2957F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(268, 670).addBox(-4.0129F, 18.0143F, -5.7043F, 9.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(611, 637).addBox(-4.0129F, 25.0143F, 3.2957F, 9.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(268, 688).addBox(-4.0129F, 32.0143F, 15.2957F, 9.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(26, 652).addBox(-4.0129F, 31.0143F, 33.2957F, 9.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(240, 406).addBox(-4.0129F, 37.0143F, 29.2957F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(474, 683).addBox(-2.0129F, 4.0143F, -24.7043F, 5.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(549, 247).addBox(-2.0129F, 3.0143F, -26.7043F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(474, 662).addBox(-2.0129F, 7.0143F, -18.7043F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(42, 652).addBox(-2.0129F, 12.0143F, -14.7043F, 5.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(74, 637).addBox(-2.0129F, 18.0143F, -9.7043F, 5.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(46, 628).addBox(-2.0129F, 25.0143F, -0.7043F, 5.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(696, 104).addBox(-2.0129F, 32.0143F, 12.2957F, 5.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(620, 503).addBox(-2.0129F, 33.0143F, 26.2957F, 5.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(385, 103).addBox(-2.0129F, 28.0143F, 16.2957F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(514, 372).addBox(-3.0129F, 27.0143F, 18.2957F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(580, 618).addBox(-3.0129F, 34.0143F, 41.2957F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(686, 575).addBox(-3.0129F, 32.0143F, 28.2957F, 7.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(624, 664).addBox(-3.0129F, 31.0143F, 14.2957F, 7.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(612, 268).addBox(-3.0129F, 24.0143F, 1.2957F, 7.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(418, 624).addBox(-3.0129F, 17.0143F, -7.7043F, 7.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 628).addBox(-3.0129F, 11.0143F, -12.7043F, 7.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(626, 106).addBox(-3.0129F, 5.0143F, -16.7043F, 7.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(160, 643).addBox(-3.0129F, 3.0143F, -22.7043F, 7.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0129F, -0.0143F, -118.2957F));

        PartDefinition innermetal2 = grabber.addOrReplaceChild("innermetal2", CubeListBuilder.create().texOffs(542, 266).addBox(-1.0129F, -13.9857F, 43.2957F, 2.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(80, 479).addBox(-1.0129F, -13.9857F, 54.2957F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(580, 192).addBox(-1.0129F, -21.9857F, 37.2957F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(124, 245).addBox(-1.0129F, -26.9857F, 31.2957F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(18, 168).addBox(-1.0129F, -26.9857F, 25.2957F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(500, 689).addBox(-1.0129F, -26.9857F, 9.2957F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(678, 688).addBox(-1.0129F, -20.9857F, -0.7043F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(656, 48).addBox(-1.0129F, -14.9857F, -6.7043F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(656, 0).addBox(-1.0129F, -8.9857F, -12.7043F, 2.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(380, 242).addBox(-1.0129F, -2.9857F, -16.7043F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(644, 48).addBox(-2.0129F, -10.9857F, 44.2957F, 4.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(325, 570).addBox(-2.0129F, -14.9857F, 44.2957F, 4.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(428, 132).addBox(-2.0129F, -21.9857F, 38.2957F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(362, 52).addBox(-2.0129F, -28.9857F, 32.2957F, 4.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(310, 221).addBox(-2.0129F, -28.9857F, 24.2957F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(628, 468).addBox(-2.0129F, -33.9857F, 18.2957F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(423, 730).addBox(-2.0129F, -28.9857F, 8.2957F, 4.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(352, 670).addBox(-2.0129F, -21.9857F, -1.7043F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(283, 733).addBox(-2.0129F, -15.9857F, -7.7043F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(500, 667).addBox(-2.0129F, -9.9857F, -13.7043F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(572, 689).addBox(-2.0129F, -3.9857F, -15.7043F, 4.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0129F, -0.0143F, -118.2957F));

        PartDefinition outershell2 = grabber.addOrReplaceChild("outershell2", CubeListBuilder.create().texOffs(686, 631).addBox(-4.0129F, -9.9857F, -14.7043F, 9.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(214, 682).addBox(-4.0129F, -15.9857F, -10.7043F, 9.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(372, 512).addBox(-4.0129F, -11.9857F, -11.7043F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(99, 24).addBox(-4.0129F, -17.9857F, -6.7043F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(386, 205).addBox(-4.0129F, -24.9857F, 2.2957F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(142, 325).addBox(-4.0129F, -31.9857F, 15.2957F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(654, 468).addBox(-4.0129F, -22.9857F, -5.7043F, 9.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(192, 637).addBox(-4.0129F, -29.9857F, 3.2957F, 9.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(686, 615).addBox(-4.0129F, -35.9857F, 15.2957F, 9.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(388, 599).addBox(-4.0129F, -40.9857F, 33.2957F, 9.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(394, 396).addBox(-4.0129F, -40.9857F, 29.2957F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(668, 337).addBox(-2.0129F, -6.9857F, -24.7043F, 5.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(394, 530).addBox(-2.0129F, -3.9857F, -26.7043F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(381, 274).addBox(-2.0129F, -11.9857F, -18.7043F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 650).addBox(-2.0129F, -17.9857F, -14.7043F, 5.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(134, 635).addBox(-2.0129F, -24.9857F, -9.7043F, 5.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(581, 626).addBox(-2.0129F, -31.9857F, -0.7043F, 5.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(696, 84).addBox(-2.0129F, -37.9857F, 12.2957F, 5.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(552, 618).addBox(-2.0129F, -39.9857F, 26.2957F, 5.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(312, 353).addBox(-2.0129F, -31.9857F, 16.2957F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(480, 44).addBox(-3.0129F, -30.9857F, 18.2957F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(604, 268).addBox(-3.0129F, -37.9857F, 41.2957F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(686, 555).addBox(-3.0129F, -38.9857F, 28.2957F, 7.0F, 7.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(70, 660).addBox(-3.0129F, -36.9857F, 14.2957F, 7.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(604, 297).addBox(-3.0129F, -30.9857F, 1.2957F, 7.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(352, 623).addBox(-3.0129F, -23.9857F, -7.7043F, 7.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(626, 12).addBox(-3.0129F, -16.9857F, -12.7043F, 7.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(520, 372).addBox(-3.0129F, -10.9857F, -16.7043F, 7.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(628, 246).addBox(-3.0129F, -4.9857F, -22.7043F, 7.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0129F, -0.0143F, -118.2957F));

        PartDefinition panel6 = customroot.addOrReplaceChild("panel6", CubeListBuilder.create().texOffs(424, 795).addBox(-2.0F, -23.0F, -13.0F, 4.0F, 46.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(428, 240).addBox(-20.0F, -4.0F, -16.0F, 40.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(242, 333).addBox(-4.0F, -19.0F, -16.0F, 8.0F, 38.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(106, 871).addBox(-24.0F, -2.0F, -13.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(110, 330).addBox(-2.0F, -31.0F, -5.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(73, 905).addBox(-32.0F, -2.0F, -5.0F, 64.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(427, 854).addBox(-4.0F, -27.0F, -8.0F, 8.0F, 54.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(678, 891).addBox(-28.0F, -4.0F, -8.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 330).addBox(-19.0F, -18.0F, -15.0F, 38.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 513).addBox(-15.0F, -14.0F, -19.0F, 30.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(144, 142).addBox(-27.0F, -18.0F, -7.0F, 54.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(168, 761).addBox(-19.0F, -26.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(420, 760).addBox(-19.0F, 18.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(143.0F, -64.0F, 72.0F, 0.1745F, -0.3491F, 0.0F));

        PartDefinition panel5 = customroot.addOrReplaceChild("panel5", CubeListBuilder.create().texOffs(649, 718).addBox(-2.0F, -23.0F, -13.0F, 4.0F, 46.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 398).addBox(-20.0F, -4.0F, -16.0F, 40.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(580, 268).addBox(-4.0F, -19.0F, -16.0F, 8.0F, 38.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(100, 854).addBox(-24.0F, -2.0F, -13.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(396, 313).addBox(-2.0F, -31.0F, -5.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(81, 834).addBox(-32.0F, -2.0F, -5.0F, 64.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(591, 809).addBox(-4.0F, -27.0F, -8.0F, 8.0F, 54.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(682, 859).addBox(-28.0F, -4.0F, -8.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(142, 338).addBox(-19.0F, -18.0F, -15.0F, 38.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(276, 538).addBox(-15.0F, -14.0F, -19.0F, 30.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(148, 0).addBox(-27.0F, -18.0F, -7.0F, 54.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(171, 740).addBox(-19.0F, -26.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(552, 730).addBox(-19.0F, 18.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(132.0F, -162.0F, 72.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition panel4 = customroot.addOrReplaceChild("panel4", CubeListBuilder.create().texOffs(405, 796).addBox(-2.0F, -23.0F, -13.0F, 4.0F, 46.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 414).addBox(-20.0F, -4.0F, -16.0F, 40.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(580, 314).addBox(-4.0F, -19.0F, -16.0F, 8.0F, 38.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(297, 780).addBox(-24.0F, -2.0F, -13.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(92, 418).addBox(-2.0F, -31.0F, -5.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 822).addBox(-32.0F, -2.0F, -5.0F, 64.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(546, 804).addBox(-4.0F, -27.0F, -8.0F, 8.0F, 54.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(683, 817).addBox(-28.0F, -4.0F, -8.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(346, 0).addBox(-19.0F, -18.0F, -15.0F, 38.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(344, 538).addBox(-15.0F, -14.0F, -19.0F, 30.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(148, 44).addBox(-27.0F, -18.0F, -7.0F, 54.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(830, 699).addBox(-19.0F, -26.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(834, 679).addBox(-19.0F, 18.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(56.0F, -240.0F, 72.0F, -0.1745F, -0.1745F, 0.0F));

        PartDefinition panel = customroot.addOrReplaceChild("panel", CubeListBuilder.create().texOffs(514, 799).addBox(-2.0F, -23.0F, -13.0F, 4.0F, 46.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(284, 458).addBox(-20.0F, -4.0F, -16.0F, 40.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(64, 582).addBox(-4.0F, -19.0F, -16.0F, 8.0F, 38.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(354, 947).addBox(-24.0F, -2.0F, -13.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(534, 398).addBox(-2.0F, -31.0F, -5.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 998).addBox(-32.0F, -2.0F, -5.0F, 64.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(564, 946).addBox(-4.0F, -27.0F, -8.0F, 8.0F, 54.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(628, 990).addBox(-28.0F, -4.0F, -8.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 418).addBox(-19.0F, -18.0F, -15.0F, 38.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(202, 550).addBox(-15.0F, -14.0F, -19.0F, 30.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 286).addBox(-27.0F, -18.0F, -7.0F, 54.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(843, 812).addBox(-19.0F, -26.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(827, 736).addBox(-19.0F, 18.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-143.0F, -64.0F, 72.0F, 0.1745F, 0.3491F, 0.0F));

        PartDefinition panel2 = customroot.addOrReplaceChild("panel2", CubeListBuilder.create().texOffs(495, 796).addBox(-2.0F, -23.0F, -13.0F, 4.0F, 46.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 446).addBox(-20.0F, -4.0F, -16.0F, 40.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 582).addBox(-4.0F, -19.0F, -16.0F, 8.0F, 38.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(88, 965).addBox(-24.0F, -2.0F, -13.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(750, 555).addBox(-2.0F, -31.0F, -5.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(271, 960).addBox(-32.0F, -2.0F, -5.0F, 64.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(519, 948).addBox(-4.0F, -27.0F, -8.0F, 8.0F, 54.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(678, 943).addBox(-28.0F, -4.0F, -8.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(284, 406).addBox(-19.0F, -18.0F, -15.0F, 38.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(134, 550).addBox(-15.0F, -14.0F, -19.0F, 30.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 763).addBox(-27.0F, -18.0F, -7.0F, 54.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(310, 725).addBox(-19.0F, -26.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(550, 750).addBox(-19.0F, 18.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-132.0F, -162.0F, 72.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition panel3 = customroot.addOrReplaceChild("panel3", CubeListBuilder.create().texOffs(445, 795).addBox(-2.0F, -23.0F, -13.0F, 4.0F, 46.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 430).addBox(-20.0F, -4.0F, -16.0F, 40.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 582).addBox(-4.0F, -19.0F, -16.0F, 8.0F, 38.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(280, 904).addBox(-24.0F, -2.0F, -13.0F, 48.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(108, 418).addBox(-2.0F, -31.0F, -5.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(53, 950).addBox(-32.0F, -2.0F, -5.0F, 64.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 886).addBox(-4.0F, -27.0F, -8.0F, 8.0F, 54.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(328, 1008).addBox(-28.0F, -4.0F, -8.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(35, 741).addBox(-19.0F, 18.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(396, 44).addBox(-19.0F, -18.0F, -15.0F, 38.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 550).addBox(-15.0F, -14.0F, -19.0F, 30.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(144, 186).addBox(-27.0F, -18.0F, -7.0F, 54.0F, 36.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(164, 780).addBox(-19.0F, -26.0F, -7.0F, 38.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-56.0F, -240.0F, 72.0F, -0.1745F, 0.1745F, 0.0F));

        PartDefinition powerring = customroot.addOrReplaceChild("powerring", CubeListBuilder.create().texOffs(514, 0).addBox(78.0F, -18.0F, 0.0F, 12.0F, 36.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(362, 72).addBox(66.0F, 18.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(428, 152).addBox(42.0F, 54.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 230).addBox(54.0F, 30.0F, 0.0F, 12.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(510, 512).addBox(18.0F, 66.0F, 0.0F, 24.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(624, 580).addBox(18.0F, -78.0F, 0.0F, 24.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(342, 132).addBox(54.0F, -54.0F, 0.0F, 12.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(428, 195).addBox(42.0F, -66.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(142, 438).addBox(66.0F, -30.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 637).addBox(-78.0F, -30.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(635, 298).addBox(-54.0F, -66.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(344, 353).addBox(-66.0F, -54.0F, 0.0F, 12.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(628, 454).addBox(-42.0F, -78.0F, 0.0F, 24.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(562, 108).addBox(-18.0F, -90.0F, 0.0F, 36.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(491, 211).addBox(-18.0F, 78.0F, 0.0F, 36.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(626, 88).addBox(-42.0F, 66.0F, 0.0F, 24.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(342, 184).addBox(-66.0F, 30.0F, 0.0F, 12.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(382, 623).addBox(-54.0F, 54.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 628).addBox(-78.0F, 18.0F, 0.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(636, 208).addBox(-90.0F, -18.0F, 0.0F, 12.0F, 36.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -108.0F, 72.0F));

        PartDefinition minigun = customroot.addOrReplaceChild("minigun", CubeListBuilder.create().texOffs(302, 258).addBox(-39.7487F, -30.1521F, -24.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(241, 313).addBox(-35.7487F, -32.1521F, -25.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(286, 258).addBox(-47.7487F, -34.1521F, -24.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(84, 330).addBox(-37.7487F, -25.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(314, 258).addBox(-40.7487F, -25.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(276, 322).addBox(-38.7487F, -27.1521F, -24.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(274, 274).addBox(-40.7487F, -33.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(324, 258).addBox(-37.7487F, -33.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(144, 142).addBox(-38.7487F, -35.1521F, -24.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(286, 229).addBox(-41.7487F, -35.1521F, -24.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(290, 45).addBox(-33.7487F, -34.1521F, -24.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(290, 37).addBox(-51.7487F, -32.1521F, -25.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(612, 321).addBox(-51.7487F, -16.1521F, -25.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(616, 144).addBox(-51.7487F, 7.8479F, -25.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(278, 132).addBox(-41.7487F, -21.1521F, -24.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(134, 175).addBox(-41.7487F, -28.1521F, -24.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(134, 184).addBox(-34.7487F, -28.1521F, -24.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(274, 331).addBox(-30.7487F, -33.1521F, -24.0F, 3.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(274, 350).addBox(-47.7487F, -33.1521F, -24.0F, 3.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(572, 432).addBox(-51.7487F, -12.1521F, -25.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(238, 469).addBox(-26.7487F, -13.1521F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(118, 623).addBox(-50.7487F, 1.8479F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(320, 617).addBox(-26.7487F, 1.8479F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(398, 458).addBox(-50.7487F, -13.1521F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(635, 292).addBox(-50.7487F, 8.8479F, -29.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(626, 34).addBox(-50.7487F, -15.1521F, -29.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(252, 582).addBox(-27.7487F, -12.1521F, -25.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(624, 594).addBox(-61.7487F, -18.1521F, 31.0F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(412, 346).addBox(-65.7487F, -10.1521F, 31.0F, 4.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(99, 142).addBox(-3.7487F, -3.1521F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 23).addBox(-2.7487F, -1.1521F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 268).addBox(-23.7487F, -1.1521F, -6.0F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(284, 450).addBox(-21.7487F, -2.1521F, -2.0F, 22.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(142, 461).addBox(-65.7487F, 9.8479F, 31.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(572, 521).addBox(-65.7487F, -14.1521F, 31.0F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(572, 486).addBox(-49.7487F, -2.1521F, 60.0F, 24.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(482, 530).addBox(-49.7487F, -10.1521F, 52.0F, 24.0F, 30.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(438, 304).addBox(-53.7487F, -18.1521F, 46.0F, 32.0F, 32.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(666, 509).addBox(-51.7487F, -4.1521F, -21.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(200, 699).addBox(-27.7487F, -4.1521F, -21.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(108, 700).addBox(-27.7487F, -12.1521F, -7.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(606, 701).addBox(-51.7487F, -12.1521F, -7.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(622, 40).addBox(-51.7487F, 7.8479F, -7.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(612, 345).addBox(-51.7487F, -16.1521F, -7.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 342).addBox(-53.7487F, -18.1521F, -3.0F, 32.0F, 32.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(116, 286).addBox(-53.7487F, 3.8479F, 38.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(56, 582).addBox(-53.7487F, -12.1521F, 38.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 462).addBox(-53.7487F, 3.8479F, 7.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(174, 608).addBox(-53.7487F, -12.1521F, 7.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(199, 347).addBox(-55.7487F, -8.1521F, 7.0F, 4.0F, 12.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(428, 132).addBox(-55.7487F, 7.8479F, 7.0F, 4.0F, 8.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(142, 418).addBox(-55.7487F, -20.1521F, 7.0F, 4.0F, 8.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(0, 142).addBox(-51.7487F, -20.1521F, 7.0F, 32.0F, 36.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(843, 550).addBox(-43.2513F, -23.1521F, -5.0F, 10.0F, 7.0F, 42.0F, new CubeDeformation(0.0F))
                .texOffs(562, 76).addBox(-51.7487F, -16.1521F, 42.0F, 28.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(418, 568).addBox(-51.7487F, -16.1521F, 3.0F, 28.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-31.0F, -116.0F, -19.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition barrel = minigun.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(714, 264).addBox(6.0F, 1.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(268, 792).addBox(6.0F, -2.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(438, 134).addBox(4.0F, -0.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(706, 402).addBox(3.0F, -4.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(714, 132).addBox(3.0F, -8.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(420, 0).addBox(1.0F, -6.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(678, 666).addBox(3.0F, 7.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(0, 682).addBox(3.0F, 3.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(142, 406).addBox(1.0F, 5.1F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(544, 664).addBox(-5.0F, 7.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(134, 670).addBox(-5.0F, 3.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(296, 396).addBox(-7.0F, 5.1F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(686, 534).addBox(-5.0F, -4.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(696, 0).addBox(-5.0F, -8.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(0, 418).addBox(-7.0F, -6.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(402, 794).addBox(-8.0F, 1.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(536, 796).addBox(-8.0F, -2.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(438, 266).addBox(-10.0F, -0.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(612, 353).addBox(-13.0F, -12.9F, -101.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(632, 379).addBox(-13.0F, 11.1F, -101.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(449, 175).addBox(-13.0F, -10.9F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(118, 612).addBox(11.0F, 4.1F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(118, 601).addBox(-13.0F, 4.1F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(428, 175).addBox(11.0F, -10.9F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(612, 337).addBox(-14.0F, -13.9F, -97.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(616, 171).addBox(-14.0F, 10.1F, -97.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(92, 700).addBox(-14.0F, -9.9F, -97.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(76, 700).addBox(10.0F, -9.9F, -97.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(685, 447).addBox(10.0F, -9.9F, -146.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(112, 656).addBox(-14.0F, -9.9F, -146.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(616, 163).addBox(-14.0F, 10.1F, -146.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(612, 329).addBox(-14.0F, -13.9F, -146.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(354, 593).addBox(-13.0F, -12.9F, -150.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(628, 422).addBox(-13.0F, 11.1F, -150.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(428, 132).addBox(-13.0F, -10.9F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(346, 570).addBox(11.0F, 4.1F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(325, 570).addBox(-13.0F, 4.1F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(185, 426).addBox(11.0F, -10.9F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(829, 550).addBox(-10.5026F, -10.9F, -98.0F, 22.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(829, 550).addBox(-10.5026F, -10.9F, -147.0F, 22.0F, 22.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-37.7487F, -2.2521F, 35.0F));

        PartDefinition minigun2 = customroot.addOrReplaceChild("minigun2", CubeListBuilder.create().texOffs(116, 213).addBox(35.7487F, -30.1521F, -24.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(23.7487F, -32.1521F, -25.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(123, 154).addBox(41.7487F, -34.1521F, -24.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(130, 265).addBox(37.7487F, -25.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(130, 257).addBox(34.7487F, -25.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(24, 89).addBox(36.7487F, -27.1521F, -24.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(130, 257).addBox(34.7487F, -33.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(130, 273).addBox(37.7487F, -33.1521F, -24.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 71).addBox(36.7487F, -35.1521F, -24.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.5F))
                .texOffs(252, 109).addBox(33.7487F, -35.1521F, -24.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(286, 195).addBox(27.7487F, -34.1521F, -24.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(286, 221).addBox(39.7487F, -32.1521F, -25.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(185, 418).addBox(23.7487F, -16.1521F, -25.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(562, 122).addBox(23.7487F, 7.8479F, -25.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(252, 268).addBox(33.7487F, -21.1521F, -24.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(134, 54).addBox(40.7487F, -28.1521F, -24.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(134, 63).addBox(33.7487F, -28.1521F, -24.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 142).addBox(27.7487F, -33.1521F, -24.0F, 3.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(314, 52).addBox(44.7487F, -33.1521F, -24.0F, 3.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(400, 232).addBox(47.7487F, -12.1521F, -25.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(24.7487F, -13.1521F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(532, 225).addBox(48.7487F, 1.8479F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(118, 550).addBox(24.7487F, 1.8479F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(48.7487F, -13.1521F, -29.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 38).addBox(24.7487F, 8.8479F, -29.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(410, 124).addBox(24.7487F, -15.1521F, -29.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(514, 306).addBox(23.7487F, -12.1521F, -25.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(452, 611).addBox(55.7487F, -18.1521F, 31.0F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(412, 323).addBox(61.7487F, -10.1521F, 31.0F, 4.0F, 20.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(123, 142).addBox(1.7487F, -3.1521F, -3.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 31).addBox(1.7487F, -1.1521F, -5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(188, 268).addBox(2.7487F, -1.1521F, -6.0F, 21.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(495, 92).addBox(-0.2513F, -2.1521F, -2.0F, 22.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(368, 406).addBox(55.7487F, 9.8479F, 31.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(514, 283).addBox(57.7487F, -14.1521F, 31.0F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(572, 464).addBox(25.7487F, -2.1521F, 60.0F, 24.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(418, 530).addBox(25.7487F, -10.1521F, 52.0F, 24.0F, 30.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(438, 0).addBox(21.7487F, -18.1521F, 46.0F, 32.0F, 32.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(656, 70).addBox(47.7487F, -4.1521F, -21.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(664, 429).addBox(23.7487F, -4.1521F, -21.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(514, 339).addBox(23.7487F, -12.1521F, -7.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(544, 306).addBox(47.7487F, -12.1521F, -7.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(580, 386).addBox(23.7487F, 7.8479F, -7.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(206, 538).addBox(23.7487F, -16.1521F, -7.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 266).addBox(21.7487F, -18.1521F, -3.0F, 32.0F, 32.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(843, 550).addBox(32.7487F, -23.1521F, -5.0F, 10.0F, 7.0F, 42.0F, new CubeDeformation(0.0F))
                .texOffs(264, 0).addBox(51.7487F, 3.8479F, 38.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(551, 132).addBox(51.7487F, -12.1521F, 38.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(264, 44).addBox(51.7487F, 3.8479F, 7.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 582).addBox(51.7487F, -12.1521F, 7.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(198, 286).addBox(51.7487F, -8.1521F, 7.0F, 4.0F, 12.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(185, 426).addBox(51.7487F, 7.8479F, 7.0F, 4.0F, 8.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(341, 415).addBox(51.7487F, -20.1521F, 7.0F, 4.0F, 8.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(19.7487F, -20.1521F, 7.0F, 32.0F, 36.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(562, 12).addBox(23.7487F, -16.1521F, 42.0F, 28.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(562, 44).addBox(23.7487F, -16.1521F, 3.0F, 28.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(31.0F, -116.0F, -19.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition barrel2 = minigun2.addOrReplaceChild("barrel2", CubeListBuilder.create().texOffs(276, 660).addBox(-8.0F, 1.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(410, 662).addBox(-8.0F, -2.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(296, 264).addBox(-10.0F, -0.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(580, 136).addBox(-5.0F, -4.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(580, 268).addBox(-5.0F, -8.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(286, 132).addBox(-7.0F, -6.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(562, 2).addBox(-5.0F, 7.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(572, 400).addBox(-5.0F, 3.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(0, 286).addBox(-7.0F, 5.1F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(0, 550).addBox(3.0F, 7.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(552, 532).addBox(3.0F, 3.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(154, 274).addBox(1.0F, 5.1F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(418, 530).addBox(3.0F, -4.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(142, 538).addBox(3.0F, -8.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(148, 0).addBox(1.0F, -6.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(438, 398).addBox(6.0F, 1.1F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(284, 528).addBox(6.0F, -2.9F, -168.0F, 2.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(144, 142).addBox(4.0F, -0.9F, -168.0F, 6.0F, 2.0F, 130.0F, new CubeDeformation(0.0F))
                .texOffs(350, 124).addBox(-13.0F, -12.9F, -101.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(438, 388).addBox(-13.0F, 11.1F, -101.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(235, 230).addBox(11.0F, -10.9F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(555, 145).addBox(-13.0F, 4.1F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(556, 225).addBox(11.0F, 4.1F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(362, 52).addBox(-13.0F, -10.9F, -101.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(476, 120).addBox(-14.0F, -13.9F, -97.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(609, 192).addBox(-14.0F, 10.1F, -97.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(544, 339).addBox(10.0F, -9.9F, -97.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(482, 568).addBox(-14.0F, -9.9F, -146.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(552, 568).addBox(10.0F, -9.9F, -146.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(609, 200).addBox(-14.0F, 10.1F, -146.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(142, 538).addBox(-14.0F, -13.9F, -146.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(572, 400).addBox(-14.0F, -9.9F, -97.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(428, 256).addBox(-13.0F, -12.9F, -150.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(484, 86).addBox(-13.0F, 11.1F, -150.0F, 26.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(383, 52).addBox(11.0F, -10.9F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(556, 447).addBox(-13.0F, 4.1F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(514, 568).addBox(11.0F, 4.1F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(142, 418).addBox(-13.0F, -10.9F, -150.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(829, 550).addBox(-11.0F, -10.9F, -147.0F, 22.0F, 22.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(829, 550).addBox(-11.0F, -10.9F, -98.0F, 22.0F, 22.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(37.7487F, -2.2521F, 35.0F));

        return LayerDefinition.create(meshdefinition, 1024, 1024);
    }


    public void defineAnimations() {
        this.animations = new Animations(idleGrounded, idleTransition, patGrounded, sit, sitImpatient, sitPat, sleepingPose, deathLoop, deathStart, attackOne, attackTwo, attackThree, attackCounter, bowDraw, standingInspect, wetShake, viewFlower, swimLoop, interact, swimMove, snowballIdle, throwSnowball, snowballIdleTransition, patEmbarassed);
    }
    @Override
    public void attackAnim(Alte alte, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.animate(alte.hyperShootAnimState, hyperShoot, pAgeInTicks);
        if (!alte.hyperShootAnimState.isStarted()) {
            this.animate(alte.hyperStartAnimState, hyperStart, pAgeInTicks);
            this.animate(alte.hyperEndAnimState, hyperEnd, pAgeInTicks);
            this.animate(alte.hyperWindupAnimState, hyperWindup, pAgeInTicks);
            this.animate(alte.hyperRelaxAnimState, hyperRecovery, pAgeInTicks);
            this.animate(alte.sparkAnimState, spark, pAgeInTicks);
            boolean flag = alte.areAnimationsBusy();
            this.togglePanel(alte.sparkAnimState.isStarted());
            this.animate(alte.rodSummonAnimState, shockrodStart, pAgeInTicks);
            this.togglePowerRing(alte.rodSummonAnimState.isStarted() || alte.punisherAnimState.isStarted());
            this.animate(alte.rodSheathAnimState, shockrodEnd, pAgeInTicks);
            this.animate(alte.punisherAnimState, punisher, pAgeInTicks);


            if (!flag) {
                super.attackAnim(alte, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            }
        }
    }

    public void togglePanel(boolean b) {
        this.root().getChild("panel").visible = b;
    }

    public void togglePowerRing(boolean b) {
        this.root().getChild("powerring").visible = b;
    }

    public void bowAnim(Alte pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pEntity.getSyncInt(Alte.ALTE_SPARKCOUNTER) <= 0) {
            super.bowAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        }
    }

    public void patAnim(Alte pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (!pEntity.isUsingHyper()) {
            super.patAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        }
    }

    public void idleAnim(Alte alte, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (alte.isUsingHyper()) {
            if (!alte.areAnimationsBusy() && !alte.hyperShootAnimState.isStarted()) {
                animate(alte.hyperIdleAnimState, hyperIdle, pAgeInTicks);
            }
        } else {
            if (!alte.areAnimationsBusy()) {
                animate(alte.idleAnimState, this.animations.idlegrounded(), pAgeInTicks);
                animate(alte.inspectAnimState, this.animations.standinginspect(), pAgeInTicks);
                animate(alte.idleAnimStartState, this.animations.idletransition(), pAgeInTicks);
                animate(alte.snowballIdleAnimState, this.animations.snowballIdle(), pAgeInTicks);
                animate(alte.snowballIdleTransitionAnimState, this.animations.snowballIdleTransition(), pAgeInTicks);
            }
        }

    }

    public boolean shouldMoveHeadY(Alte friend) {
        return friend.getSyncInt(Alte.ALTE_SPARKCOUNTER) <= 0;
    }

    public boolean shouldMoveHeadX(Alte friend) {
        return !friend.isUsingHyper() && friend.getSyncInt(Alte.ALTE_SPARKCOUNTER) <= 0;
    }

    @Override
    public void setupAnim(Alte alt, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(alt, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if (alt.isUsingHyper()) {
            this.root().yRot = this.parts.head().yRot;
            this.parts.head().yRot = 0;
        }
        this.toggleArsenal(alt.isUsingHyper());
        this.toggleShockRod(alt.isUsingShockRod());
    }

    public void toggleArsenal(boolean b) {
        this.root().getChild("panel").visible = b;
        this.root().getChild("panel2").visible = b;
        this.root().getChild("panel3").visible = b;
        this.root().getChild("panel4").visible = b;
        this.root().getChild("panel5").visible = b;
        this.root().getChild("panel6").visible = b;
        this.root().getChild("powerring").visible = b;
        this.root().getChild("minigun").visible = b;
        this.root().getChild("minigun2").visible = b;
    }

    public void toggleShockRod(boolean b) {
        this.parts.rightarm().getChild("lowerarm").getChild("grabber").visible = b;
    }

}