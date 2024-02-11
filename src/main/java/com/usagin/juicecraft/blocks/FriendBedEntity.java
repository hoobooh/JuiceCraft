package com.usagin.juicecraft.blocks;

import com.usagin.juicecraft.Init.BlockEntityInit;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FriendBedEntity extends BlockEntity {
    DyeColor color;
    public FriendBedEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.FRIEND_BED.get(),pPos, pBlockState);
    }

    public FriendBedEntity(BlockPos pPos, BlockState pBlockState, DyeColor pColor) {
        super(BlockEntityInit.FRIEND_BED.get(),pPos, pBlockState);
        this.color=pColor;
    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor pColor) {
        this.color = pColor;
    }
}
