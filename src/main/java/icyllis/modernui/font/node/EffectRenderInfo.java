/*
 * Modern UI.
 * Copyright (C) 2019-2020 BloCamLimb. All rights reserved.
 *
 * Modern UI is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Modern UI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Modern UI. If not, see <https://www.gnu.org/licenses/>.
 */

package icyllis.modernui.font.node;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import icyllis.modernui.font.process.FormattingStyle;

import javax.annotation.Nonnull;

/**
 * Draw underline or strikethrough
 */
public class EffectRenderInfo {

    /**
     * Underline style type
     */
    public static final byte UNDERLINE     = 0;
    /**
     * Strikethrough style type
     */
    public static final byte STRIKETHROUGH = 1;

    /**
     * Offset from the string's baseline as which to draw the underline
     */
    private static final float UNDERLINE_OFFSET     = 1.5f;
    /**
     * Offset from the string's baseline as which to draw the strikethrough line
     */
    private static final float STRIKETHROUGH_OFFSET = -3.0f;

    /**
     * Thickness of the underline
     */
    private static final float UNDERLINE_THICKNESS     = 1.0f;
    /**
     * Thickness of the strikethrough line
     */
    private static final float STRIKETHROUGH_THICKNESS = 1.0f;

    /**
     * Offset Z to ensure that effects render over characters in 3D world
     */
    public static final float EFFECT_DEPTH = 0.01f;


    /**
     * Start X offset of this effect to the start x of the whole text
     */
    protected final float start;

    /**
     * End X offset of this effect to the start x of the whole text
     */
    protected final float end;

    /**
     * The color in 0xRRGGBB format, or {@link FormattingStyle#NO_COLOR}
     */
    protected final int color;

    /**
     * Either {@link #UNDERLINE} or {@link #STRIKETHROUGH}
     */
    protected final byte type;

    public EffectRenderInfo(float start, float end, int color, byte type) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.type = type;
    }

    public void drawEffect(@Nonnull IVertexBuilder builder, float x, float y, int r, int g, int b, int a) {
        if (color != -1) {
            r = color >> 16 & 0xff;
            g = color >> 8 & 0xff;
            b = color & 0xff;
        }
        if (type == UNDERLINE) {
            y += UNDERLINE_OFFSET;
            builder.pos(x + start, y + UNDERLINE_THICKNESS, EFFECT_DEPTH).color(r, g, b, a).endVertex();
            builder.pos(x + end, y + UNDERLINE_THICKNESS, EFFECT_DEPTH).color(r, g, b, a).endVertex();
            builder.pos(x + end, y, EFFECT_DEPTH).color(r, g, b, a).endVertex();
            builder.pos(x + start, y, EFFECT_DEPTH).color(r, g, b, a).endVertex();
        } else if (type == STRIKETHROUGH) {
            y += STRIKETHROUGH_OFFSET;
            builder.pos(x + start, y + STRIKETHROUGH_THICKNESS, EFFECT_DEPTH).color(r, g, b, a).endVertex();
            builder.pos(x + end, y + STRIKETHROUGH_THICKNESS, EFFECT_DEPTH).color(r, g, b, a).endVertex();
            builder.pos(x + end, y, EFFECT_DEPTH).color(r, g, b, a).endVertex();
            builder.pos(x + start, y, EFFECT_DEPTH).color(r, g, b, a).endVertex();
        }
    }

    @Nonnull
    public static EffectRenderInfo underline(float start, float end, int color) {
        return new EffectRenderInfo(start, end, color, UNDERLINE);
    }

    @Nonnull
    public static EffectRenderInfo strikethrough(float start, float end, int color) {
        return new EffectRenderInfo(start, end, color, STRIKETHROUGH);
    }
}