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

package com.teammoeg.frostedheart.compat.jei;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

//borrowed from create
public class StaticBlock extends AnimatedKinetics {
    BlockState bs;

    public StaticBlock(BlockState bs) {
        this.bs = bs;
    }

    @Override
    public void draw(GuiGraphics matrixStack, int xOffset, int yOffset) {
        matrixStack.pose().pushPose();
        matrixStack.pose().translate(xOffset, yOffset, 0);
        matrixStack.pose().translate(0, 0, 200);
        matrixStack.pose().translate(2, 22, 0);
        matrixStack.pose().mulPose(new Quaternionf().rotationX(-15.5f/180*Mth.PI));
        matrixStack.pose().mulPose(new Quaternionf().rotationY((22.5f + 90)/180*Mth.PI));
        int scale = 30;

        blockElement(bs)
                .rotateBlock(0, 0, 0)
                .scale(scale)
                .render(matrixStack);

        matrixStack.pose().popPose();
    }

}
