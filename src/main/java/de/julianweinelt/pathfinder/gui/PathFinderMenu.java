package de.julianweinelt.pathfinder.gui;

import de.julianweinelt.pathfinder.command.CommandParser;
import de.julianweinelt.pathfinder.saving.ConfigManager;
import de.julianweinelt.pathfinder.util.FileDownloader;
import de.julianweinelt.pathfinder.util.ToastHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PathFinderMenu extends GuiScreen {

    private PathFinderListSlot listSlot;
    private List<GuiButton> buttons = new ArrayList<>();
    private final GuiScreen parent;

    public PathFinderMenu(GuiScreen parent) {
        this.parent = parent;
    }

    private ResourceLocation getTexture(String name) {
        return new ResourceLocation("pathfinder", String.format("textures/gui/%s.png", name));
    }

    @Override
    public void initGui() {
        listSlot = new PathFinderListSlot(this, mc, width, height, 40, height - 40, 24);

        buttons.clear();
        int index = 0;
        for (PathFinderListSlot.ListItem item : listSlot.items) {
            int buttonX = width - 45;
            int buttonY = 40 + index * 24;
            buttons.add(new GuiIconButton(1000 + index, buttonX, buttonY, 20, 20,
                    getTexture("reload"), 20, 20, "Reload", true,
                    item.namespace));
            buttons.add(new GuiIconButton(2000 + index, buttonX - 30, buttonY, 20, 20,
                    getTexture("download"), 20, 20, "Install", false, item.namespace));
            //buttons.add(new GuiButton(1000 + index, buttonX, buttonY, 80, 20, "Download"));
            index++;
        }
        buttons.add(new GuiButton(0, 20, height - 30, 60, 20, "Back"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawCenteredString(fontRenderer, "Pathfinder", width / 2, 10, 0xFFFFFF);

        listSlot.drawScreen(mouseX, mouseY, partialTicks);

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
        listSlot.handleMouseInput();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        int index = button.id;
        if (index == 0) {
            Minecraft.getMinecraft().displayGuiScreen(parent);
        }
        if (index - 1000 >= 0 && index - 1000 < listSlot.items.size()) {
            // Reload buttons
            PathFinderListSlot.ListItem item = listSlot.items.get(index - 1000);

        } else if (index - 2000 >= 0 && index - 2000 < listSlot.items.size()) {
            // Download/delete buttons
            PathFinderListSlot.ListItem item = listSlot.items.get(index - 2000);
            if (button instanceof GuiIconButton) {
                GuiIconButton iconButton = (GuiIconButton) button;
                if (iconButton.getIconTexture().getResourcePath().contains("delete")) {
                    File dataFile = new File(ConfigManager.getConfigFolder(), item.namespace + ".json");
                    if (dataFile.exists()) if (dataFile.delete()) {
                        ((GuiIconButton) button).setIconTexture(getTexture("download"));
                    }
                } else {
                    button.enabled = false;
                    FileDownloader.downloadPathfinderJSON(item.namespace).thenAccept(downloaded -> {
                        button.enabled = true;
                        if (downloaded) {
                            executeForButton(index + 1000, b -> b.enabled = true);
                            ((GuiIconButton) button).setIconTexture(getTexture("delete"));
                        } else ToastHelper.showToast("Failed to download", "No commands available");
                    });
                }
            }
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

    public static class PathFinderListSlot extends GuiSlot {

        private final PathFinderMenu parent;
        private final List<ListItem> items = new ArrayList<>();

        public PathFinderListSlot(PathFinderMenu parent, net.minecraft.client.Minecraft mcIn, int width, int height, int top, int bottom, int slotHeight) {
            super(mcIn, width, height, top, bottom, slotHeight);
            this.parent = parent;

            for (String namespace : CommandParser.getNameSpacesFiltered()) {
                items.add(new ListItem(namespace, false, CommandParser.checkInstalled(namespace)));
            }
        }

        @Override
        protected int getSize() {
            return items.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {}

        @Override
        protected boolean isSelected(int index) {
            return false;
        }

        @Override
        protected void drawBackground() {
            parent.drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int entryID, int x, int y, int slotHeight, int mouseXIn, int mouseYIn, float partialTicks) {
            ListItem item = items.get(entryID);

            // Namespace links
            parent.fontRenderer.drawString(item.namespace, x + 10, y + 6, 0xFFFFFF);

            // Rotes Ausrufezeichen
            if (item.showAlert) {
                parent.fontRenderer.drawString(TextFormatting.RED + "!", x + 150, y + 6, 0xFF0000);
            }
        }

        public static class ListItem {
            public String namespace;
            public boolean showAlert;
            public boolean installed;

            public ListItem(String namespace, boolean showAlert, boolean installed) {
                this.namespace = namespace;
                this.showAlert = showAlert;
                this.installed = installed;
            }
        }
    }
}
