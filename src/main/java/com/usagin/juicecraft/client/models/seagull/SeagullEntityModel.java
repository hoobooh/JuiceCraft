package com.usagin.juicecraft.client.models.seagull;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.enemies.Seagull;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import static com.usagin.juicecraft.client.animation.SeagullAnimation.*;
import org.jetbrains.annotations.NotNull;

public class SeagullEntityModel<T extends Seagull> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(JuiceCraft.MODID, "seagull"), "main");
	private final ModelPart root;

	public SeagullEntityModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 9).addBox(-4.0F, -2.0F, -1.5F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(21, 24).addBox(3.0F, -2.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-0.85F, -3.5F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(1.4F, -1.75F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(25, 4).addBox(-0.1F, -1.75F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
		.texOffs(13, 21).addBox(-0.75F, -2.5F, -1.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, 0.5F, 0.0F, 0.0F, 0.9599F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 21).addBox(-0.35F, -3.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(35, 12).addBox(1.4F, -2.5F, -1.44F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(5, 35).addBox(0.9F, -2.5F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
		.texOffs(35, 21).addBox(1.4F, -2.5F, 1.44F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
		.texOffs(35, 6).addBox(0.9F, -2.5F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-4.0F, -1.75F, 0.0F));

		PartDefinition cube_r5 = tail.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(15, 16).addBox(-9.25F, -0.75F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.75F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition leftleft = body.addOrReplaceChild("leftleft", CubeListBuilder.create().texOffs(11, 27).addBox(-0.5588F, -0.2934F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -0.5F, 1.25F, 0.0F, 0.0F, 0.6545F));

		PartDefinition leftfoot = leftleft.addOrReplaceChild("leftfoot", CubeListBuilder.create().texOffs(5, 32).addBox(-0.75F, -0.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 32).addBox(-0.75F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(21, 31).addBox(-0.75F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(31, 15).addBox(-0.25F, -0.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(29, 30).addBox(-0.25F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(30, 27).addBox(-0.25F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(5, 29).addBox(0.25F, -0.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 29).addBox(0.25F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(27, 24).addBox(0.25F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.1088F, 3.4566F, 0.0F));

		PartDefinition rightleg = body.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(16, 27).addBox(-0.5588F, -0.2934F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -0.5F, -1.25F, 0.0F, 0.0F, 0.6545F));

		PartDefinition rightfoot = rightleg.addOrReplaceChild("rightfoot", CubeListBuilder.create().texOffs(10, 33).addBox(-0.75F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(32, 24).addBox(-0.75F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(32, 18).addBox(-0.75F, -0.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(30, 33).addBox(-0.25F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(25, 33).addBox(-0.25F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(15, 33).addBox(-0.25F, -0.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(20, 34).addBox(0.25F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(34, 29).addBox(0.25F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 35).addBox(0.25F, -0.25F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.1088F, 3.4566F, 0.0F));

		PartDefinition leftwing = body.addOrReplaceChild("leftwing", CubeListBuilder.create().texOffs(19, 9).addBox(-7.0F, -1.5F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(19, 13).addBox(-5.0F, 0.5F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.0F, 2.0F));

		PartDefinition rightwing = body.addOrReplaceChild("rightwing", CubeListBuilder.create().texOffs(21, 0).addBox(-7.0F, -1.5F, -1.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-5.0F, 0.5F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

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