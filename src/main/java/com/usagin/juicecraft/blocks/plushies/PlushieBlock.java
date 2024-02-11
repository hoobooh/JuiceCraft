package com.usagin.juicecraft.blocks.plushies;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;

public abstract class PlushieBlock extends Block {

    public PlushieBlock(Properties pProperties) {
        super(pProperties);
    }
    public abstract Material getMaterial();
}
