package com.usagin.juicecraft.blocks.plushies;

import com.usagin.juicecraft.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PlushieBlockEntity extends BlockEntity {
    public PlushieBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.PLUSHIE.get(), pPos, pBlockState);
    }
}
