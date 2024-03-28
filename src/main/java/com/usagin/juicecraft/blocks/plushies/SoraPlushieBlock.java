package com.usagin.juicecraft.blocks.plushies;

import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoraPlushieBlock extends PlushieBlock {

    public SoraPlushieBlock(Properties pProperties) {
        super(pProperties);
    }
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SoraPlushieBlockEntity(pPos, pState);
    }

    public ResourceLocation TEXTURE = new ResourceLocation(JuiceCraft.MODID,"textures/entities/plushies/sora.png");
    @Override
    public ResourceLocation getTexture() {
        return this.TEXTURE;
    }

}
