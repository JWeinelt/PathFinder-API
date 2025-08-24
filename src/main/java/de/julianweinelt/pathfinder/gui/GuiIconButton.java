package de.julianweinelt.pathfinder.gui;

import de.julianweinelt.pathfinder.command.CommandParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collections;

public class GuiIconButton extends GuiButton {

    private ResourceLocation iconTexture;
    private final int iconWidth, iconHeight;
    private final String hoverText;
    private final boolean disabled;
    private String assignedNameSpace = "";

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/widgets.png");

    public GuiIconButton(int buttonId, int x, int y, int width, int height,
                         ResourceLocation iconTexture, int iconWidth, int iconHeight, String hoverText) {
        super(buttonId, x, y, width, height, "");
        this.iconTexture = iconTexture;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.hoverText = hoverText;
        disabled = false;
    }
    public GuiIconButton(int buttonId, int x, int y, int width, int height,
                         ResourceLocation iconTexture, int iconWidth, int iconHeight, String hoverText, boolean disabled) {
        super(buttonId, x, y, width, height, "");
        this.iconTexture = iconTexture;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.hoverText = hoverText;
        this.disabled = disabled;
    }

    public GuiIconButton(int buttonId, int x, int y, int width, int height,
                         ResourceLocation iconTexture, int iconWidth, int iconHeight, String hoverText, boolean disabled,
                         String assignedNameSpace) {
        super(buttonId, x, y, width, height, hoverText);
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.hoverText = hoverText;
        this.disabled = disabled;
        this.iconTexture = iconTexture;
        this.assignedNameSpace = assignedNameSpace;
    }

    public String getAssignedNameSpace() {
        return assignedNameSpace;
    }

    public void setIconTexture(ResourceLocation newIcon) {
        this.iconTexture = newIcon;
    }


    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) return;
        if (!CommandParser.checkInstalled(assignedNameSpace) && iconTexture.getResourcePath().contains("reload")) {
            enabled = false;

        }

        if (CommandParser.checkInstalled(assignedNameSpace) && iconTexture.getResourcePath().contains("download")) {
            iconTexture = new ResourceLocation(iconTexture.getResourceDomain(), 
                    iconTexture.getResourcePath().replace("download", "delete"));
        }

        mc.getTextureManager().bindTexture(BUTTON_TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        boolean hovered = mouseX >= this.x && mouseX <= this.x + this.width
                && mouseY >= this.y && mouseY <= this.y + this.height;

        int textureY;
        if (!this.enabled) textureY = 46;
        else textureY = hovered ? 86 : 66;

        drawTexturedModalRect(this.x, this.y, 0, textureY, this.width / 2, this.height);
        drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, textureY, this.width / 2, this.height);

        mc.getTextureManager().bindTexture(iconTexture);
        drawModalRectWithCustomSizedTexture(
                x + (width - iconWidth) / 2,
                y + (height - iconHeight) / 2,
                0, 0, iconWidth, iconHeight, iconWidth, iconHeight
        );

        if (mc.currentScreen == null) return;
        if (hovered && hoverText != null && !hoverText.isEmpty()) {
            mc.currentScreen.drawHoveringText(Collections.singletonList(hoverText), mouseX, mouseY);
        }
    }

    public ResourceLocation getIconTexture() {
        return iconTexture;
    }
}
