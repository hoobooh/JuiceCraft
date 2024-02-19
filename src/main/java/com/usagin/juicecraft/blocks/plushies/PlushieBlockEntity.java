package com.usagin.juicecraft.blocks.plushies;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PlushieBlockEntity extends BlockEntity {

    public PlushieBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
    }
}
