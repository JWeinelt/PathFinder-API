package de.julianweinelt.pathfinder.command;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.julianweinelt.pathfinder.saving.ConfigManager;
import de.julianweinelt.pathfinder.util.FileDownloader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static de.julianweinelt.pathfinder.PathFinderAPI.logger;

public class CommandParser {
    private static final Gson gson = new Gson();
    private static int loaded = 0;
    private static int errors = 0;

    public static void loadAllNamespaces() {
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        Set<String> namespaces = rm.getResourceDomains();
        for (String namespace : namespaces) {
            CommandRegistry.getInstance().registerNameSpace(loadCommand(namespace));
        }
        logger.info("Loaded commands for Pathfinder API. Loaded: {}, Not Found: {}", loaded, errors);
    }

    public static PNameSpace loadCommand(String nameSpace) {
        File file = new File(ConfigManager.getConfigFolder(), nameSpace + ".json");
        if (!file.exists()) {
            errors++;
            logger.debug("JSON file not found: {}", file.getAbsolutePath());
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<PNameSpace>() {}.getType();
            loaded++;
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException e) {
            errors++;
            logger.debug("Could not load commands for namespace {}", nameSpace);
            return null;
        }
    }

    public static Set<String> getNameSpacesFiltered() {
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        Set<String> namespaces = rm.getResourceDomains();
        Arrays.asList("realms", "forge", "fml", ".mcassetsroot").forEach(namespaces::remove);
        return namespaces;
    }

    public static boolean checkInstalled(String namespace) {
        File file = new File(ConfigManager.getConfigFolder(), namespace + ".json");
        return file.exists();
    }
}