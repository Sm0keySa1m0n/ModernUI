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

package icyllis.modernui.impl;

import icyllis.modernui.gui.master.ModernUIScreen;
import icyllis.modernui.gui.module.GeneralSetting;
import icyllis.modernui.gui.module.IngameMenuHome;
import icyllis.modernui.gui.module.SettingHeader;

public class GuiIngameMenu extends ModernUIScreen {

    public GuiIngameMenu(boolean isFullMenu) {
        super(l -> {
            l.addModule(i -> i >= 30 && i < 50, SettingHeader::new);
            l.addModule(i -> i == 31, GeneralSetting::new);
            l.addModule(i -> true, IngameMenuHome::new);
        });
    }
}
