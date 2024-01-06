package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.models.alte.AlteEntityModel;
import com.usagin.juicecraft.client.models.Alte.AlteEntityModel;
import com.usagin.juicecraft.client.models.Alte.AlteEyeLayer;
import com.usagin.juicecraft.client.models.Alte.AlteMediumEyeLayer;
import com.usagin.juicecraft.client.models.Alte.AlteOrbLayer;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.client.renderer.FriendItemInHandLayer;
import com.usagin.juicecraft.client.renderer.FriendItemOnBackLayer;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class AlteEntityRenderer extends MobRenderer<Alte, AlteEntityModel> {
    private static final ResourceLocation ALTE_NEUTRAL = new ResourceLocation(MODID, "textures/entities/Alte/Alte_neutral.png");
    private static final ResourceLocation ALTE_NARROW = new ResourceLocation(MODID, "textures/entities/Alte/Alte_midclose.png");
    private static final ResourceLocation ALTE_CLOSED = new ResourceLocation(MODID, "textures/entities/Alte/Alte_closed.png");
    private static final ResourceLocation ALTE_WINK = new ResourceLocation(MODID, "textures/entities/Alte/Alte_closed.png");
    FriendEyeLayer<Alte, AlteEntityModel> panels;
    FriendEyeLayer<Alte, AlteEntityModel> powerring;
    FriendEyeLayer<Alte, AlteEntityModel> orb;
    FriendItemInHandLayer<Alte, AlteEntityModel> pLayer;
    FriendItemOnBackLayer<Alte, AlteEntityModel> pBackLayer;
    public AlteEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AlteEntityModel(pContext.bakeLayer(AlteEntityModel.LAYER_LOCATION)),0.5f);
        panels =new FriendEyeLayer<>(this);
        powerring =new FriendEyeLayer<>(this);
        orb=new FriendEyeLayer<>(this);
        pLayer=new FriendItemInHandLayer<>(this, pContext.getItemInHandRenderer());
        pBackLayer = new FriendItemOnBackLayer<>(this, pContext.getItemInHandRenderer());
        orb.visible=true;
        this.addLayer(orb);
        this.addLayer(pBackLayer);
        this.addLayer(pLayer);
        this.addLayer(powerring);
        this.addLayer(panels);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Alte pEntity) {
        if(pEntity.getPose() == Pose.SLEEPING||pEntity.blinkCounter<=6){
            return ALTE_CLOSED;
        }
        else if(pEntity.patCounter!=0 || (pEntity.getTimeSinceLastPat() > 3600 && !pEntity.getIsWandering() && !pEntity.isAggressive())){
            return ALTE_WINK;
        }
        else if(pEntity.blinkCounter<=8){
            return ALTE_NARROW;
        }
        return ALTE_NEUTRAL;
    }
    private static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void render(@NotNull Alte pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity,pEntityYaw,pPartialTicks,pPoseStack,pBuffer,pPackedLight);
    }
}

