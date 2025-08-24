package de.julianweinelt.pathfinder;

import de.julianweinelt.pathfinder.command.CommandParser;
import de.julianweinelt.pathfinder.command.CommandRegistry;
import de.julianweinelt.pathfinder.saving.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = PathFinderAPI.MODID, name = PathFinderAPI.NAME, version = PathFinderAPI.VERSION, clientSideOnly = true)
@Mod.EventBusSubscriber(modid = PathFinderAPI.MODID)
public class PathFinderAPI
{
    public static final String MODID = "pathfinder";
    public static final String NAME = "PathFinder API";
    public static final String VERSION = "1.0.2";

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
    }

    public static CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }
}
