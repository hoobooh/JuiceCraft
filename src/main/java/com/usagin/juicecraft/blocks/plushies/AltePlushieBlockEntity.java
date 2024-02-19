package com.usagin.juicecraft.blocks.plushies;

import com.usagin.juicecraft.Init.BlockEntityInit;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AltePlushieBlockEntity extends PlushieBlockEntity {
    String id = "";
    public AltePlushieBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.ALTE_PLUSHIE.get(), pPos, pBlockState);
    }
    public AltePlushieBlockEntity(BlockPos pPos, BlockState pBlockState, String id) {
        super(BlockEntityInit.ALTE_PLUSHIE.get(), pPos, pBlockState);
        this.id=id;
    }
    public BlockEntityType<?> getType() {
        return super.getType();
    }
}
