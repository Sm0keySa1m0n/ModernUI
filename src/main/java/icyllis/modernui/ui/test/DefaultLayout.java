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

package icyllis.modernui.ui.test;

@Deprecated
public class DefaultLayout implements ILayout {

    public static final DefaultLayout INSTANCE = new DefaultLayout();

    private DefaultLayout() {

    }

    @Override
    public int getLayoutX(IViewRect prev, IViewRect parent) {
        return parent.getWidth() >> 1;
    }

    @Override
    public int getLayoutY(IViewRect prev, IViewRect parent) {
        return parent.getWidth() >> 1;
    }

    @Override
    public int getLayoutWidth(IViewRect prev, IViewRect parent) {
        return 16;
    }

    @Override
    public int getLayoutHeight(IViewRect prev, IViewRect parent) {
        return 16;
    }
}
