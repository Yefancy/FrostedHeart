/*
 * Copyright (c) 2024 TeamMoeg
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

package com.teammoeg.frostedheart.world.flora;

import com.google.common.collect.Lists;
import com.teammoeg.frostedheart.world.FHSurfaceBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

public class FrozenForestBiome {

    /**
     * 构建生物群系并返回Biome对象。
     *
     * @return Biome对象
     */
    public final Biome build() {
        // 创建一个Biome.Builder对象，用于构建生物群系
        Biome.BiomeBuilder biomeBuilder = new Biome.BiomeBuilder();

        // 设置生物群系的降水类型、类别、深度、大小、温度和降雨量
        biomeBuilder.precipitation(Biome.Precipitation.SNOW)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .depth(1.0F)
                .scale(0.1F)
                .temperature(0.1F)
                .downfall(1.0F)

                // 设置生物群系的环境效果，如水的颜色、雾的颜色、雾的颜色、天空颜色、心情音效和粒子效果
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(calculateSkyColor(0.8F))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.068093334F))
                        .build());

        // 创建一个BiomeGenerationSettings.Builder对象，用于构建生成的生物群系的设置
        BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder();

        // 调用Generation方法，该方法将生成物群系生成器的所有设置添加到biomeGenBuilder中
        this.Generation(biomeGenBuilder);

        // 将生成的生物群系设置添加到biomeBuilder中
        biomeBuilder.generationSettings(biomeGenBuilder.build());

        // 创建一个MobSpawnInfo.Builder对象，用于构建生物群系的生物生成设置
        MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();

        // 调用MobSpawn方法，该方法将生物群系的所有生物生成设置添加到mobSpawnBuilder中
        this.MobSpawn(mobSpawnBuilder);

        // 将生物群系的生物生成设置添加到biomeBuilder中
        biomeBuilder.mobSpawnSettings(mobSpawnBuilder.build());

        // 构建并返回最终的Biome对象
        return biomeBuilder.build();
    }


    public int calculateSkyColor(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = Mth.clamp(lvt_1_1_, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public void Generation(BiomeGenerationSettings.Builder builder) {

        builder.surfaceBuilder(FHSurfaceBuilder.FROZEN_FOREST);

        // 添加雪地森林的生成特征
        ConfiguredFeature<?, ?> BARETREE = Feature.TREE.configured((
                new TreeConfiguration.TreeConfigurationBuilder(
                        new SimpleStateProvider(Blocks.OAK_LOG.defaultBlockState()),
                        new SimpleStateProvider(Blocks.AIR.defaultBlockState()),
                        new PineFoliagePlacer(
                                UniformInt.fixed(3),
                                UniformInt.fixed(2),
                                UniformInt.fixed(1)),
                        new ForkingTrunkPlacer(3, 2, 1),
                        new TwoLayersFeatureSize(2, 0, 2))
        ).ignoreVines().build()).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(9);

        builder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                BARETREE
        );
    }

    public void MobSpawn(MobSpawnSettings.Builder builder) {

        BiomeDefaultFeatures.snowySpawns(builder);
    }

}
