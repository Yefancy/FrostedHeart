package com.teammoeg.frostedheart.content.tips.client.waypoint;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teammoeg.frostedheart.content.tips.client.gui.widget.IconButton;
import com.teammoeg.frostedheart.content.tips.client.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;

public class Waypoint {
    protected static final Minecraft MC = Minecraft.getInstance();
    public String id;
    public Vector3f target;
    public boolean focus;
    public boolean visible;
    public int color;

    public Waypoint(Vector3f target, String ID, int color) {
        this.id = ID;
        this.target = target;
        this.color = color;
        this.focus = false;
        this.visible = true;
    }

    public void render(PoseStack ms) {
        if (!this.visible) return;

        Vec2 screenPos = GuiUtil.worldPosToScreenPos(this.target);
        double x = Mth.clamp(screenPos.x, 10, MC.getWindow().getGuiScaledWidth()-10);
        double y = Mth.clamp(screenPos.y, 10, MC.getWindow().getGuiScaledHeight()-10);
        double xP = x / MC.getWindow().getGuiScaledWidth();
        double yP = y / MC.getWindow().getGuiScaledHeight();

        ms.pushPose();
        ms.translate(x, y, 0);
        if (xP >= 0.45 && xP <= 0.55 && yP >= 0.45 && yP <= 0.55) {
            renderText(ms);
        }
        renderMain(ms);
        ms.popPose();
    }

    public void renderMain(PoseStack ms) {
        if (this.focus) {
            GuiUtil.renderIcon(ms, IconButton.ICON_BOX_ON, -5, -5, this.color);
        } else {
            GuiUtil.renderIcon(ms, IconButton.ICON_BOX, -5, -5, this.color);
        }
    }

    public void renderText(PoseStack ms) {
        float textWidth = MC.font.width(this.id)*0.5F;
        GuiComponent.fill(ms, (int)(-textWidth)-2, -19, (int)(textWidth)+2, -7, 0x80000000);
        MC.font.draw(ms, this.id, -textWidth, -17, this.color);

        if (MC.player.isShiftKeyDown()) {
            Vec3 v = new Vec3(this.target);
            String distance = "Distance: " + (int)v.distanceTo(MC.player.position()) + " blocks";
            float textWidth1 = MC.font.width(distance)*0.5F;
            GuiComponent.fill(ms, (int)(-textWidth1)-2, 8, (int)(textWidth1)+2, 20, 0x80000000);
            MC.font.draw(ms, distance, -textWidth1, 10, this.color);

            String pos = "[X: %.2f, Y: %.2f, Z: %.2f]";
            pos = String.format(pos, this.target.x(), this.target.y(), this.target.z());
            float textWidth2 = MC.font.width(pos)*0.5F;
            GuiComponent.fill(ms, (int)(-textWidth2)-2, 20, (int)(textWidth2)+2, 32, 0x80000000);
            MC.font.draw(ms, pos, -textWidth2, 22, this.color);
        }
    }

    @Override
    public String toString() {
        return "ID: '" + id + "' [" + target.x() + ", " + target.y() + ", " + target.z() + "]";
    }
}
