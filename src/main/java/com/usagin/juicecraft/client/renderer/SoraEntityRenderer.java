package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.client.models.sora.SoraEntityModel;
import com.usagin.juicecraft.client.models.sora.SoraEyeLayer;
import com.usagin.juicecraft.client.models.sora.SoraMediumEyeLayer;
import com.usagin.juicecraft.client.models.sora.SoraOrbLayer;
import com.usagin.juicecraft.friends.Sora;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class SoraEntityRenderer extends MobRenderer<Sora, SoraEntityModel> {
    private static final ResourceLocation SORA_NEUTRAL = new ResourceLocation(MODID, "textures/entities/sora/sora_neutral.png");
    private static final ResourceLocation SORA_NARROW = new ResourceLocation(MODID, "textures/entities/sora/sora_midclose.png");
    private static final ResourceLocation SORA_CLOSED = new ResourceLocation(MODID, "textures/entities/sora/sora_closed.png");
    SoraEyeLayer<Sora, SoraEntityModel> eyeopen;
    SoraMediumEyeLayer<Sora, SoraEntityModel> eyemedium;
    SoraOrbLayer<Sora, SoraEntityModel> orb;
    public SoraEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SoraEntityModel(pContext.bakeLayer(SoraEntityModel.LAYER_LOCATION)),0.5f);
        eyeopen=new SoraEyeLayer<>(this);
        eyemedium=new SoraMediumEyeLayer<>(this);
        orb=new SoraOrbLayer<>(this);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Sora pEntity) {
        if(pEntity.patCounter!=0){
            this.layers.clear();
            this.addLayer(orb);
            return SORA_CLOSED;
        }
        if(pEntity.blinkCounter<=6){
            this.layers.clear();
            this.addLayer(orb);
            return SORA_CLOSED;
        }

        else if(pEntity.blinkCounter<=8){
            this.layers.clear();
            this.addLayer(eyemedium);
            this.addLayer(orb);
            return SORA_NARROW;
        }

        this.layers.clear();
        this.addLayer(eyeopen);
        this.addLayer(orb);
        return SORA_NEUTRAL;
    }
    @Override
    public void render(@NotNull Sora pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.scale(0.17F, 0.17F, 0.17F);
        //pPoseStack.translate(0F,2F,0F);
        super.render(pEntity,pEntityYaw,pPartialTicks,pPoseStack,pBuffer,pPackedLight);
    }
}

