package de.julianweinelt.pathfinder.suggestion;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class CustomListPlayers implements CustomListProvider {
    @Override
    public List<String> onSuggest() {
        List<String> players = new ArrayList<>();
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
            players.add(player.getName());
        }
        players.addAll(Arrays.asList(new String[]{"@s", "@a", "@p", "@r", "@e"}));
        return players;
    }
}
