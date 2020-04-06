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

package icyllis.modernui.gui.option;

import com.mojang.blaze3d.systems.RenderSystem;
import icyllis.modernui.font.FontTools;
import icyllis.modernui.font.IFontRenderer;
import icyllis.modernui.gui.master.DrawTools;
import icyllis.modernui.gui.master.IMouseListener;
import icyllis.modernui.gui.module.SettingResourcePack;
import icyllis.modernui.gui.util.Color3I;
import icyllis.modernui.system.ModernUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ResourcePackEntry implements IMouseListener {

    private final IFontRenderer fontRenderer = FontTools.FONT_RENDERER;

    private final TextureManager textureManager = Minecraft.getInstance().getTextureManager();

    private final ClientResourcePackInfo resourcePack;

    private final SettingResourcePack module;

    private float x1, y1;

    private float x2, y2;

    private float width;

    private final float height;

    private boolean mouseHovered = false;

    private String title = "";

    private String[] desc = new String[0];

    public ResourcePackEntry(SettingResourcePack module, ClientResourcePackInfo resourcePack) {
        this.module = module;
        this.resourcePack = resourcePack;
        this.height = ResourcePackGroup.ENTRY_HEIGHT;
    }

    public final void setPos(float x1, float x2, float y) {
        this.x1 = x1;
        this.x2 = x2;
        this.width = x2 - x1;
        this.y1 = y;
        this.y2 = y + height;
        updateTitle();
        updateDescription();
    }

    public final void draw() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        if (module.getHighlight() == this) {
            RenderSystem.disableTexture();
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x1 + 1, y2, 0.0D).color(128, 128, 128, 96).endVertex();
            bufferBuilder.pos(x2 - 7, y2, 0.0D).color(128, 128, 128, 96).endVertex();
            bufferBuilder.pos(x2 - 7, y1, 0.0D).color(128, 128, 128, 96).endVertex();
            bufferBuilder.pos(x1 + 1, y1, 0.0D).color(128, 128, 128, 96).endVertex();
            tessellator.draw();
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            GL11.glLineWidth(1.0F);
            bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x1 + 1, y2, 0.0D).color(255, 255, 255, 224).endVertex();
            bufferBuilder.pos(x2 - 7, y2, 0.0D).color(255, 255, 255, 224).endVertex();
            bufferBuilder.pos(x2 - 7, y1, 0.0D).color(255, 255, 255, 224).endVertex();
            bufferBuilder.pos(x1 + 1, y1, 0.0D).color(255, 255, 255, 224).endVertex();
            tessellator.draw();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            RenderSystem.enableTexture();
        } else if (mouseHovered) {
            RenderSystem.disableTexture();
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            GL11.glLineWidth(1.0F);
            bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x1 + 1, y2, 0.0D).color(224, 224, 224, 180).endVertex();
            bufferBuilder.pos(x2 - 7, y2, 0.0D).color(224, 224, 224, 180).endVertex();
            bufferBuilder.pos(x2 - 7, y1, 0.0D).color(224, 224, 224, 180).endVertex();
            bufferBuilder.pos(x1 + 1, y1, 0.0D).color(224, 224, 224, 180).endVertex();
            tessellator.draw();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            RenderSystem.enableTexture();
        }

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        bindTexture();
        DrawTools.blitIcon(x1 + 3, y1 + 2, 32, 32);

        fontRenderer.drawString(title, x1 + 39, y1 + 4);
        int i = 0;
        for (String d : desc) {
            fontRenderer.drawString(d, x1 + 39, y1 + 14 + i * 10, Color3I.GRAY);
            i++;
            if (i > 1) {
                break;
            }
        }
    }

    public void updateTitle() {
        title = resourcePack.getTitle().getFormattedText();
        if (!resourcePack.getCompatibility().isCompatible()) {
            title = TextFormatting.DARK_RED + "(" + I18n.format("resourcePack.incompatible") + ") " + TextFormatting.RESET + title;
        }
        float w = fontRenderer.getStringWidth(title);
        float cw = width - 45;
        if (w > cw) {
            float kw = cw - fontRenderer.getStringWidth("...");
            title = fontRenderer.trimStringToWidth(title, kw, false) + "...";
        }

    }

    public void updateDescription() {
        this.desc = FontTools.splitStringToWidth(resourcePack.getDescription().getFormattedText(), width - 45);
    }

    public void bindTexture() {
        resourcePack.func_195808_a(textureManager);
    }

    public boolean canIntoSelected() {
        return !module.getSelectedGroup().getEntries().contains(this);
    }

    public boolean canIntoAvailable() {
        return module.getSelectedGroup().getEntries().contains(this) && !resourcePack.isAlwaysEnabled();
    }

    public boolean canGoUp() {
        List<ResourcePackEntry> list = module.getSelectedGroup().getEntries();
        int i = list.indexOf(this);
        return i > 0 && !(list.get(i - 1)).resourcePack.isOrderLocked();
    }

    public boolean canGoDown() {
        List<ResourcePackEntry> list = module.getSelectedGroup().getEntries();
        int i = list.indexOf(this);
        return i >= 0 && i < list.size() - 1 && !(list.get(i + 1)).resourcePack.isOrderLocked();
    }

    public ClientResourcePackInfo getResourcePack() {
        return resourcePack;
    }

    public final void intoSelected(ResourcePackGroup selectedGroup) {
        resourcePack.getPriority().insert(selectedGroup.getEntries(), this, ResourcePackEntry::getResourcePack, true);
    }

    public void goUp() {
        List<ResourcePackEntry> list = module.getSelectedGroup().getEntries();
        int i = list.indexOf(this);
        list.remove(i);
        list.add(i - 1, this);
    }

    public void goDown() {
        List<ResourcePackEntry> list = module.getSelectedGroup().getEntries();
        int i = list.indexOf(this);
        list.remove(i);
        list.add(i + 1, this);
    }

    @Override
    public boolean updateMouseHover(double mouseX, double mouseY) {
        boolean prev = mouseHovered;
        mouseHovered = isMouseInArea(mouseX, mouseY);
        if (prev != mouseHovered) {
            if (mouseHovered) {
                onMouseHoverEnter();
            } else {
                onMouseHoverExit();
            }
        }
        return mouseHovered;
    }

    private boolean isMouseInArea(double mouseX, double mouseY) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    @Override
    public final void setMouseHoverExit() {
        if (mouseHovered) {
            mouseHovered = false;
            onMouseHoverExit();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            module.setHighlight(this);
            return true;
        }
        return false;
    }

    private void onMouseHoverEnter() {

    }

    private void onMouseHoverExit() {

    }

    @Override
    public final boolean isMouseHovered() {
        return mouseHovered;
    }
}