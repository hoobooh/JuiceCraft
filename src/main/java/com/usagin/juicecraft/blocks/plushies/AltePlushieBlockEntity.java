package com.usagin.juicecraft.blocks.plushies;

import com.usagin.juicecraft.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AltePlushieBlockEntity extends BlockEntity {
    public AltePlushieBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.ALTE_PLUSHIE.get(), pPos, pBlockState);
    }
}
