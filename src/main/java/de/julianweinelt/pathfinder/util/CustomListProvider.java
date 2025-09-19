package de.julianweinelt.pathfinder.util;

import de.julianweinelt.pathfinder.PathFinderAPI;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancementManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CustomListProvider {
    public static void init() {
        PathFinderAPI.registerCustomList("advancements", new CustomListResult() {
            @Override
            public List<String> completions() {
                Minecraft mc = Minecraft.getMinecraft();
                ClientAdvancementManager manager = mc.player.connection.getAdvancementManager();
                List<String> list = new ArrayList<>();
                for (Advancement a : manager.getAdvancementList().getAdvancements()) {
                    list.add(getName(a));
                }
                return list;
            }

            private String getName(Advancement ad) {
                return ad.getId().getResourceDomain() + ":" + ad.getId().getResourcePath();
            }
        });
    }
}
