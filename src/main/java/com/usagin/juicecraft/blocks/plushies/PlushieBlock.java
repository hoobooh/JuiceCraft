package com.usagin.juicecraft.blocks.plushies;

import com.usagin.juicecraft.blocks.FriendBedEntity;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class PlushieBlock extends Block  implements EntityBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public PlushieBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0).setValue(WATERLOGGED, Boolean.FALSE));

    }
    public float getYRotationDegrees(BlockState pState) {
        return RotationSegment.convertToDegrees(pState.getValue(ROTATION));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ROTATION, WATERLOGGED);
    }
    public static VoxelShape PLUSHIESHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    public abstract ResourceLocation getTexture();
    public Direction getBedDirection(BlockState state, LevelReader level, BlockPos pos)
    {
        return state.getValue(HorizontalDirectionalBlock.FACING);
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        float n=(pContext.getRotation() % 360);
        if(n < 0){
            n+=360;
        }
        return this.defaultBlockState().setValue(ROTATION, (int) Math.floor(n/22.5));
    }
}
