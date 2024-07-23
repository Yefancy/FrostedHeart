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

import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

public class FHTags {
    public static final class Blocks {
//        public static final ITag.INamedTag<Block> ALWAYS_BREAKABLE = create("always_breakable");
        public static final Tags.IOptionalNamedTag<Block> DECORATIONS = tag("decorations");
        public static final Tags.IOptionalNamedTag<Block> WALL_BLOCKS = tag("wall_blocks");

        private static Tag.Named<Block> create(String id) {
            return BlockTags.bind(new ResourceLocation(FHMain.MODID, id).toString());
        }

        private static Tags.IOptionalNamedTag<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation(FHMain.MODID, name));
        }
    }
}
