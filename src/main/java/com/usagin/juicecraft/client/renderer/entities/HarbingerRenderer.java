package com.usagin.juicecraft.client.renderer.entities;

import com.usagin.juicecraft.client.models.harbinger.HarbingerModel;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import com.usagin.juicecraft.enemies.Harbinger;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class HarbingerRenderer extends MobRenderer<Harbinger,HarbingerModel<Harbinger>> {
    public static ResourceLocation EYES = new ResourceLocation(MODID,"textures/entities/harbinger/harbinger_eye_layer.png");
    public static ResourceLocation MAIN = new ResourceLocation(MODID,"textures/entities/harbinger/harbinger.png");
    public HarbingerRenderer(EntityRendererProvider.Context pContext, HarbingerModel<Harbinger> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
        this.addLayer(new FriendEyeLayer<>(this,EYES,true));
    }
    public HarbingerRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, new HarbingerModel<>(pContext.bakeLayer(HarbingerModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Harbinger pEntity) {
        return MAIN;
    }

}
