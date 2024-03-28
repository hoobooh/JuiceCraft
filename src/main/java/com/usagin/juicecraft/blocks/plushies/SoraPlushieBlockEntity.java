package com.usagin.juicecraft.blocks.plushies;

import com.usagin.juicecraft.Init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SoraPlushieBlockEntity extends PlushieBlockEntity {
    String id = "";
    public SoraPlushieBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.SORA_PLUSHIE.get(), pPos, pBlockState);
    }
    public SoraPlushieBlockEntity(BlockPos pPos, BlockState pBlockState, String id) {
        super(BlockEntityInit.SORA_PLUSHIE.get(), pPos, pBlockState);
        this.id=id;
    }
    public BlockEntityType<?> getType() {
        return super.getType();
    }
}
