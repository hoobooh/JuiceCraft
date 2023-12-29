package com.usagin.juicecraft.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;

import static net.minecraft.world.entity.Pose.SITTING;
import static net.minecraft.world.entity.Pose.SLEEPING;

public abstract class FriendEntityModel<T extends Friend> extends HierarchicalModel<T> implements ArmedModel {
    Logger LOGGER = LogUtils.getLogger();
    public ModelParts parts;
    public Animations animations;

    public abstract void defineAnimations();

    public abstract void defineParts(ModelPart root);

    public record Animations(AnimationDefinition idlegrounded, AnimationDefinition idletransition,
                             AnimationDefinition patgrounded, AnimationDefinition sit, AnimationDefinition sitimpatient,
                             AnimationDefinition sitpat, AnimationDefinition sleep, AnimationDefinition death,
                             AnimationDefinition deathstart, AnimationDefinition attackone,
                             AnimationDefinition attacktwo, AnimationDefinition attackthree,
                             AnimationDefinition counter, AnimationDefinition bowdraw,
                             AnimationDefinition standinginspect) {
    }

    public record ModelParts(ModelPart customroot, ModelPart head, ModelPart leftarm, ModelPart rightarm,
                             ModelPart leftleg, ModelPart rightleg, ModelPart chest) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        parts.customroot().offsetScale(new Vector3f(-0.83F, -0.83F, -0.83F));
        parts.customroot().offsetPos(new Vector3f(0, 24, 0));
        parts.customroot().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
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

    @Override
    public ModelPart root() {
        return this.parts.customroot();
    }

    @Override
    public void setupAnim(Friend pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);
        if (!pEntity.getIsDying()) {
            if (pEntity.getPose() != SITTING) {
                if (pEntity.getPose() == SLEEPING) {
                    animate(pEntity.sleepAnimState, animations.sleep(), pAgeInTicks);
                } else {
                    if(pEntity.animatestandingtimer>0){
                        animate(pEntity.idleAnimState, animations.standinginspect(), pAgeInTicks);
                    }
                    else{animate(pEntity.idleAnimState, animations.idlegrounded(), pAgeInTicks);}
                    animate(pEntity.idleAnimStartState, animations.idletransition(), pAgeInTicks);
                }
                animate(pEntity.patAnimState, animations.patgrounded(), pAgeInTicks);
            } else {
                animate(pEntity.sitPatAnimState, animations.sitpat(), pAgeInTicks);
                animate(pEntity.sitAnimState, animations.sit(), pAgeInTicks);
                animate(pEntity.sitImpatientAnimState, animations.sitimpatient(), pAgeInTicks);
            }
            if (pEntity.getAttackType() == 50) {
                if(pEntity.getAttackCounter()==34/pEntity.getAttackSpeed()){
                    pEntity.attackAnimState.stop();
                }
                animate(pEntity.attackAnimState, animations.counter(), pAgeInTicks, (float) pEntity.getAttackSpeed());
            }
            else if (pEntity.getAttackType() == 40) {
                animate(pEntity.attackAnimState, animations.attackone(), pAgeInTicks, (float) pEntity.getAttackSpeed());
            } else if (pEntity.getAttackType() == 20) {
                animate(pEntity.attackAnimState, animations.attacktwo(), pAgeInTicks, (float) pEntity.getAttackSpeed());
            } else if (pEntity.getAttackType() == 10) {
                animate(pEntity.attackAnimState, animations.attackthree(), pAgeInTicks, (float) pEntity.getAttackSpeed());
            }

            if (!pEntity.getInSittingPose()) {
                if (!pEntity.isSprinting() && !pEntity.isSwimming() && !pEntity.idle()) {
                    this.parts.rightleg().xRot = (float) (Math.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount);
                    this.parts.leftleg().xRot = (float) ((Math.cos(pLimbSwing * 0.6662F + (float) Math.PI)) * 1.4F * pLimbSwingAmount);
                    if (pEntity.getAttackCounter() == 0 && !pEntity.drawBowAnimationState.isStarted()) {
                        this.parts.leftarm().xRot = (float) (Math.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount);
                        this.parts.leftarm().zRot = (float) -Math.toRadians(10);
                        this.parts.rightarm().zRot = (float) Math.toRadians(10);
                        this.parts.rightarm().xRot = (float) ((Math.cos(pLimbSwing * 0.6662F + (float) Math.PI)) * 1.4F * pLimbSwingAmount);
                    }
                }
                animate(pEntity.drawBowAnimationState,animations.bowdraw(),pAgeInTicks);
            }
            if (!pEntity.sitImpatientAnimState.isStarted() && pEntity.getPose() != SLEEPING) {
                this.parts.head().yRot = (pNetHeadYaw * (float) Math.PI / 180f);
                this.parts.head().xRot = (pHeadPitch * (float) Math.PI / 180f);
            }
        } else {
            animate(pEntity.deathStartAnimState, animations.deathstart(), pAgeInTicks);
            animate(pEntity.deathAnimState, animations.death(), pAgeInTicks);
        }

    }
}
