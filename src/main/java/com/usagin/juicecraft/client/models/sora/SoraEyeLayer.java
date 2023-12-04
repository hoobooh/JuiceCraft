package com.usagin.juicecraft.client.models.sora;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.usagin.juicecraft.JuiceCraft.MODID;

@OnlyIn(Dist.CLIENT)
public class SoraEyeLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SORA_EYES = RenderType.eyes(new ResourceLocation(MODID, "textures/entities/sora/sora_eyelayer.png"));
    public SoraEyeLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    public @NotNull RenderType renderType() {
        return SORA_EYES;
    }
}

