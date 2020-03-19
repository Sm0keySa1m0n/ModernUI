/*
 * Modern UI.
 * Copyright (C) 2019 BloCamLimb. All rights reserved.
 *
 * Modern UI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Modern UI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Modern UI. If not, see <https://www.gnu.org/licenses/>.
 */

package icyllis.modernui.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import icyllis.modernui.gui.animation.Animation;
import icyllis.modernui.gui.animation.Applier;
import icyllis.modernui.gui.master.DrawTools;
import icyllis.modernui.gui.master.GlobalModuleManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class MenuHomeBG implements IElement {

    private float sizeW, height;

    public MenuHomeBG() {
        GlobalModuleManager.INSTANCE.addAnimation(
                new Animation(4, true)
                    .applyTo(new Applier(0, 32, value -> sizeW = value)));
    }

    @Override
    public void draw(float currentTime) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(0, height, 0.0D).color(0, 0, 0, 180).endVertex();
        bufferbuilder.pos(sizeW, height, 0.0D).color(0, 0, 0, 180).endVertex();
        bufferbuilder.pos(sizeW, 0, 0.0D).color(0, 0, 0, 180).endVertex();
        bufferbuilder.pos(0, 0, 0.0D).color(0, 0, 0, 180).endVertex();
        tessellator.draw();
        bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        GL11.glLineWidth(1.0F);
        bufferbuilder.pos(sizeW, 0, 0.0D).color(140, 140, 140, 220).endVertex();
        bufferbuilder.pos(sizeW, height, 0.0D).color(140, 140, 140, 220).endVertex();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    @Override
    public void resize(int width, int height) {
        this.height = height;
    }
}