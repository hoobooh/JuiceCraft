package com.usagin.juicecraft.blocks;

import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FriendBedEntity extends BedBlockEntity {
    public FriendBedEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    public FriendBedEntity(BlockPos pPos, BlockState pBlockState, DyeColor pColor) {
        super(pPos, pBlockState, pColor);
    }
}
