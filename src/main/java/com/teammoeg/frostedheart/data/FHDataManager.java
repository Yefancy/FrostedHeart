/*
 * Copyright (c) 2021 TeamMoeg
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
 */

package com.teammoeg.frostedheart.data;

import com.google.gson.JsonObject;
import com.teammoeg.frostedheart.climate.ITempAdjustFood;
import com.teammoeg.frostedheart.climate.IWarmKeepingEquipment;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class FHDataManager {
    public static class ResourceMap<T> extends HashMap<ResourceLocation, T> {
        public ResourceMap() {
            super();
        }

        public ResourceMap(int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
        }

        public ResourceMap(int initialCapacity) {
            super(initialCapacity);
        }

        public ResourceMap(Map<? extends ResourceLocation, ? extends T> m) {
            super(m);
        }
    }

    private FHDataManager() {
    }

    public static final ResourceMap<FoodTempData> foodData = new ResourceMap<>();
    public static final ResourceMap<ArmorTempData> armorData = new ResourceMap<>();
    public static final ResourceMap<BiomeTempData> biomeData = new ResourceMap<>();
    public static final ResourceMap<BlockTempData> blockData = new ResourceMap<>();
    public static final EnumMap<FHDataTypes, ResourceMap> datas = new EnumMap<>(FHDataTypes.class);

    static {
        datas.put(FHDataTypes.Armor, armorData);
        datas.put(FHDataTypes.Biome, biomeData);
        datas.put(FHDataTypes.Block, blockData);
        datas.put(FHDataTypes.Food, foodData);
    }

    @SuppressWarnings("unchecked")
    public static final void register(FHDataTypes dt, JsonObject data) {
        JsonDataHolder jdh = dt.type.create(data);
        //System.out.println("registering "+dt.type.location+": "+jdh.getId());
        datas.get(dt).put(jdh.getId(), jdh);
    }

    public static ITempAdjustFood getFood(ItemStack is) {
        return foodData.get(is.getItem().getRegistryName());
    }

    public static IWarmKeepingEquipment getArmor(ItemStack is) {
        //System.out.println(is.getItem().getRegistryName());
        return armorData.get(is.getItem().getRegistryName());
    }

    public static Byte getBiomeTemp(Biome b) {
        BiomeTempData data = biomeData.get(b.getRegistryName());
        if (data != null)
            return data.getTemp().byteValue();
        return null;
    }

    public static BlockTempData getBlockData(Block b) {
        return blockData.get(b.getRegistryName());
    }

}