package de.julianweinelt.pathfinder.gui;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;

@Mod.EventBusSubscriber
public class MainMenuHandler {

    @SubscribeEvent
    public static void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiMainMenu) {
            GuiMainMenu menu = (GuiMainMenu) event.getGui();

            int buttonWidth = 120;
            int buttonHeight = 20;
            int x = menu.width / 2 - buttonWidth / 2;
            int y = menu.height / 4;

            event.getButtonList().add(new GuiButton(999, x, y, buttonWidth, buttonHeight, "Pathfinder"));
        }
    }

    @SubscribeEvent
    public static void onGuiButtonPress(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiMainMenu) {
            GuiButton button = event.getButton();
            if (button.id == 999) {
                event.getGui().mc.displayGuiScreen(new PathFinderConsentMenu(event.getGui()));
            }
        }
    }
}
