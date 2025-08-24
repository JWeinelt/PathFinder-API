package de.julianweinelt.pathfinder.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PathFinderConsentMenu extends GuiScreen {

    private final List<GuiButton> buttons = new ArrayList<>();
    private final GuiScreen parent;

    public PathFinderConsentMenu(GuiScreen parent) {
        this.parent = parent;
    }

    private ResourceLocation getTexture(String name) {
        return new ResourceLocation("pathfinder", String.format("textures/gui/%s.png", name));
    }

    @Override
    public void initGui() {
        buttons.clear();
        buttons.add(new GuiButton(0, width / 2 - 80, height - 80, 60, 20, "Okay"));
        buttons.add(new GuiButton(1, width / 2 + 20, height - 80, 60, 20, "Back"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString(fontRenderer, "Welcome to PathFinder API!", width / 2, 10, 0xFFFFFF);
        drawCenteredString(fontRenderer, "Please acknowledge that opening the following",  width / 2, 40, 0xFFFFFF);
        drawCenteredString(fontRenderer, "menu will make you able to download JSON files",  width / 2, 55, 0xFFFFFF);
        drawCenteredString(fontRenderer, "from an external server.",  width / 2, 70, 0xFFFFFF);
        drawCenteredString(fontRenderer, "These files contain command data used by PathFinder.",  width / 2, 90, 0xFFFFFF);
        drawCenteredString(fontRenderer, "If you do not want to download this data, click back.",  width / 2, 105, 0xFFFFFF);
        drawCenteredString(fontRenderer, "You need to download command data to use the command",  width / 2, 120, 0xFFFFFF);
        drawCenteredString(fontRenderer, "suggestions in chat.",  width / 2, 135, 0xFFFFFF);

        for (GuiButton button : buttons) {
            button.drawButton(mc, mouseX, mouseY, partialTicks);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (GuiButton button : buttons) {
            if (button.mousePressed(mc, mouseX, mouseY)) {
                actionPerformed(button);
            }
        }

        //listSlot.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int index = button.id;
        if (index == 0) {
            Minecraft.getMinecraft().displayGuiScreen(new PathFinderMenu(parent));
        }
        if (index == 1) {
            Minecraft.getMinecraft().displayGuiScreen(parent);
        }
    }

    private GuiButton getButtonByID(int id) {
        for (GuiButton button : buttons) {
            if (button.id == id) return button;
        }
        return null;
    }
    private boolean executeForButton(int id, Consumer<GuiButton> consumer) {
        GuiButton b = getButtonByID(id);
        if (b != null) {
            consumer.accept(b);
            return true;
        }
        return false;
    }
}
