package com.usagin.juicecraft.client.models.sora;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.usagin.juicecraft.client.renderer.FriendEyeLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.usagin.juicecraft.JuiceCraft.MODID;

@OnlyIn(Dist.CLIENT)
public
class SoraOrbLayer<T extends Entity, M extends EntityModel<T>> extends FriendEyeLayer<T,M> {
    private static final RenderType SORA_ORB = RenderType.eyes(new ResourceLocation(MODID, "textures/entities/sora/sora_orb_layer.png"));
    public SoraOrbLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }
    public @NotNull RenderType renderType() {
        return SORA_ORB;
    }
}
