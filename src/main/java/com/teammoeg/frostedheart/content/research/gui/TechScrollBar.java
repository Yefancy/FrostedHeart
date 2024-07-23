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

package com.teammoeg.frostedheart.content.research.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.PanelScrollBar;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.WidgetType;

import dev.ftb.mods.ftblibrary.ui.ScrollBar.Plane;

public class TechScrollBar extends PanelScrollBar {
    // Have to do this hack since FTBL fields are private.
    private static final Theme dtheme = new Theme() {
        @Override
        public void drawScrollBar(PoseStack matrixStack, int x, int y, int w, int h, WidgetType type,
                                  boolean vertical) {
            GuiHelper.setupDrawing();
            TechIcons.drawTexturedRect(matrixStack, x, y, w, h, type != WidgetType.MOUSE_OVER);
        }
    };

    boolean isHidden = false;

    public TechScrollBar(Panel parent, Panel panel) {
        super(parent, panel);
    }

    public TechScrollBar(Panel parent, Plane plane, Panel p) {
        super(parent, plane, p);
    }

    @Override
    public boolean canMouseScroll() {
        return super.canMouseScroll() && panel.isEnabled() && !isHidden;
    }

    @Override
    public void drawBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
        if (!isHidden) {
            GuiHelper.setupDrawing();
            TechIcons.SLIDER_FRAME.draw(matrixStack, x, y, w, h);
        }
    }

    @Override
    public void drawScrollBar(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
        if (!isHidden)
            super.drawScrollBar(matrixStack, dtheme, x + 1, y + 1, w - 2, h - 2);
    }

    public void hide() {
        this.isHidden = true;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void unhide() {
        this.isHidden = false;
    }
}
