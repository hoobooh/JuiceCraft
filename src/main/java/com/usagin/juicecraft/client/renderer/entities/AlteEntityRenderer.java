package com.usagin.juicecraft.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.usagin.juicecraft.client.models.alte.AlteEntityModel;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.client.renderer.FriendItemInHandLayer;
import com.usagin.juicecraft.client.renderer.FriendItemOnBackLayer;
import com.usagin.juicecraft.client.renderer.FriendRenderer;
import com.usagin.juicecraft.friends.Alte;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class AlteEntityRenderer extends FriendRenderer<Alte, AlteEntityModel> {
    private static final ResourceLocation ALTE_NEUTRAL = new ResourceLocation(MODID, "textures/entities/alte/neutral.png");
    private static final ResourceLocation ALTE_NARROW = new ResourceLocation(MODID, "textures/entities/alte/half.png");
    private static final ResourceLocation ALTE_CLOSED = new ResourceLocation(MODID, "textures/entities/alte/closed.png");
    private static final ResourceLocation ALTE_WINK = new ResourceLocation(MODID, "textures/entities/alte/wink.png");
    private static final ResourceLocation ALTE_ENERGYLAYER = new ResourceLocation(MODID, "textures/entities/alte/lightlayerfinal.png");
    FriendEyeLayer<Alte, AlteEntityModel> energylayer;
    FriendItemInHandLayer<Alte, AlteEntityModel> pLayer;
    FriendItemOnBackLayer<Alte, AlteEntityModel> pBackLayer;
    public AlteEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AlteEntityModel(pContext.bakeLayer(AlteEntityModel.LAYER_LOCATION)),0.5f);
        energylayer =new FriendEyeLayer<>(this, ALTE_ENERGYLAYER);
        pLayer=new FriendItemInHandLayer<>(this, pContext.getItemInHandRenderer());
        pBackLayer = new FriendItemOnBackLayer<>(this, pContext.getItemInHandRenderer());
        energylayer.visible=true;
        this.addLayer(energylayer);
        this.addLayer(pBackLayer);
        this.addLayer(pLayer);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Alte pEntity) {
        if(pEntity.getPose() == Pose.SLEEPING||pEntity.blinkCounter<=6||(pEntity.shakeAnimO>0 && pEntity.shakeAnimO<2) || pEntity.shakeAnimO > 3){
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
}

