package com.teammoeg.frostedheart.research.gui;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.frostedheart.research.effects.Effect;
import com.teammoeg.frostedheart.research.gui.FHIcons.FHIcon;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.util.text.ITextComponent;

public class EffectWidget extends Widget {
	List<ITextComponent> tooltips;
	ITextComponent title;
	FHIcon icon;
	public EffectWidget(Panel panel,Effect e) {
		super(panel);
		tooltips=e.getTooltip();
		title=e.getName();
		icon=e.getIcon();
		this.setSize(16,16);
	}
	/*public boolean checkMouseOver(int mouseX, int mouseY) {
		if (parent == null) {
			return true;
		} else if (!parent.isMouseOver()) {
			return false;
		}

		int ax = getX();
		int ay = getY();
		return mouseX >= ax && mouseY >= ay+4 && mouseX < ax + width-8 && mouseY < ay + height-8;
	}*/
	@Override
	public void addMouseOverText(TooltipList list) {
		list.add(title);
		tooltips.forEach(list::add);
	}

	@Override
	public void draw(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h) {
		GuiHelper.setupDrawing();
		DrawDeskIcons.SLOT.draw(matrixStack, x-4, y-4, 24,24);
		icon.draw(matrixStack, x, y, w, h);
	}





}