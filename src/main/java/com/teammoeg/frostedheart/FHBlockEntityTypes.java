/*
 * Copyright (c) 2022-2024 TeamMoeg
 *
 * This file is part of Frosted Heart.
 *
 * Frosted Heart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Frosted Heart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frosted Heart. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.teammoeg.frostedheart;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.simibubi.create.content.kinetics.base.HorizontalHalfShaftInstance;
import com.teammoeg.frostedheart.client.renderer.HalfShaftRenderer;
import com.teammoeg.frostedheart.content.decoration.RelicChestTileEntity;
import com.teammoeg.frostedheart.content.incubator.HeatIncubatorTileEntity;
import com.teammoeg.frostedheart.content.incubator.IncubatorTileEntity;
import com.teammoeg.frostedheart.content.research.blocks.DrawingDeskTileEntity;
import com.teammoeg.frostedheart.content.research.blocks.MechCalcTileEntity;
import com.teammoeg.frostedheart.content.steamenergy.HeatPipeTileEntity;
import com.teammoeg.frostedheart.content.steamenergy.charger.ChargerTileEntity;
import com.teammoeg.frostedheart.content.steamenergy.fountain.FountainTileEntity;
import com.teammoeg.frostedheart.content.steamenergy.debug.DebugHeaterTileEntity;
import com.teammoeg.frostedheart.content.steamenergy.sauna.SaunaTileEntity;
import com.teammoeg.frostedheart.content.steamenergy.steamcore.SteamCoreTileEntity;
import com.teammoeg.frostedheart.content.town.house.HouseBlockEntity;
import com.teammoeg.frostedheart.content.town.hunting.HuntingBaseBlockEntity;
import com.teammoeg.frostedheart.content.town.hunting.HuntingCampBlockEntity;
import com.teammoeg.frostedheart.content.town.mine.MineBaseBlockEntity;
import com.teammoeg.frostedheart.content.town.mine.MineBlockEntity;
import com.teammoeg.frostedheart.content.town.warehouse.WarehouseBlockEntity;
import com.teammoeg.frostedheart.content.utility.incinerator.GasVentTileEntity;
import com.teammoeg.frostedheart.content.utility.incinerator.OilBurnerTileEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.teammoeg.frostedheart.FHMain.FH_REGISTRATE;

public class FHBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, FHMain.MODID);
    public static final RegistryObject<BlockEntityType<HeatPipeTileEntity>> HEATPIPE = REGISTER.register(
            "heat_pipe", makeType(HeatPipeTileEntity::new, FHBlocks.HEAT_PIPE)
    );
    public static final RegistryObject<BlockEntityType<DebugHeaterTileEntity>> DEBUGHEATER = REGISTER.register(
            "debug_heater", makeType(DebugHeaterTileEntity::new,  FHBlocks.DEBUG_HEATER)
    );
    public static final RegistryObject<BlockEntityType<ChargerTileEntity>> CHARGER = REGISTER.register(
            "charger", makeType(ChargerTileEntity::new, FHBlocks.CHARGER)
    );

    public static final RegistryObject<BlockEntityType<OilBurnerTileEntity>> OIL_BURNER = REGISTER.register(
            "oil_burner", makeType(OilBurnerTileEntity::new, FHBlocks.OIL_BURNER)
    );
    public static final RegistryObject<BlockEntityType<GasVentTileEntity>> GAS_VENT = REGISTER.register(
            "gas_vent", makeType(GasVentTileEntity::new, FHBlocks.GAS_VENT)
    );

    public static final RegistryObject<BlockEntityType<DrawingDeskTileEntity>> DRAWING_DESK = REGISTER.register(
            "drawing_desk", makeType(DrawingDeskTileEntity::new, FHBlocks.DRAWING_DESK)
    );
    public static final RegistryObject<BlockEntityType<RelicChestTileEntity>> RELIC_CHEST = REGISTER.register(
            "relic_chest", makeType(RelicChestTileEntity::new, FHBlocks.RELIC_CHEST)
    );

    public static final RegistryObject<BlockEntityType<MechCalcTileEntity>> MECH_CALC = REGISTER.register(
            "mechanical_calculator", makeType(MechCalcTileEntity::new, FHBlocks.MECHANICAL_CALCULATOR)
    );

    /*public static final RegistryObject<TileEntityType<SteamCoreTileEntity>> STEAM_CORE = REGISTER.register(
            "steam_core", makeType(SteamCoreTileEntity::new, FHBlocks.steam_core)
    );*/
    public static final BlockEntityEntry<SteamCoreTileEntity> STEAM_CORE = FH_REGISTRATE
        .blockEntity("steam_core", SteamCoreTileEntity::new)
        .instance(() -> HorizontalHalfShaftInstance::new)
        .validBlocks(FHBlocks.STEAM_CORE)
        .renderer(() -> HalfShaftRenderer::new)
        .register();

    public static final RegistryObject<BlockEntityType<FountainTileEntity>> FOUNTAIN = REGISTER.register(
            "fountain", makeType(FountainTileEntity::new, FHBlocks.FOUNTAIN_BASE)
    );

    public static final RegistryObject<BlockEntityType<SaunaTileEntity>> SAUNA = REGISTER.register(
            "sauna", makeType(SaunaTileEntity::new, FHBlocks.SAUNA_VENT)
    );
    public static final RegistryObject<BlockEntityType<?>> INCUBATOR = REGISTER.register(
            "incubator", makeType(IncubatorTileEntity::new, FHBlocks.INCUBATOR)
    );
    public static final RegistryObject<BlockEntityType<?>> INCUBATOR2 = REGISTER.register(
            "heat_incubator", makeType(HeatIncubatorTileEntity::new, FHBlocks.HEAT_INCUBATOR)
    );
    public static final RegistryObject<BlockEntityType<HouseBlockEntity>> HOUSE = REGISTER.register(
            "house", makeType(HouseBlockEntity::new, FHBlocks.HOUSE)
    );
    public static final RegistryObject<BlockEntityType<WarehouseBlockEntity>> WAREHOUSE = REGISTER.register(
            "warehouse", makeType(WarehouseBlockEntity::new, FHBlocks.WAREHOUSE)
    );
    public static final RegistryObject<BlockEntityType<MineBlockEntity>> MINE = REGISTER.register(
            "mine", makeType(MineBlockEntity::new, FHBlocks.MINE)
    );
    public static final RegistryObject<BlockEntityType<MineBaseBlockEntity>> MINE_BASE = REGISTER.register(
            "mine_base", makeType(MineBaseBlockEntity::new, FHBlocks.MINE_BASE)
    );
    public static final RegistryObject<BlockEntityType<HuntingCampBlockEntity>> HUNTING_CAMP = REGISTER.register(
            "hunting_camp", makeType(HuntingCampBlockEntity::new, FHBlocks.HUNTING_CAMP)
    );
    public static final RegistryObject<BlockEntityType<HuntingBaseBlockEntity>> HUNTING_BASE = REGISTER.register(
            "hunting_base", makeType(HuntingBaseBlockEntity::new, FHBlocks.HUNTING_BASE)
    );
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> makeType(BlockEntitySupplier<T> create, Supplier<Block> valid) {
        return makeTypeMultipleBlocks(create, () -> ImmutableSet.of(valid.get()));
    }
    @SafeVarargs
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> makeType(BlockEntitySupplier<T> create, Supplier<Block>... valid) {
        return makeTypeMultipleBlocks(create, () -> Arrays.stream(valid).map(Supplier::get).collect(Collectors.toList()));
    }
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> makeTypeMultipleBlocks(BlockEntitySupplier<T> create, Supplier<Collection<Block>> valid) {
        return () -> new BlockEntityType<>(create, ImmutableSet.copyOf(valid.get()), null);
    }

}