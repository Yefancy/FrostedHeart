package com.teammoeg.frostedheart.content.steamenergy.steamcore;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.simibubi.create.foundation.utility.VoxelShaper;
import com.teammoeg.frostedheart.FHTileTypes;
import com.teammoeg.frostedheart.base.block.FHKineticBlock;
import com.teammoeg.frostedheart.client.util.GuiUtils;
import com.teammoeg.frostedheart.content.steamenergy.ISteamEnergyBlock;

import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SteamCoreBlock extends FHKineticBlock implements ISteamEnergyBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    static final VoxelShaper shape = VoxelShaper.forDirectional(VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 16, 16, 16)), Direction.SOUTH);


    public SteamCoreBlock( Properties blockProps) {
        super(blockProps);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, Boolean.FALSE).with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH));
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return FHTileTypes.STEAM_CORE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(LIT);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape.get(state.get(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return blockState.get(BlockStateProperties.HORIZONTAL_FACING).rotateY().getAxis();
    }

    @Override
    public boolean hasShaftTowards(IWorldReader arg0, BlockPos arg1, BlockState state, Direction dir) {
        return dir == state.get(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ActionResultType superResult = super.onBlockActivated(state, world, pos, player, hand, hit);
        if (superResult.isSuccessOrConsume() || player.isSneaking())
            return superResult;
        ItemStack item = player.getHeldItem(hand);

        TileEntity te = Utils.getExistingTileEntity(world, pos);
        if (te instanceof SteamCoreTileEntity) {
            return ((SteamCoreTileEntity) te).onClick(player, item);
        }
        return superResult;
    }
}