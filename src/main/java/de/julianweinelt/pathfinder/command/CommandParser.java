package de.julianweinelt.pathfinder.command;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.julianweinelt.pathfinder.PathFinderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommandParser {
    public static void loadAllNamespaces() {
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();

        Set<String> namespaces = rm.getResourceDomains(); // liefert alle Mods/Namespaces
        for (String namespace : namespaces) {
            ResourceLocation path = new ResourceLocation(namespace, "pathfinder.json");

            try {
                // Check if the resource exists using the resource manager instead of file system
                IResource resource = rm.getResource(path);
                InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                JsonObject data = parse(reader);
                if (data != null) {
                    Type commandListType = new TypeToken<List<Command>>(){}.getType();
                    List<Command> commands = new Gson().fromJson(data.get("commands"), commandListType);
                    String nameSpace = data.get("namespace").getAsString();
                    if (!Objects.equals(nameSpace, path.getResourceDomain())) PathFinderAPI.logger.warn(
                            "Namespace mismatch: Expected {}, found {}", path.getResourceDomain(), nameSpace);

                }
            } catch (Exception e) {
                // Only log if it's not a simple "resource not found" case
                PathFinderAPI.logger.debug("Namespace {} does not have a pathfinder.json file.", namespace);
            }
        }
    }

    public static JsonObject parse(InputStreamReader reader) {
        try {
            return new JsonParser().parse(reader).getAsJsonObject();
        } catch (Exception e) {
            PathFinderAPI.logger.warn("Failed to parse commands from: {}", e.getMessage());
            return null;
        }
    }
}