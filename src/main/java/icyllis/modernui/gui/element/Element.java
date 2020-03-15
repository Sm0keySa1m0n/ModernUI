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

import icyllis.modernui.api.manager.IModuleManager;
import icyllis.modernui.font.FontRendererTools;
import icyllis.modernui.font.IFontRenderer;
import icyllis.modernui.gui.master.GlobalModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

import java.util.function.Function;

public abstract class Element implements IElement {

    protected Minecraft minecraft = Minecraft.getInstance();

    protected TextureManager textureManager = minecraft.textureManager;

    protected IFontRenderer fontRenderer = FontRendererTools.CURRENT_RENDERER;

    protected IModuleManager moduleManager = GlobalModuleManager.INSTANCE;

    /**
     * Change X/Y position when game window size changed
     */
    public Function<Integer, Float> xResizer, yResizer;

    /**
     * Logical X/Y to render
     */
    public float x, y;

    public Element(Function<Integer, Float> xResizer, Function<Integer, Float> yResizer) {
        this.xResizer = xResizer;
        this.yResizer = yResizer;
    }

    @Override
    public void resize(int width, int height) {
        x = xResizer.apply(width);
        y = yResizer.apply(height);
    }
}
