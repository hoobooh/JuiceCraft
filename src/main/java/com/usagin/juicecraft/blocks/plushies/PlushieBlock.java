package com.usagin.juicecraft.blocks.plushies;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class PlushieBlock extends Block {

    public PlushieBlock(Properties pProperties) {
        super(pProperties);
    }
    public static VoxelShape PLUSHIESHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    public abstract Material getMaterial();
}
