package com.usagin.juicecraft.blocks;

import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FriendBedBlock extends BedBlock {
    private final DyeColor color;

    public FriendBedBlock(DyeColor pColor, Properties pProperties) {
        super(pColor, pProperties);
        this.color = pColor;
    }
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FriendBedEntity(pPos, pState, this.color);
    }
}
