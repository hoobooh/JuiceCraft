package com.usagin.juicecraft.client.renderer;

import net.minecraft.resources.ResourceLocation;

public class WidgetSprites {
    ResourceLocation normal, down;
    public WidgetSprites(ResourceLocation normal, ResourceLocation down){
        this.normal=normal;this.down=down;
    }
    public ResourceLocation get(boolean a, boolean b){
        if(a && b){
            return this.down;
        }
        return this.normal;
    }
}
