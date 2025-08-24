package de.julianweinelt.pathfinder.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.TextComponentString;

public class ToastHelper {

    public static void showToast(String title, String message) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiToast guiToast = mc.getToastGui();

        // SystemToast Typ: NOTICE, TASK, etc.
        SystemToast toast = new SystemToast(SystemToast.Type.TUTORIAL_HINT,
                new TextComponentString(title),
                new TextComponentString(message));

        guiToast.add(toast);
    }
}
