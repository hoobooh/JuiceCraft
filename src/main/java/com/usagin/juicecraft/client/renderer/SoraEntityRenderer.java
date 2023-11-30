package com.usagin.juicecraft.client.renderer;

import com.usagin.juicecraft.client.models.SoraEntityModel;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class SoraEntityRenderer extends MobRenderer<Sora, SoraEntityModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entities/sora.png");
    public SoraEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SoraEntityModel(pContext.bakeLayer(SoraEntityModel.LAYER_LOCATION)),0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Sora pEntity) {
        return TEXTURE;
    }
}

