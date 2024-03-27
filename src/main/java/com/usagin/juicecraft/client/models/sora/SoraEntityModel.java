package com.usagin.juicecraft.client.models.sora;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.usagin.juicecraft.client.animation.SoraAnimation1.*;
import static com.usagin.juicecraft.client.animation.SoraAnimation2.*;
import static com.usagin.juicecraft.client.animation.SoraAnimation3.*;

public class SoraEntityModel extends FriendEntityModel<Sora> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JuiceCraft.MODID, "soraentitymodel"), "main");

    public SoraEntityModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition customroot = partdefinition.addOrReplaceChild("customroot", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hip = customroot.addOrReplaceChild("hip", CubeListBuilder.create().texOffs(105, 131).addBox(-16.0F, -81.0F, -4.0F, 32.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = hip.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(358, 76).addBox(-4.5F, 0.0F, -10.0F, 5.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -79.0F, 4.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition cube_r2 = hip.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(132, 404).addBox(-0.5F, 0.0F, -10.0F, 5.0F, 8.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, -79.0F, 4.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition cube_r3 = hip.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(325, 184).addBox(-18.0F, 0.0F, -2.75F, 36.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -79.0F, 14.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r4 = hip.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 346).addBox(-18.0F, 0.0F, -1.25F, 36.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -79.0F, -5.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition butt = hip.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(75, 0).addBox(-19.0F, 0.0F, 2.0F, 38.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -71.0F, -7.0F));

        PartDefinition rightleg = butt.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(65, 293).addBox(-8.0F, 0.0F, -8.0F, 15.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 11.0F, 11.0F));

        PartDefinition lowerleg2 = rightleg.addOrReplaceChild("lowerleg2", CubeListBuilder.create().texOffs(71, 242).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 36.0F, 14.0F, new CubeDeformation(0.01F))
                .texOffs(258, 331).addBox(-8.0F, 13.0F, -9.0F, 16.0F, 8.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(162, 200).addBox(-7.0F, 32.0F, -9.0F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(243, 286).addBox(-8.0F, 23.0F, 0.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leftleg = butt.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(270, 286).addBox(-7.0F, 0.0F, -8.0F, 15.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, 11.0F, 11.0F));

        PartDefinition lowerleg = leftleg.addOrReplaceChild("lowerleg", CubeListBuilder.create().texOffs(128, 242).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 36.0F, 14.0F, new CubeDeformation(0.01F))
                .texOffs(171, 189).addBox(-7.0F, 32.0F, -9.0F, 14.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(326, 17).addBox(-8.0F, 13.0F, -9.0F, 16.0F, 8.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(112, 293).addBox(-8.0F, 23.0F, 0.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition weaponholster = hip.addOrReplaceChild("weaponholster", CubeListBuilder.create(), PartPose.offset(-23.0F, -68.0F, 32.0F));

        PartDefinition waist = hip.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(205, 242).addBox(-14.0F, -18.0F, -8.0F, 28.0F, 26.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(47, 387).addBox(-11.0F, -18.0F, 0.0F, 22.0F, 26.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(100, 394).addBox(-11.0F, -18.0F, -12.0F, 22.0F, 26.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -84.0F, 8.0F));

        PartDefinition chest = waist.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 131).addBox(-15.5F, -18.0F, -17.0F, 31.0F, 18.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(307, 426).addBox(2.5F, -11.5F, -21.0F, 13.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(345, 467).addBox(-2.5F, -8.5F, -21.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(35, 371).addBox(-15.5F, -11.5F, -21.0F, 13.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(346, 57).addBox(-15.0F, -18.0F, 2.0758F, 30.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(97, 77).addBox(-15.0F, -20.0F, -16.0F, 30.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(317, 295).addBox(-15.25F, -19.5F, -18.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(307, 17).addBox(8.25F, -19.5F, -18.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 3.0F));

        PartDefinition cube_r5 = chest.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(346, 295).addBox(-16.0F, -1.75F, 25.0F, 32.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -23.0758F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r6 = chest.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(381, 169).addBox(-6.5F, -9.5F, -6.25F, 13.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -15.5F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r7 = chest.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(216, 166).addBox(6.0F, -11.75F, -7.0F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -15.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r8 = chest.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(89, 189).addBox(-2.0F, -6.75F, -6.75F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(451, 204).addBox(2.0F, -7.75F, -7.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(16, 264).addBox(1.0F, -4.75F, -7.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(398, 0).addBox(-2.0F, -4.75F, -7.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(429, 426).addBox(6.0F, -4.75F, -7.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 446).addBox(-10.0F, -4.75F, -7.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(180, 429).addBox(-6.0F, -7.75F, -7.5F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(171, 242).addBox(-15.0F, -11.75F, -7.5F, 9.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -15.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(374, 242).addBox(-6.0F, -16.0F, -6.0F, 12.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -7.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(296, 115).addBox(-20.0F, -16.0F, 4.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 189).addBox(-20.0F, -16.0F, 0.0F, 40.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 71).addBox(-16.0F, -27.0F, -16.0F, 32.0F, 27.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(108, 38).addBox(6.25F, -28.0F, -19.0F, 12.0F, 5.0F, 33.0F, new CubeDeformation(0.0F))
                .texOffs(296, 128).addBox(-18.25F, -14.0F, -8.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(306, 57).addBox(-18.25F, -17.0F, -12.0F, 6.0F, 3.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(128, 166).addBox(-18.25F, -20.0F, -15.0F, 6.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(75, 32).addBox(-18.25F, -23.0F, -15.0F, 2.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(289, 23).addBox(-18.25F, -28.0F, -15.0F, 4.0F, 5.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(75, 166).addBox(9.25F, -23.0F, -19.0F, 9.0F, 3.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(216, 51).addBox(12.25F, -20.0F, -19.0F, 6.0F, 3.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(306, 300).addBox(12.25F, -17.0F, -12.0F, 6.0F, 3.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(385, 115).addBox(12.25F, -14.0F, -8.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(216, 0).addBox(-9.5F, -35.5F, -11.0F, 19.0F, 6.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(346, 312).addBox(-7.25F, -3.5F, -6.25F, 13.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(322, 389).addBox(-10.25F, -1.5F, -8.25F, 16.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -32.0F, -9.25F, 0.3153F, 0.4733F, 0.6935F));

        PartDefinition cube_r10 = head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(361, 154).addBox(-5.75F, -0.5F, -8.25F, 16.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(335, 88).addBox(-5.75F, -2.5F, -6.25F, 13.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, -32.0F, -9.25F, 0.3932F, -0.4125F, -0.8747F));

        PartDefinition cube_r11 = head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(425, 192).addBox(-24.5F, -13.5F, -11.75F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, -16.25F, -0.7968F, 0.1229F, -0.0002F));

        PartDefinition cube_r12 = head.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(366, 0).addBox(-26.75F, -7.75F, -11.75F, 8.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, -16.25F, -0.7873F, 0.185F, 0.0618F));

        PartDefinition cube_r13 = head.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(104, 425).addBox(-15.25F, -25.0F, 2.5F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5F, -15.5F, -13.0F, 0.0826F, 0.0594F, 1.1778F));

        PartDefinition cube_r14 = head.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(164, 293).addBox(10.0F, -18.25F, -2.0F, 6.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -15.5F, -13.0F, -0.2184F, 0.0393F, -0.9984F));

        PartDefinition cube_r15 = head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(29, 0).addBox(-13.5F, -12.0F, -3.0F, 6.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.25F, -15.0F, -13.0F, -0.2067F, -0.0815F, 0.605F));

        PartDefinition cube_r16 = head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(445, 28).addBox(-21.75F, -7.0F, 15.0F, 6.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -15.5F, -13.0F, 0.3836F, 0.1355F, 1.2074F));

        PartDefinition cube_r17 = head.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(87, 446).addBox(-21.75F, -7.75F, 15.0F, 6.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -15.5F, -13.0F, 0.3591F, 0.3781F, 0.8208F));

        PartDefinition cube_r18 = head.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(256, 408).addBox(-11.75F, -18.25F, 8.0F, 6.0F, 11.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -15.5F, -13.0F, 0.0F, 0.0F, 0.8011F));

        PartDefinition cube_r19 = head.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(171, 166).addBox(-16.0F, -18.25F, -2.0F, 6.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -15.5F, -13.0F, -0.2184F, -0.0393F, 0.9984F));

        PartDefinition cube_r20 = head.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(205, 408).addBox(5.75F, -17.75F, 8.0F, 6.0F, 11.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -15.5F, -13.0F, 0.0F, 0.0F, -0.8011F));

        PartDefinition cube_r21 = head.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(171, 0).addBox(-15.75F, -24.25F, -8.75F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5F, -15.5F, 5.0F, -0.262F, -0.1349F, 1.2214F));

        PartDefinition cube_r22 = head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(444, 107).addBox(15.75F, -7.75F, 15.0F, 6.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -15.5F, -13.0F, 0.3591F, -0.3781F, -0.8208F));

        PartDefinition cube_r23 = head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(443, 143).addBox(15.75F, -7.0F, 15.0F, 6.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -15.5F, -13.0F, 0.3836F, -0.1355F, -1.2074F));

        PartDefinition cube_r24 = head.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(164, 398).addBox(9.75F, -24.25F, -8.75F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -15.5F, 5.0F, -0.262F, 0.1349F, -1.2214F));

        PartDefinition cube_r25 = head.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(84, 131).addBox(9.25F, -25.0F, 2.5F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -15.5F, -13.0F, 0.0826F, -0.0594F, -1.1778F));

        PartDefinition cube_r26 = head.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(444, 70).addBox(7.5F, -12.0F, -3.0F, 6.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.25F, -15.0F, -13.0F, -0.2067F, 0.0815F, -0.605F));

        PartDefinition cube_r27 = head.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(137, 332).addBox(-19.5F, -8.75F, 12.5F, 22.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 8.25F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r28 = head.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(374, 271).addBox(-18.5F, -16.5F, -13.25F, 21.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, -16.25F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r29 = head.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(354, 44).addBox(-17.75F, -14.25F, -8.0F, 23.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, -16.25F, -0.4363F, 0.0F, 0.0F));

        PartDefinition backhair = head.addOrReplaceChild("backhair", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -26.0F, 16.75F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r30 = backhair.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(75, 32).addBox(8.25F, -11.0F, 11.0F, 3.0F, 20.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.3878F, 29.3473F, -21.5F, -0.1726F, -0.4658F, -0.1525F));

        PartDefinition cube_r31 = backhair.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(205, 277).addBox(-11.5F, 6.0F, 15.0F, 2.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.3878F, 29.3473F, -21.5F, -0.3241F, 0.4523F, 0.1062F));

        PartDefinition cube_r32 = backhair.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 71).addBox(-11.25F, -11.0F, 11.0F, 3.0F, 20.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.3878F, 29.3473F, -21.5F, -0.1726F, 0.4658F, 0.1525F));

        PartDefinition cube_r33 = backhair.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(53, 24).addBox(-6.25F, -3.0F, 22.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.3878F, 5.3473F, -20.75F, 0.2099F, 0.3817F, 0.3547F));

        PartDefinition cube_r34 = backhair.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(131, 32).addBox(-5.25F, 14.0F, 25.0F, 2.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.3878F, 5.3473F, -20.75F, 0.079F, 0.3817F, 0.3547F));

        PartDefinition cube_r35 = backhair.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(25, 71).addBox(-5.0F, 24.0F, 35.0F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.3878F, 5.3473F, -20.75F, -0.2265F, 0.3817F, 0.3547F));

        PartDefinition cube_r36 = backhair.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(432, 241).addBox(-7.25F, -8.0F, 12.0F, 4.0F, 25.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.3878F, 5.3473F, -20.75F, 0.0181F, 0.3923F, 0.309F));

        PartDefinition cube_r37 = backhair.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(0, 281).addBox(9.5F, 6.0F, 15.0F, 2.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.3878F, 29.3473F, -21.5F, -0.3241F, -0.4523F, -0.1062F));

        PartDefinition cube_r38 = backhair.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(237, 408).addBox(-20.0F, 18.0F, -0.75F, 14.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(425, 174).addBox(-3.0F, 17.0F, 3.25F, 20.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(256, 157).addBox(-17.0F, 17.0F, 3.25F, 10.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(345, 408).addBox(-4.0F, 18.0F, -0.75F, 24.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(205, 370).addBox(-12.0F, -6.0F, 3.25F, 25.0F, 23.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(129, 102).addBox(-16.0F, -6.0F, -0.75F, 32.0F, 24.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.3473F, 0.6378F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r39 = backhair.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(100, 32).addBox(4.0F, 24.0F, 35.0F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.3878F, 5.3473F, -20.75F, -0.2265F, -0.3817F, -0.3547F));

        PartDefinition cube_r40 = backhair.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(166, 32).addBox(3.25F, 14.0F, 25.0F, 2.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.3878F, 5.3473F, -20.75F, 0.079F, -0.3817F, -0.3547F));

        PartDefinition cube_r41 = backhair.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(360, 331).addBox(3.25F, -3.0F, 22.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.3878F, 5.3473F, -20.75F, 0.2099F, -0.3817F, -0.3547F));

        PartDefinition cube_r42 = backhair.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(432, 277).addBox(3.25F, -8.0F, 12.0F, 4.0F, 25.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.3878F, 5.3473F, -20.75F, 0.0181F, -0.3923F, -0.309F));

        PartDefinition backmidhair = backhair.addOrReplaceChild("backmidhair", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 36.3473F, 6.6378F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r43 = backmidhair.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(216, 33).addBox(-23.0F, 30.0F, 5.25F, 46.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 171).addBox(-25.0F, 31.0F, 1.25F, 50.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(432, 331).addBox(-22.0F, 18.0F, 1.25F, 19.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(432, 313).addBox(1.0F, 17.0F, 5.25F, 19.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(309, 331).addBox(-20.0F, 17.0F, 5.25F, 15.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(288, 408).addBox(-2.0F, 18.0F, 1.25F, 24.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, -6.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition backlowhair = backmidhair.addOrReplaceChild("backlowhair", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 25.0F, 4.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r44 = backlowhair.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(214, 446).addBox(-33.0F, 31.0F, 1.25F, 15.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(216, 148).addBox(-15.0F, 31.0F, 1.25F, 11.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(205, 342).addBox(4.0F, 30.0F, 5.25F, 23.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(326, 17).addBox(-12.0F, 30.0F, 5.25F, 5.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(358, 446).addBox(-29.0F, 30.0F, 5.25F, 8.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(282, 368).addBox(1.0F, 31.0F, 1.25F, 31.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(410, 408).addBox(-29.0F, 18.0F, 1.25F, 24.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 418).addBox(0.0F, 17.0F, 5.25F, 25.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(124, 446).addBox(-26.0F, 17.0F, 5.25F, 18.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(77, 369).addBox(-3.0F, 18.0F, 1.25F, 31.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, -6.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition backendhair = backlowhair.addOrReplaceChild("backendhair", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 25.0F, 3.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r45 = backendhair.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(353, 368).addBox(-28.0F, 31.0F, 2.25F, 7.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(187, 131).addBox(-12.0F, 31.0F, 2.25F, 8.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(149, 166).addBox(8.0F, 41.0F, 2.25F, 3.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(403, 106).addBox(11.0F, 46.0F, 1.25F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(205, 317).addBox(11.0F, 31.0F, 2.25F, 6.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 302).addBox(25.0F, 31.0F, 2.25F, 8.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(216, 51).addBox(-29.0F, 18.0F, 1.25F, 12.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 256).addBox(-11.0F, 18.0F, 2.25F, 11.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(375, 459).addBox(6.0F, 18.0F, 1.25F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(306, 57).addBox(12.0F, 18.0F, 2.25F, 8.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 321).addBox(22.0F, 17.0F, 5.25F, 7.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(185, 278).addBox(12.0F, 17.0F, 5.25F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(10.0F, 17.0F, 5.25F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(184, 351).addBox(-9.0F, 17.0F, 5.25F, 5.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(108, 131).addBox(-27.0F, 17.0F, 5.25F, 5.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(175, 446).addBox(22.0F, 18.0F, 1.25F, 15.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, -6.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition bangs = head.addOrReplaceChild("bangs", CubeListBuilder.create().texOffs(353, 380).addBox(-7.4884F, 4.3713F, -3.5035F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -16.25F, -0.0436F, 0.0F, -0.0436F));

        PartDefinition cube_r46 = bangs.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(253, 446).addBox(-28.75F, -16.5F, 1.75F, 7.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 19.0F, -11.25F, 0.0312F, 0.2598F, 0.1354F));

        PartDefinition cube_r47 = bangs.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(11, 281).addBox(-4.25F, -5.5F, -3.75F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 11.0F, 0.0F, 0.0001F, -0.0057F, -0.0433F));

        PartDefinition cube_r48 = bangs.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(318, 276).addBox(-7.25F, -8.0F, -4.25F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 11.0F, 0.0F, -0.0871F, -0.0057F, -0.0433F));

        PartDefinition cube_r49 = bangs.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(425, 161).addBox(-9.25F, -12.0F, -4.5F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 11.0F, 0.0F, -0.1308F, -0.0057F, -0.0433F));

        PartDefinition cube_r50 = bangs.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(395, 35).addBox(-17.5F, -10.5F, -4.5F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 11.0F, 0.0F, -0.1308F, 0.0057F, 0.0433F));

        PartDefinition cube_r51 = bangs.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(16, 71).addBox(-24.0F, 2.25F, 2.25F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(264, 370).addBox(-26.25F, -4.75F, 2.25F, 5.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 19.0F, -11.25F, 0.1068F, 0.2609F, 0.0902F));

        PartDefinition cube_r52 = bangs.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(16, 0).addBox(-29.5F, -5.5F, 2.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 18.0F, -21.25F, 0.0813F, 0.803F, -0.0619F));

        PartDefinition cube_r53 = bangs.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(0, 71).addBox(-31.0F, -7.75F, 2.25F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 18.0F, -21.25F, 0.171F, 0.7893F, 0.1244F));

        PartDefinition cube_r54 = bangs.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(29, 0).addBox(-31.5F, -15.25F, 1.75F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -21.25F, 0.1682F, 0.7708F, 0.2444F));

        PartDefinition cube_r55 = bangs.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(75, 32).addBox(-10.0F, 3.75F, -4.15F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5116F, 11.3713F, 0.4965F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r56 = bangs.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(54, 0).addBox(-12.0F, 1.25F, -4.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5116F, 11.3713F, 0.4965F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r57 = bangs.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(112, 302).addBox(-14.0F, -2.25F, -4.0F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5116F, 11.3713F, 0.4965F, 0.0436F, 0.0F, 0.0F));

        PartDefinition rightahoge = head.addOrReplaceChild("rightahoge", CubeListBuilder.create(), PartPose.offset(-20.0F, -28.75F, -13.75F));

        PartDefinition cube_r58 = rightahoge.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(140, 32).addBox(-16.75F, 3.0F, -2.25F, 9.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(296, 140).addBox(-19.75F, 4.0F, -3.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 6.75F, -2.5F, 0.5437F, -0.1501F, -1.4006F));

        PartDefinition cube_r59 = rightahoge.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(270, 242).addBox(-7.75F, 3.0F, -5.25F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(342, 426).addBox(-11.75F, 4.0F, -6.25F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 6.75F, -2.5F, 0.5376F, -0.1726F, -1.3627F));

        PartDefinition cube_r60 = rightahoge.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(286, 358).addBox(-1.75F, 3.5F, -7.25F, 12.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 242).addBox(-1.75F, 4.5F, -9.25F, 12.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 6.75F, -2.5F, 0.4821F, -0.3009F, -1.1277F));

        PartDefinition cube_r61 = rightahoge.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(84, 131).addBox(-10.75F, 6.0F, 4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 6.75F, -2.5F, 0.2891F, -0.2219F, -0.8036F));

        PartDefinition cube_r62 = rightahoge.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(265, 295).addBox(-7.75F, 4.75F, 4.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 6.75F, -2.5F, 0.3222F, -0.1692F, -0.9733F));

        PartDefinition cube_r63 = rightahoge.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(263, 78).addBox(-1.75F, 4.5F, 3.75F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 6.75F, -2.5F, 0.3351F, -0.141F, -1.0569F));

        PartDefinition leftahoge = head.addOrReplaceChild("leftahoge", CubeListBuilder.create(), PartPose.offsetAndRotation(20.0F, -28.75F, -13.75F, 0.1745F, -0.1745F, 0.0F));

        PartDefinition cube_r64 = leftahoge.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(216, 80).addBox(-1.0F, -2.25F, -3.25F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(243, 295).addBox(0.75F, -1.25F, -4.25F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8785F, 5.6676F, -2.41F, 0.3864F, 0.3085F, 0.5124F));

        PartDefinition cube_r65 = leftahoge.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(65, 204).addBox(-3.75F, -2.25F, -8.25F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8785F, 5.6676F, -2.41F, 0.5285F, 0.2327F, 0.5761F));

        PartDefinition cube_r66 = leftahoge.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(166, 67).addBox(-10.25F, -2.5F, -9.75F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8785F, 5.6676F, -2.41F, 0.6148F, 0.5874F, 0.7673F));

        PartDefinition cube_r67 = leftahoge.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(281, 17).addBox(-10.25F, -3.0F, -5.25F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(216, 187).addBox(-10.25F, -1.5F, -6.25F, 12.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.8785F, 5.6676F, -2.41F, 0.4462F, 0.4733F, 0.6935F));

        PartDefinition rightsidelong = head.addOrReplaceChild("rightsidelong", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 16.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(185, 255).addBox(-1.0F, -2.0F, -7.0F, 2.0F, 17.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.25F, -20.0F, -10.0F, -0.0058F, -0.218F, 0.0445F));

        PartDefinition cube_r68 = rightsidelong.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(21, 242).addBox(-3.25F, 12.0F, -12.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0115F, -0.1304F, -0.088F));

        PartDefinition lowerrightlong = rightsidelong.addOrReplaceChild("lowerrightlong", CubeListBuilder.create().texOffs(249, 92).addBox(-3.0F, 0.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(189, 316).addBox(-1.0F, 1.0F, -6.0F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(284, 57).addBox(-1.0F, 10.0F, -3.0F, 2.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 1.0F, 0.0435F, 0.0038F, -0.0872F));

        PartDefinition leftsidelong = head.addOrReplaceChild("leftsidelong", CubeListBuilder.create().texOffs(216, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 16.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(205, 398).addBox(-1.0F, -2.0F, -7.0F, 2.0F, 17.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.25F, -20.0F, -10.0F, -0.0058F, 0.218F, -0.0445F));

        PartDefinition cube_r69 = leftsidelong.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(273, 115).addBox(1.25F, 12.0F, -12.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0115F, 0.1304F, 0.088F));

        PartDefinition lowerleftlong = leftsidelong.addOrReplaceChild("lowerleftlong", CubeListBuilder.create().texOffs(390, 76).addBox(-1.0F, 0.0F, -3.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(112, 334).addBox(-1.0F, 1.0F, -6.0F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(53, 48).addBox(-1.0F, 10.0F, -3.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 1.0F, 0.0435F, -0.0038F, 0.0872F));

        PartDefinition leftarm = chest.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(373, 312).addBox(0.0F, 0.0F, -6.0F, 11.0F, 28.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(57, 418).addBox(-1.0F, -1.0F, -8.0F, 7.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(432, 378).addBox(-3.0F, 3.0F, -8.0F, 6.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -18.0F, -5.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition lowerarm2 = leftarm.addOrReplaceChild("lowerarm2", CubeListBuilder.create().texOffs(0, 371).addBox(-5.0F, 0.0F, -6.0F, 11.0F, 28.0F, 12.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 321).addBox(-8.0F, 20.0F, -9.0F, 17.0F, 6.0F, 18.0F, new CubeDeformation(-0.01F)), PartPose.offset(5.0F, 28.0F, 0.0F));

        PartDefinition cube_r70 = lowerarm2.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(410, 426).addBox(34.75F, -3.0F, -6.0F, 3.0F, 24.0F, 12.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-40.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r71 = lowerarm2.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(29, 446).addBox(15.0F, 1.5F, -6.0F, 3.0F, 24.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -12.0F, 1.5708F, -1.4835F, -1.5708F));

        PartDefinition cube_r72 = lowerarm2.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(58, 446).addBox(3.0F, 0.5F, -5.0F, 3.0F, 24.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 1.4835F, -1.5708F));

        PartDefinition cube_r73 = lowerarm2.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(432, 204).addBox(3.0F, 0.5F, -6.0F, 3.0F, 24.0F, 12.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition grabber2 = lowerarm2.addOrReplaceChild("grabber2", CubeListBuilder.create(), PartPose.offset(42.0F, 26.0F, 0.0F));

        PartDefinition rightarm = chest.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(372, 356).addBox(-11.0F, 0.0F, -6.0F, 11.0F, 28.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(81, 334).addBox(-6.0F, -1.0F, -8.0F, 7.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(432, 349).addBox(-3.0F, 3.0F, -8.0F, 6.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -18.0F, -5.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition lowerarm = rightarm.addOrReplaceChild("lowerarm", CubeListBuilder.create().texOffs(149, 357).addBox(-5.0F, 0.0F, -6.0F, 11.0F, 28.0F, 12.0F, new CubeDeformation(-0.01F))
                .texOffs(205, 317).addBox(-8.0F, 20.0F, -9.0F, 17.0F, 6.0F, 18.0F, new CubeDeformation(-0.01F)), PartPose.offset(-6.0F, 28.0F, 0.0F));

        PartDefinition cube_r74 = lowerarm.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(425, 82).addBox(-37.75F, -3.0F, -6.0F, 3.0F, 24.0F, 12.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(41.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r75 = lowerarm.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(441, 426).addBox(-18.0F, 1.5F, -6.0F, 3.0F, 24.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.0F, 0.0F, -12.0F, 1.5708F, 1.4835F, 1.5708F));

        PartDefinition cube_r76 = lowerarm.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(0, 446).addBox(-6.0F, 0.5F, -5.0F, 3.0F, 24.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, -1.5708F, -1.4835F, 1.5708F));

        PartDefinition cube_r77 = lowerarm.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(425, 119).addBox(-6.0F, 0.5F, -6.0F, 3.0F, 24.0F, 12.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition grabber = lowerarm.addOrReplaceChild("grabber", CubeListBuilder.create(), PartPose.offset(2.0F, 28.0F, 0.0F));

        PartDefinition cube_r78 = grabber.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(71, 334).addBox(-4.0F, -3.0F, 41.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 283).addBox(-6.0F, -5.0F, -21.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -2.0F, -20.0F, 4.0F, 4.0F, 66.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition guard2 = grabber.addOrReplaceChild("guard2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 3.0F, -14.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r79 = guard2.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(164, 416).addBox(-3.0F, -5.5F, -17.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(457, 273).addBox(-3.0F, -12.0F, -13.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(180, 77).addBox(-3.0F, -19.0F, -9.0F, 6.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 242).addBox(-3.0F, -23.0F, -5.0F, 6.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(110, 32).addBox(-3.0F, -24.0F, -1.0F, 6.0F, 24.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition guard1 = grabber.addOrReplaceChild("guard1", CubeListBuilder.create(), PartPose.offset(-1.0F, 3.0F, -14.0F));

        PartDefinition cube_r80 = guard1.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(88, 425).addBox(-3.0F, 2.5F, -17.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(458, 0).addBox(-3.0F, 3.0F, -13.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(216, 89).addBox(-3.0F, 2.0F, -9.0F, 6.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(263, 51).addBox(-3.0F, 1.0F, -5.0F, 6.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(128, 166).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 24.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition blade = grabber.addOrReplaceChild("blade", CubeListBuilder.create().texOffs(216, 0).addBox(-2.0F, 16.133F, -214.2226F, 4.0F, 3.0F, 200.0F, new CubeDeformation(0.0F))
                .texOffs(410, 408).addBox(-2.0F, 19.133F, -190.2226F, 4.0F, 3.0F, 170.0F, new CubeDeformation(0.0F))
                .texOffs(425, 0).addBox(-2.0F, -23.867F, -190.2226F, 4.0F, 3.0F, 170.0F, new CubeDeformation(0.0F))
                .texOffs(216, 277).addBox(-2.0F, -1.867F, -277.2226F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(402, 282).addBox(-2.0F, -3.867F, -274.2226F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 131).addBox(-2.0F, -7.867F, -268.2226F, 4.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(377, 0).addBox(-2.0F, -11.867F, -262.2226F, 4.0F, 22.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(425, 0).addBox(-2.0F, -14.867F, -250.2226F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(216, 89).addBox(-2.0F, -17.867F, -238.2226F, 4.0F, 34.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(223, 204).addBox(-2.0F, -20.867F, -214.2226F, 4.0F, 3.0F, 200.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -17.867F, -214.2226F, 4.0F, 34.0F, 207.0F, new CubeDeformation(0.0F))
                .texOffs(205, 408).addBox(-1.0F, -23.867F, -214.2226F, 2.0F, 3.0F, 200.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -20.867F, -238.2226F, 2.0F, 40.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(178, 20).addBox(-1.0F, -17.867F, -250.2226F, 2.0F, 34.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(425, 41).addBox(-1.0F, -14.867F, -262.2226F, 2.0F, 28.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(97, 71).addBox(-1.0F, -10.867F, -268.2226F, 2.0F, 20.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(75, 0).addBox(-1.0F, -6.867F, -274.2226F, 2.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(258, 321).addBox(-1.0F, -2.867F, -280.2226F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 446).addBox(-1.0F, -26.867F, -190.2226F, 2.0F, 3.0F, 170.0F, new CubeDeformation(0.0F))
                .texOffs(432, 204).addBox(-1.0F, 22.133F, -190.2226F, 2.0F, 3.0F, 170.0F, new CubeDeformation(0.0F))
                .texOffs(0, 242).addBox(-1.0F, 19.133F, -214.2226F, 2.0F, 3.0F, 200.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.75F, -13.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition bell = chest.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(346, 76).addBox(-3.0F, 3.5F, -4.75F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(346, 76).addBox(-3.0F, 5.5F, -4.75F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(216, 69).addBox(-4.0F, 0.5F, -5.75F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(76, 446).addBox(-1.0F, 3.5F, -5.75F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(75, 0).addBox(-0.5F, 7.25F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -22.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition shellleft = bell.addOrReplaceChild("shellleft", CubeListBuilder.create().texOffs(370, 430).addBox(-4.0F, -0.5F, -5.75F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(47, 446).addBox(-4.0F, -1.5F, -5.75F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition shellright = bell.addOrReplaceChild("shellright", CubeListBuilder.create().texOffs(370, 430).addBox(0.0F, -0.5F, -5.75F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(53, 327).addBox(2.0F, -1.5F, -5.75F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition zipper = bell.addOrReplaceChild("zipper", CubeListBuilder.create().texOffs(54, 5).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(75, 37).addBox(-1.5F, 6.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 77).addBox(-1.5F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(75, 3).addBox(0.5F, 5.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.25F, -4.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition collarback = chest.addOrReplaceChild("collarback", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -18.0F, 6.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r81 = collarback.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(306, 74).addBox(-5.0F, 12.0F, 0.0F, 10.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(358, 106).addBox(-10.0F, 6.0F, 0.0F, 20.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 198).addBox(-15.0F, 0.0F, 0.0F, 30.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition jacket = chest.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -6.0F));

        PartDefinition cube_r82 = jacket.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(336, 141).addBox(-17.0F, 8.0F, -7.0F, 34.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -8.25F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r83 = jacket.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(264, 386).addBox(-15.0F, 0.0F, -6.0F, 30.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.25F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r84 = jacket.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(268, 60).addBox(13.0F, 9.25F, -7.0F, 4.0F, 8.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r85 = jacket.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(104, 332).addBox(12.5F, 1.5F, -4.0F, 4.0F, 12.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -9.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r86 = jacket.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(327, 331).addBox(-16.5F, 1.5F, -4.0F, 4.0F, 12.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -9.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r87 = jacket.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(258, 158).addBox(-17.0F, 9.25F, -7.0F, 4.0F, 8.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r88 = jacket.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(0, 359).addBox(-18.0F, 8.75F, 2.75F, 36.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 7.0758F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r89 = jacket.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(0, 242).addBox(-19.0F, 10.25F, -8.0F, 4.0F, 7.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -9.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r90 = jacket.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(216, 148).addBox(15.0F, 10.25F, -8.0F, 4.0F, 7.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -9.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r91 = jacket.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(205, 358).addBox(-18.0F, 9.0F, -9.5F, 36.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -8.25F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r92 = jacket.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(336, 128).addBox(-17.0F, 7.75F, 1.0F, 34.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0758F, 0.1309F, 0.0F, 0.0F));

        PartDefinition skirt = hip.addOrReplaceChild("skirt", CubeListBuilder.create(), PartPose.offset(0.0F, -67.0F, 4.0F));

        PartDefinition cube_r93 = skirt.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(128, 293).addBox(-1.5F, -5.0F, -12.0F, 5.0F, 13.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r94 = skirt.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(300, 132).addBox(-3.5F, -5.0F, -12.0F, 5.0F, 13.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r95 = skirt.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(289, 242).addBox(-19.0F, -4.0F, 0.0F, 38.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r96 = skirt.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(289, 259).addBox(-19.0F, -4.0F, -4.0F, 38.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition middleskirt = skirt.addOrReplaceChild("middleskirt", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition cube_r97 = middleskirt.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(205, 277).addBox(3.5F, -8.0F, -13.0F, 5.0F, 12.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r98 = middleskirt.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(0, 281).addBox(-8.5F, -8.0F, -13.0F, 5.0F, 12.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r99 = middleskirt.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(273, 98).addBox(-19.0F, -4.0F, 5.0F, 38.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r100 = middleskirt.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(316, 446).addBox(-24.25F, -4.25F, -7.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, -0.1739F, 0.0151F, 0.0859F));

        PartDefinition cube_r101 = middleskirt.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(295, 446).addBox(18.25F, -4.25F, 3.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.1739F, 0.0151F, -0.0859F));

        PartDefinition cube_r102 = middleskirt.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(274, 446).addBox(-24.25F, -4.25F, 3.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.1739F, -0.0151F, 0.0859F));

        PartDefinition cube_r103 = middleskirt.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(337, 446).addBox(18.25F, -4.25F, -7.0F, 6.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, -0.1739F, -0.0151F, -0.0859F));

        PartDefinition cube_r104 = middleskirt.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(281, 0).addBox(-19.0F, -4.0F, -9.0F, 38.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition endskirt = middleskirt.addOrReplaceChild("endskirt", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition cube_r105 = endskirt.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(249, 248).addBox(8.5F, 0.0F, -14.0F, 5.0F, 8.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r106 = endskirt.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(256, 119).addBox(-13.5F, 0.0F, -14.0F, 5.0F, 8.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r107 = endskirt.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(296, 171).addBox(-19.0F, 0.0F, 10.0F, 38.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r108 = endskirt.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(379, 446).addBox(-25.25F, 1.75F, -11.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -9.0F, -0.1739F, 0.0151F, 0.0859F));

        PartDefinition cube_r109 = endskirt.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(459, 237).addBox(23.25F, 2.25F, -11.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -6.0F, -0.1739F, -0.0151F, -0.0859F));

        PartDefinition cube_r110 = endskirt.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(458, 14).addBox(-29.25F, 2.25F, -11.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -6.0F, -0.1739F, 0.0151F, 0.0859F));

        PartDefinition cube_r111 = endskirt.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(81, 355).addBox(18.25F, 1.75F, 7.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, 0.1739F, 0.0151F, -0.0859F));

        PartDefinition cube_r112 = endskirt.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(456, 130).addBox(-29.25F, 2.25F, 7.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 7.0F, 0.1739F, -0.0151F, 0.0859F));

        PartDefinition cube_r113 = endskirt.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(456, 94).addBox(23.25F, 2.25F, 7.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 7.0F, 0.1739F, 0.0151F, -0.0859F));

        PartDefinition cube_r114 = endskirt.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(114, 242).addBox(-25.25F, 1.75F, 7.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 10.0F, 0.1739F, -0.0151F, 0.0859F));

        PartDefinition cube_r115 = endskirt.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(454, 49).addBox(18.25F, 1.75F, -11.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -9.0F, -0.1739F, -0.0151F, -0.0859F));

        PartDefinition cube_r116 = endskirt.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(317, 282).addBox(-19.0F, 0.0F, -14.0F, 38.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 1024, 1024);
    }



    public boolean shouldRenderZapper(Sora sora){
        boolean flag = sora.getAttackType()==25 && sora.getAttackCounter() < 45 && sora.getAttackCounter() > 1;
        flag = flag || (sora.getAttackType()==28 && sora.getAttackCounter() < 40 && sora.getAttackCounter() > 5);
        return flag;
    }
    public void setZapperVisible(boolean b){
        this.parts.rightarm().getChild("lowerarm").getChild("grabber").visible=b;
    }

    public void defineAnimations() {
        this.animations = new Animations(idleGrounded, idleTransition, patGrounded, sit, sitImpatient, sitPat, sleepingPose, deathLoop, deathStart, attackOne, attackTwo, attackThree, attackCounter, bowDraw, standingInspect, wetShake, viewFlower, swimLoop, interact, swimMove, snowballIdle, throwSnowball, snowballIdleTransition, patEmbarassed);}
    public void attackAnim(Sora pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean flag = pEntity.slashThroughAnimState.isStarted() || pEntity.isUsingHyper();
        this.animate(pEntity.slashThroughAnimState,slashThrough,pAgeInTicks);
        this.animate(pEntity.chargeAnimState,unlimitedCharge,pAgeInTicks);
        if(pEntity.isBoisterous()){
            flag=true;
            int n = pEntity.getAttackType();
            if(n==28){
                this.animate(pEntity.attackAnimState, boisterousOne,pAgeInTicks);
            }else if (n==25){
                this.animate(pEntity.attackAnimState, boisterousTwo,pAgeInTicks);
            }else if(n==22){
                this.animate(pEntity.attackAnimState, boisterousThree,pAgeInTicks);
            }else if(n==55){
                this.animate(pEntity.attackCounterAnimState, boisterousCounter,pAgeInTicks);
            }
        }
        if(!flag){
        super.attackAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);}
    }

    @Override
    public void setupAnim(@NotNull Sora sora, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.setZapperVisible(this.shouldRenderZapper(sora));
        super.setupAnim(sora, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

}
