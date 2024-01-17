package com.usagin.juicecraft.client.models.sora;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.animation.SoraAnimation;
import com.usagin.juicecraft.client.models.FriendEntityModel;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;

import static com.usagin.juicecraft.client.animation.SoraAnimation.*;
import static net.minecraft.world.entity.Pose.SITTING;
import static net.minecraft.world.entity.Pose.SLEEPING;

public class SoraEntityModel extends FriendEntityModel<Sora> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "soraentitymodel"), "main");
	public SoraEntityModel(ModelPart root) {
		super(root);
	}
	public void defineParts(ModelPart root){
		ModelPart customroom = root.getChild("customroom");
		ModelPart head = root.getChild("customroom").getChild("Friend").getChild("head");
		ModelPart leftarm = root.getChild("customroom").getChild("Friend").getChild("leftarm");
		ModelPart rightarm = root.getChild("customroom").getChild("Friend").getChild("rightarm");
		ModelPart leftleg = root.getChild("customroom").getChild("Friend").getChild("leftleg");
		ModelPart rightleg = root.getChild("customroom").getChild("Friend").getChild("rightleg");
		ModelPart chest = root.getChild("customroom").getChild("Friend").getChild("chest");
		this.parts=new ModelParts(customroom, head, leftarm, rightarm, leftleg, rightleg, chest);
	}
	public void defineAnimations(){
		this.animations=new Animations(IDLEGROUNDED,IDLEGROUNDED,IDLETRANSITION,PATGROUNDED,SIT,SITIMPATIENT,SITPAT,SLEEPINGPOSE,DEATHANIM,DEATHANIMSTART,ATTACKONE,ATTACKTWO,ATTACKTHREE,COUNTERANIM, BOWDRAW, ATTACKONE, ATTACKONE, ATTACKONE, ATTACKONE, ATTACKONE);
	}

	public void translateToHand(HumanoidArm pSide, @NotNull PoseStack pPoseStack) {
		String limbName;
		String grabberName;
		if (pSide.name().equals("LEFT")) {
			limbName = "lowerarm2";
			grabberName = "grabber2";
		} else {
			limbName = "lowerarm";
			grabberName = "grabber";
		}
		Logger LOGGER = LogUtils.getLogger();
		String armName = pSide.name().toLowerCase() + "arm";
		ModelPart root = this.root().getChild("Friend");
		ModelPart limb = this.root().getChild("Friend").getChild(armName).getChild(limbName);
		ModelPart arm = this.root().getChild("Friend").getChild(armName);
		ModelPart hand = limb.getChild(grabberName);
		pPoseStack.translate(0, 1.5, 0);
		translateAndRotate(pPoseStack, root);
		translateAndRotate(pPoseStack, arm);
		translateAndRotate(pPoseStack, limb);
		translateAndRotate(pPoseStack, hand);

		pPoseStack.scale(0.883F, 0.883F, 0.883F);

	}
	public void translateToBack(@NotNull PoseStack pPoseStack, @Nullable ItemStack pItemStack) {
		ModelPart root = this.root().getChild("Friend");
		ModelPart hip = root.getChild("hip");
		ModelPart holster = hip.getChild("weaponholster");
		pPoseStack.translate(0, 1.5, 0);
		translateAndRotate(pPoseStack, root);
		translateAndRotate(pPoseStack, hip);
		translateAndRotate(pPoseStack, holster);
		if(pItemStack!=null){
			if(pItemStack.getItem() instanceof BowItem){
				pPoseStack.translate(0.45,0,0);
			}
		}
		pPoseStack.translate(0,0.2,0);
		pPoseStack.rotateAround(new Quaternionf().rotationZYX((float) -Math.toRadians(80), (float) -Math.toRadians(90),0), holster.x * 0.17F / 16, holster.y * 0.17F / 16, holster.z * 0.17F / 16);
		pPoseStack.mulPose(new Quaternionf().rotationZYX( 0,(float)Math.toRadians(180),0));

		pPoseStack.scale(0.883F, 0.883F, 0.883F);
	}

	public void translateAndRotate(PoseStack pPoseStack, ModelPart part) {
		pPoseStack.translate(part.x * 0.17 / 16.0F, part.y * 0.17 / 16.0F, part.z * 0.17 / 16.0F);
		if (part.xRot != 0.0F || part.yRot != 0.0F || part.zRot != 0.0F) {
			pPoseStack.mulPose((new Quaternionf()).rotationZYX(part.zRot, part.yRot, part.xRot));
		}

		if (part.xScale != 1.0F || part.yScale != 1.0F || part.zScale != 1.0F) {
			pPoseStack.scale(part.xScale, part.yScale, part.zScale);
		}

	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition customroom = partdefinition.addOrReplaceChild("customroom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Friend = customroom.addOrReplaceChild("Friend", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightleg = Friend.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(0, 154).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 24.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 214).addBox(-8.0F, 0.0F, -4.0F, 16.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -60.0F, 4.0F));

		PartDefinition lowerleg2 = rightleg.addOrReplaceChild("lowerleg2", CubeListBuilder.create().texOffs(429, 410).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 20.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(311, 464).addBox(-8.0F, 0.0F, -4.0F, 16.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(220, 48).addBox(-8.0F, 16.0F, -8.0F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shoecuff = lowerleg2.addOrReplaceChild("shoecuff", CubeListBuilder.create().texOffs(272, 36).addBox(-25.0F, -20.0F, -4.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(268, 160).addBox(-5.0F, -20.0F, -4.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(300, 80).addBox(-21.0F, -20.0F, -8.0F, 16.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(296, 36).addBox(-21.0F, -20.0F, 12.0F, 16.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 36.0F, -4.0F));

		PartDefinition bone = shoecuff.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(204, 288).addBox(-25.0F, -4.0F, -4.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(284, 288).addBox(-5.0F, -4.0F, -4.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(300, 92).addBox(-21.0F, -4.0F, -8.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(308, 12).addBox(-21.0F, -4.0F, 12.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(268, 124).addBox(-25.0F, -12.0F, 4.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = Friend.addOrReplaceChild("head", CubeListBuilder.create().texOffs(-3, 121).addBox(-19.75F, -28.05F, -14.75F, 39.5F, 6.0F, 26.75F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -124.0F, 4.0F));

		PartDefinition headbase = head.addOrReplaceChild("headbase", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -153.0F, -12.0F, 32.0F, 28.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(136, 92).addBox(-20.0F, -141.0F, 4.0F, 40.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(160, 144).addBox(-20.0F, -141.0F, 8.0F, 40.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 125.0F, -4.0F));

		PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, 129.0F, -4.0F));

		PartDefinition ahoges = hair.addOrReplaceChild("ahoges", CubeListBuilder.create(), PartPose.offset(5.0F, -1.0F, -5.0F));

		PartDefinition rightahoge = ahoges.addOrReplaceChild("rightahoge", CubeListBuilder.create().texOffs(324, 0).addBox(-22.0F, 11.0F, 3.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(316, 210).addBox(-25.0F, 15.0F, 5.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(320, 152).addBox(-27.0F, 19.0F, 7.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(252, 112).addBox(-29.0F, 21.0F, 9.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(228, 288).addBox(-16.0F, 3.0F, -1.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(184, 104).addBox(-12.0F, -1.0F, -5.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(268, 44).addBox(-18.0F, 7.0F, 1.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(292, 0).addBox(-6.0F, -5.0F, -1.0F, 12.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(120, 258).addBox(-2.0F, -7.0F, -1.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, -163.0F, -6.0F));

		PartDefinition leftahoge2 = ahoges.addOrReplaceChild("leftahoge2", CubeListBuilder.create().texOffs(276, 244).addBox(-8.0F, -11.0F, 3.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(316, 260).addBox(2.0F, 3.0F, 3.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 86).addBox(6.0F, 7.0F, 5.0F, 10.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(276, 254).addBox(12.0F, 9.0F, 7.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(328, 40).addBox(-16.0F, -7.0F, 7.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(328, 218).addBox(-4.0F, -5.0F, -1.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(316, 268).addBox(-2.0F, -1.0F, 1.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -147.0F, -5.0F));

		PartDefinition leftahoge = ahoges.addOrReplaceChild("leftahoge", CubeListBuilder.create().texOffs(276, 244).addBox(-6.0F, -7.0F, 2.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(316, 260).addBox(4.0F, 7.0F, 2.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 86).addBox(8.0F, 11.0F, 4.0F, 10.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(276, 254).addBox(14.0F, 13.0F, 6.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(328, 40).addBox(-14.0F, -3.0F, 6.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(328, 218).addBox(-2.0F, -1.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(316, 268).addBox(0.0F, 3.0F, 0.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -159.0F, -9.0F));

		PartDefinition tophair = hair.addOrReplaceChild("tophair", CubeListBuilder.create().texOffs(200, 192).addBox(16.0F, -165.0F, -12.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 124).addBox(-4.0F, -167.0F, -8.0F, 16.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(140, 240).addBox(-12.0F, -167.0F, -4.0F, 8.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(0, 100).addBox(-20.0F, -161.0F, -4.0F, 40.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(1, 101).addBox(-19.0F, -160.0F, -11.0F, 38.0F, 3.0F, 19.0F, new CubeDeformation(0.0F))
				.texOffs(0, 100).addBox(-19.0F, -157.0F, -4.0F, 38.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(96, 0).addBox(-16.0F, -165.0F, -12.0F, 32.0F, 4.0F, 28.0F, new CubeDeformation(0.0F))
				.texOffs(332, 50).addBox(8.0F, -161.0F, 16.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(328, 232).addBox(-16.0F, -161.0F, 16.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(292, 100).addBox(-8.0F, -153.0F, 20.0F, 16.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(268, 184).addBox(-12.0F, -157.0F, 20.0F, 24.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(100, 100).addBox(-8.0F, -159.0F, 18.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(184, 112).addBox(-8.0F, -165.0F, 16.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(220, 84).addBox(-16.0F, -161.0F, -8.0F, 36.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(224, 92).addBox(-16.0F, -161.0F, -12.0F, 34.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-16.0F, -161.0F, -12.0F, -2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(292, 24).addBox(-8.0F, -165.0F, -16.0F, 16.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bangs = hair.addOrReplaceChild("bangs", CubeListBuilder.create().texOffs(76, 328).addBox(-12.0F, 6.0F, -7.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(56, 124).addBox(-16.0F, 14.0F, -5.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(332, 184).addBox(-18.8F, 22.0F, -3.0F, 2.8F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(168, 160).addBox(-20.8F, 28.0F, -1.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(100, 104).addBox(-4.0F, 2.0F, -7.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(204, 288).addBox(0.0F, 2.0F, -11.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(124, 330).addBox(4.0F, 2.0F, -11.0F, 4.0F, 14.8F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(60, 324).addBox(8.0F, 2.0F, -11.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(108, 64).addBox(12.0F, 2.0F, -11.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(228, 296).addBox(16.0F, 2.0F, -7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(248, 140).addBox(20.0F, 2.0F, -7.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(236, 314).addBox(-14.0F, 6.0F, -1.0F, 2.0F, 24.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(168, 314).addBox(-10.0F, 2.0F, -1.0F, 2.0F, 24.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(16, 124).addBox(-10.0F, 2.0F, -5.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(228, 20).addBox(-12.0F, 34.0F, -5.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(280, 264).addBox(-12.0F, 2.0F, -3.0F, 2.0F, 32.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 100).addBox(-8.0F, 2.0F, -7.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(32, 312).addBox(26.0F, 2.0F, -3.0F, 2.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(112, 0).addBox(24.0F, 26.0F, -5.0F, 2.0F, 20.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 294).addBox(24.0F, 2.0F, -5.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64).addBox(24.0F, 6.0F, -1.0F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(332, 196).addBox(0.0F, 24.8F, -7.8F, 4.0F, 5.2F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(132, 268).addBox(4.0F, 16.8F, -9.0F, 4.0F, 9.2F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(284, 60).addBox(-12.0F, 6.0F, 5.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(268, 160).addBox(24.0F, 6.0F, 5.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -163.0F, -9.0F));

		PartDefinition leftsidehair = hair.addOrReplaceChild("leftsidehair", CubeListBuilder.create().texOffs(128, 172).addBox(-5.0F, 0.0F, 7.0F, 4.0F, 44.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(120, 290).addBox(-5.0F, 0.0F, 3.0F, 4.0F, 36.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(108, 328).addBox(-5.0F, 0.0F, -9.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 124).addBox(-1.0F, 0.0F, -1.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(92, 328).addBox(-1.0F, 0.0F, -5.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, 0.0F, 3.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(96, 0).addBox(3.0F, 28.0F, 7.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(332, 108).addBox(7.0F, 48.0F, 11.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(332, 124).addBox(11.0F, 56.0F, 15.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, -157.0F, 9.0F));

		PartDefinition rightsidehair = hair.addOrReplaceChild("rightsidehair", CubeListBuilder.create().texOffs(172, 266).addBox(1.0F, 0.0F, 6.0F, 4.0F, 44.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 294).addBox(1.0F, 0.0F, 2.0F, 4.0F, 36.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(328, 296).addBox(1.0F, 0.0F, -10.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(312, 280).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(328, 276).addBox(-3.0F, 0.0F, -6.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 0).addBox(-3.0F, 0.0F, 2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(80, 124).addBox(-7.0F, 28.0F, 6.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(332, 172).addBox(-11.0F, 40.0F, 10.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-21.0F, -157.0F, 10.0F));

		PartDefinition backhair = hair.addOrReplaceChild("backhair", CubeListBuilder.create(), PartPose.offset(0.0F, -157.0F, 20.0F));

		PartDefinition upper = backhair.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(72, 280).addBox(12.0F, -11.0F, -5.0F, 4.0F, 44.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(300, 244).addBox(8.0F, -7.0F, -5.0F, 4.0F, 36.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(188, 288).addBox(4.0F, -3.0F, -1.0F, 4.0F, 40.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(104, 284).addBox(0.0F, 1.0F, -1.0F, 4.0F, 40.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(56, 268).addBox(-4.0F, 1.0F, -1.0F, 4.0F, 44.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 268).addBox(-8.0F, -3.0F, -1.0F, 4.0F, 44.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(88, 284).addBox(-12.0F, -7.0F, -5.0F, 4.0F, 40.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(152, 294).addBox(-16.0F, -11.0F, -5.0F, 4.0F, 36.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 5.0F));

		PartDefinition middle = backhair.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(220, 308).addBox(17.0F, -23.0F, -9.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(312, 308).addBox(13.0F, -19.0F, -5.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(296, 308).addBox(9.0F, -15.0F, -5.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(252, 320).addBox(5.0F, -7.0F, -1.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(320, 128).addBox(1.0F, -3.0F, -1.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(316, 236).addBox(-3.0F, 1.0F, -1.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(136, 294).addBox(-7.0F, -3.0F, -1.0F, 4.0F, 36.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(280, 308).addBox(-11.0F, -19.0F, -5.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(204, 308).addBox(-15.0F, -23.0F, -5.0F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 294).addBox(-19.0F, -27.0F, -9.0F, 4.0F, 36.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 55.0F, 9.0F));

		PartDefinition lower = backhair.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(332, 20).addBox(21.0F, -16.0F, -11.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(316, 186).addBox(13.0F, -12.0F, -3.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(156, 266).addBox(9.0F, -8.0F, -3.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(184, 332).addBox(25.0F, -2.0F, -7.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(24, 266).addBox(1.0F, -4.0F, 1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 266).addBox(-3.0F, 0.0F, 1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(244, 224).addBox(-7.0F, 4.0F, 1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(332, 160).addBox(-11.0F, 16.0F, 5.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(328, 316).addBox(-19.0F, 0.0F, -3.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(204, 64).addBox(-15.0F, -12.0F, -3.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(44, 316).addBox(-19.0F, -20.0F, -7.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 154).addBox(-23.0F, -20.0F, -11.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(268, 0).addBox(-27.0F, -10.8F, -7.0F, 4.0F, 10.8F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 76.0F, 11.0F));

		PartDefinition leftarm = Friend.addOrReplaceChild("leftarm", CubeListBuilder.create(), PartPose.offset(16.0F, -113.0F, 6.0F));

		PartDefinition lowerarm2 = leftarm.addOrReplaceChild("lowerarm2", CubeListBuilder.create().texOffs(244, 224).mirror().addBox(-8.0F, 20.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(437, 358).mirror().addBox(-6.0F, 0.0F, -6.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(477, 21).mirror().addBox(2.0F, 0.0F, -2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(449, 165).mirror().addBox(-2.0F, 0.0F, -6.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 25.0F, 0.0F));

		PartDefinition grabber2 = lowerarm2.addOrReplaceChild("grabber2", CubeListBuilder.create(), PartPose.offset(-1.0F, 32.0F, -3.0F));

		PartDefinition base5 = leftarm.addOrReplaceChild("base5", CubeListBuilder.create().texOffs(148, 160).addBox(5.0F, -121.0F, 0.0F, 4.0F, 32.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(248, 264).addBox(13.0F, -113.0F, 4.0F, 4.0F, 24.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(180, 192).addBox(9.0F, -117.0F, 0.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 114.0F, -6.0F));

		PartDefinition rightarm = Friend.addOrReplaceChild("rightarm", CubeListBuilder.create(), PartPose.offset(-16.0F, -113.0F, 6.0F));

		PartDefinition lowerarm = rightarm.addOrReplaceChild("lowerarm", CubeListBuilder.create().texOffs(244, 224).addBox(-8.0F, 20.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(437, 358).addBox(2.0F, 0.0F, -6.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(477, 21).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 28.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(449, 165).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 25.0F, 0.0F));

		PartDefinition grabber = lowerarm.addOrReplaceChild("grabber", CubeListBuilder.create(), PartPose.offset(1.0F, 32.0F, -3.0F));

		PartDefinition base4 = rightarm.addOrReplaceChild("base4", CubeListBuilder.create().texOffs(96, 172).addBox(-20.0F, -121.0F, 0.0F, 4.0F, 32.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(264, 264).addBox(-28.0F, -113.0F, 4.0F, 4.0F, 24.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(212, 192).addBox(-24.0F, -117.0F, 0.0F, 4.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, 114.0F, -6.0F));

		PartDefinition neck = Friend.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(84, 256).addBox(-6.0F, -19.0F, -6.0F, 12.0F, 19.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -112.0F, 4.0F));


		PartDefinition chest = Friend.addOrReplaceChild("chest", CubeListBuilder.create(), PartPose.offset(0.0F, -92.0F, 4.0F));

		PartDefinition base3 = chest.addOrReplaceChild("base3", CubeListBuilder.create().texOffs(114, 458).addBox(-16.0F, -121.0F, -4.0F, 32.0F, 22.0F, 20.0F, new CubeDeformation(0.0F))
				.texOffs(13, 479).addBox(-16.0F, -117.0F, -8.0F, 32.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 93.0F, -4.0F));

		PartDefinition jacket = chest.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(2, 66).addBox(-18.0F, -99.0F, -9.0F, 36.0F, 8.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 93.0F, -4.0F));

		PartDefinition collar = jacket.addOrReplaceChild("collar", CubeListBuilder.create().texOffs(332, 100).addBox(-16.0F, -1.0F, -4.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(332, 64).addBox(8.0F, -1.0F, -4.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(312, 176).addBox(-12.0F, 3.0F, -8.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(312, 116).addBox(4.0F, 3.0F, -8.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(312, 68).addBox(0.0F, 7.0F, -8.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(312, 56).addBox(-8.0F, 7.0F, -8.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(256, 104).addBox(-4.0F, 11.0F, -8.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -120.0F, -4.0F));

		PartDefinition ball = collar.addOrReplaceChild("ball", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, -7.0F));

		PartDefinition bone3 = ball.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(32, 154).addBox(-4.0F, -116.0F, -16.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(37, 159).addBox(-1.0F, -108.0F, -13.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 116.0F, 11.0F));

		PartDefinition zip = bone3.addOrReplaceChild("zip", CubeListBuilder.create().texOffs(384, 256).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -107.0F, -12.0F));

		PartDefinition waist = Friend.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, -80.0F, 4.0F));

		PartDefinition base2 = waist.addOrReplaceChild("base2", CubeListBuilder.create().texOffs(192, 264).addBox(-12.0F, -103.0F, -4.0F, 24.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(264, 136).addBox(-12.0F, -103.0F, 8.0F, 24.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(80, 144).addBox(-15.0F, -103.0F, 0.0F, 30.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 83.0F, -4.0F));

		PartDefinition hip = Friend.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offset(0.0F, -60.0F, 4.0F));

		PartDefinition butt = hip.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(297, 415).addBox(-19.0F, -11.0F, -7.0F, 38.0F, 13.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition base = hip.addOrReplaceChild("base", CubeListBuilder.create().texOffs(132, 266).addBox(-24.0F, -80.0F, -4.0F, 4.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 266).addBox(20.0F, -80.0F, -4.0F, 4.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(180, 176).addBox(-20.0F, -80.0F, -8.0F, 40.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(180, 160).addBox(-20.0F, -80.0F, 12.0F, 40.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(108, 64).addBox(-20.0F, -92.0F, -4.0F, 40.0F, 24.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(244, 208).addBox(-16.0F, -92.0F, -8.0F, 32.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(244, 192).addBox(-16.0F, -92.0F, 12.0F, 32.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 68.0F, -4.0F));

		PartDefinition dress = hip.addOrReplaceChild("dress", CubeListBuilder.create().texOffs(128, 48).addBox(-24.0F, 0.0F, 16.0F, 48.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(128, 32).addBox(-24.0F, 0.0F, -12.0F, 48.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(236, 0).addBox(24.0F, 0.0F, -8.0F, 4.0F, 12.0F, 24.0F, new CubeDeformation(0.0F))
				.texOffs(256, 20).addBox(20.0F, 0.0F, -8.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(256, 20).addBox(-24.0F, 0.0F, -8.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(256, 20).addBox(-24.0F, 0.0F, 12.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(256, 20).addBox(20.0F, 0.0F, 12.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(104, 220).addBox(-28.0F, 0.0F, -8.0F, 4.0F, 12.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -4.0F));

		PartDefinition weaponholster = hip.addOrReplaceChild("weaponholster", CubeListBuilder.create(), PartPose.offset(-18.0F, -14.0F, 1.0F));

		PartDefinition leftleg = Friend.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(0, 214).addBox(-8.0F, 0.0F, -4.0F, 16.0F, 24.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 154).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, -60.0F, 4.0F));

		PartDefinition lowerleg = leftleg.addOrReplaceChild("lowerleg", CubeListBuilder.create().texOffs(429, 410).addBox(-4.0F, 0.0F, -8.0F, 8.0F, 20.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(311, 464).addBox(-8.0F, 0.0F, -4.0F, 16.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(204, 104).addBox(-8.0F, 16.0F, -8.0F, 16.0F, 20.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(232, 36).addBox(-12.0F, 24.0F, 0.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition shoecuff2 = lowerleg.addOrReplaceChild("shoecuff2", CubeListBuilder.create().texOffs(268, 100).addBox(-7.0F, -20.0F, -4.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(268, 0).addBox(13.0F, -20.0F, -4.0F, 4.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(292, 224).addBox(-3.0F, -20.0F, -8.0F, 16.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(292, 160).addBox(-3.0F, -20.0F, 12.0F, 16.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 36.0F, -4.0F));

		PartDefinition bone2 = lowerleg.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(284, 60).addBox(-7.0F, -4.0F, -4.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(56, 124).addBox(13.0F, -4.0F, -4.0F, 4.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(136, 232).addBox(-3.0F, -4.0F, -8.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(188, 20).addBox(-3.0F, -4.0F, 12.0F, 16.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 36.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}
}