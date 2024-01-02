package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.models.sora.SoraEntityModel;
import com.usagin.juicecraft.client.models.sora.SoraEyeLayer;
import com.usagin.juicecraft.client.models.sora.SoraMediumEyeLayer;
import com.usagin.juicecraft.client.models.sora.SoraOrbLayer;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.event.ScreenEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class SoraEntityRenderer extends MobRenderer<Sora, SoraEntityModel> {
    private static final ResourceLocation SORA_NEUTRAL = new ResourceLocation(MODID, "textures/entities/sora/sora_neutral.png");
    private static final ResourceLocation SORA_NARROW = new ResourceLocation(MODID, "textures/entities/sora/sora_midclose.png");
    private static final ResourceLocation SORA_CLOSED = new ResourceLocation(MODID, "textures/entities/sora/sora_closed.png");
    SoraEyeLayer<Sora, SoraEntityModel> eyeopen;
    SoraMediumEyeLayer<Sora, SoraEntityModel> eyemedium;
    SoraOrbLayer<Sora, SoraEntityModel> orb;
    FriendItemInHandLayer<Sora, SoraEntityModel> pLayer;
    FriendItemOnBackLayer<Sora, SoraEntityModel> pBackLayer;
    public SoraEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SoraEntityModel(pContext.bakeLayer(SoraEntityModel.LAYER_LOCATION)),0.5f);
        eyeopen=new SoraEyeLayer<>(this);
        eyemedium=new SoraMediumEyeLayer<>(this);
        orb=new SoraOrbLayer<>(this);
        pLayer=new FriendItemInHandLayer<>(this, pContext.getItemInHandRenderer());
        pBackLayer = new FriendItemOnBackLayer<>(this, pContext.getItemInHandRenderer());
        orb.visible=true;
        this.addLayer(orb);
        this.addLayer(pBackLayer);
        this.addLayer(pLayer);
        this.addLayer(eyemedium);
        this.addLayer(eyeopen);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Sora pEntity) {
        if(pEntity.patCounter!=0 || pEntity.getPose() == Pose.SLEEPING||pEntity.blinkCounter<=6){
            this.eyeopen.visible=false;
            this.eyemedium.visible=false;
            return SORA_CLOSED;
        }

        else if(pEntity.blinkCounter<=8 || (pEntity.getTimeSinceLastPat() > 3600 && !pEntity.getIsWandering() && !pEntity.isAggressive())){
            this.eyeopen.visible=false;
            this.eyemedium.visible=true;
            return SORA_NARROW;
        }
        this.eyeopen.visible=true;
        this.eyemedium.visible=false;
        return SORA_NEUTRAL;
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void render(@NotNull Sora pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity,pEntityYaw,pPartialTicks,pPoseStack,pBuffer,pPackedLight);
    }
}

