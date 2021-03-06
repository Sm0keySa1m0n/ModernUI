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

package icyllis.modernui.system;

import net.minecraft.client.Minecraft;
import net.optifine.shaders.gui.GuiShaders;

import java.lang.reflect.InvocationTargetException;

public class ModIntegration {

    public static boolean optifineLoaded;

    public static void init() {
        try {
            Class<?> clazz = Class.forName("optifine.Installer");
            String ver = (String) clazz.getMethod("getOptiFineVersion").invoke(null);
            optifineLoaded = true;
            ModernUI.LOGGER.debug(ModernUI.MARKER, "OptiFine Loaded : {}", ver);
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static class OptiFine {

        public static void openShadersGui() {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.displayGuiScreen(new GuiShaders(minecraft.currentScreen, minecraft.gameSettings));
        }
    }
}
