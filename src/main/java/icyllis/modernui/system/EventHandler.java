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

import icyllis.modernui.graphics.renderer.RenderTools;
import icyllis.modernui.ui.example.ContainerTest;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * Listens global common events
 */
@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    static void rightClickItem(@Nonnull PlayerInteractEvent.RightClickItem event) {
        if (Config.isDeveloperMode()) {
            if (event.getItemStack().getItem() == Items.DIAMOND) {
                if (event.getSide().isServer()) {
                    NetworkHooks.openGui((ServerPlayerEntity) event.getPlayer(), new ContainerTest.Provider());
                }
            }
        }
    }

    /*@SubscribeEvent
    static void onContainerClosed(PlayerContainerEvent.Close event) {

    }*/

    /**
     * Listens global client events
     */
    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(Dist.CLIENT)
    static class Client {

        @SubscribeEvent
        static void onPlayerLogin(@Nonnull ClientPlayerNetworkEvent.LoggedInEvent event) {
            ClientPlayerEntity playerEntity = event.getPlayer();
            if (playerEntity != null && RenderTools.glCapabilitiesErrors > 0) {
                playerEntity.sendMessage(new StringTextComponent("[Modern UI] There are " + RenderTools.glCapabilitiesErrors +
                        " GL capabilities that are not supported by your GPU, see debug.log for detailed info")
                        .mergeStyle(TextFormatting.RED), null);
            }
        }

        /*@SubscribeEvent(receiveCanceled = true)
        static void onGuiOpen(@Nonnull GuiOpenEvent event) {

        }

        @SubscribeEvent
        static void onRenderTick(@Nonnull TickEvent.RenderTickEvent event) {

        }

        @SubscribeEvent
        static void onClientTick(@Nonnull TickEvent.ClientTickEvent event) {

        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {

        }

        @SubscribeEvent
        static void onGuiInit(GuiScreenEvent.InitGuiEvent event) {

        }

        @SubscribeEvent
        public static void onScreenStartMouseClicked(@Nonnull GuiScreenEvent.MouseClickedEvent.Pre event) {

        }

        @SubscribeEvent
        public static void onScreenStartMouseReleased(@Nonnull GuiScreenEvent.MouseReleasedEvent.Pre event) {

        }

        @SubscribeEvent
        public static void onScreenStartMouseDragged(@Nonnull GuiScreenEvent.MouseDragEvent.Pre event) {

        }*/
    }

    /*@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {

        @SubscribeEvent
        public static void setupCommon(FMLCommonSetupEvent event) {

        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent event) {

        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerSounds(@Nonnull RegistryEvent.Register<SoundEvent> event) {

        }

        @SubscribeEvent
        public static void registerContainers(@Nonnull RegistryEvent.Register<ContainerType<?>> event) {

        }

        @SubscribeEvent
        public static void onConfigChange(@Nonnull ModConfig.ModConfigEvent event) {

        }

        @SubscribeEvent
        public static void onLoadComplete(FMLLoadCompleteEvent event) {

        }
    }*/
}
