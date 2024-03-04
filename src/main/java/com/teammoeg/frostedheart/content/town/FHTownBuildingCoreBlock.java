package com.teammoeg.frostedheart.content.town;

import blusunrize.immersiveengineering.common.util.Utils;
import com.teammoeg.frostedheart.FHTileTypes;
import com.teammoeg.frostedheart.base.block.FHBaseBlock;
import com.teammoeg.frostedheart.content.climate.chunkheatdata.ChunkHeatData;
import com.teammoeg.frostedheart.content.town.house.HouseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class FHTownBuildingCoreBlock extends FHBaseBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public FHTownBuildingCoreBlock(Properties blockProps) {
        super(blockProps);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, Boolean.FALSE).with(BlockStateProperties.FACING, Direction.SOUTH));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(LIT,BlockStateProperties.FACING);
    }

    public abstract TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world);

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, entity, stack);
        TownTileEntity te = (TownTileEntity) Utils.getExistingTileEntity(world, pos);    //这玩意原本是写在HouseBlock里面的，这里的强制转型原本转为了HouseTileEntity，现在改成TownTileEntity不知道是否可行
        if (te != null) {
            // register the house to the town
            if (entity instanceof ServerPlayerEntity) {
                if (ChunkHeatData.hasAdjust(world, pos)) {
                    TeamTown.from((PlayerEntity) entity).addTownBlock(pos, te);
                }
            }
        }
    }
}
