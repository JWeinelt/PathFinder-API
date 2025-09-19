package de.julianweinelt.pathfinder;

import de.julianweinelt.pathfinder.command.CommandParser;
import de.julianweinelt.pathfinder.command.CommandRegistry;
import de.julianweinelt.pathfinder.saving.ConfigManager;
import de.julianweinelt.pathfinder.util.CustomListProvider;
import de.julianweinelt.pathfinder.util.CustomListResult;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.GameRules;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@Mod(modid = PathFinderAPI.MODID, name = PathFinderAPI.NAME, version = PathFinderAPI.VERSION, clientSideOnly = true)
@Mod.EventBusSubscriber(modid = PathFinderAPI.MODID)
public class PathFinderAPI {
    public static final String MODID = "pathfinder";
    public static final String NAME = "PathFinder API";
    public static final String VERSION = "1.1.1";

    public static final HashMap<String, CustomListResult> customLists = new HashMap<>();

    public static Logger logger;

    @Mod.Instance
    public static PathFinderAPI instance;

    public static CommandRegistry commandRegistry;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ConfigManager.init(event.getModConfigurationDirectory());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        commandRegistry = new CommandRegistry();
        logger.info("Welcome to the PathFinder API!");
        CommandParser.loadAllNamespaces();

        CustomListProvider.init();
    }

    public static CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public static List<String> getSuggestionTypeEntries(String customList, EntityPlayer player) {
        List<String> list = new ArrayList<>();
        switch (customList) {
            case "entity":
                for (EntityEntry entity : ForgeRegistries.ENTITIES.getValuesCollection()) {
                    list.add(entity.getName());
                }
                break;
            case "gamerule":
                GameRules gameRules = player.world.getGameRules();
                list.addAll(Arrays.asList(gameRules.getRules()));
                break;
            case "coordinate-x":
                list.add("~");
                if (player == null) return list;
                list.add("" + player.getPosition().getX());
                break;
            case "coordinate-y":
                list.add("~");
                if (player == null) return list;
                list.add("" + player.getPosition().getY());
                break;
            case "coordinate-z":
                list.add("~");
                if (player == null) return list;
                list.add("" + player.getPosition().getZ());
                break;
            case "player":
                for (EntityPlayerMP mp : Minecraft.getMinecraft().player.getServer().getPlayerList().getPlayers()) {
                    list.add(mp.getName());
                }
                break;
        }
        return list;
    }

    @Nonnull
    public static List<String> getCustomListTypeEntries(String name) {
        CustomListResult result = customLists.getOrDefault(name, Collections::emptyList);
        return result.completions();
    }

    @Nullable
    public static CustomListResult getCustomListResult(String name) {
        return customLists.getOrDefault(name, null);
    }

    public static void registerCustomList(String name, CustomListResult result) {
        customLists.put(name, result);
    }
}