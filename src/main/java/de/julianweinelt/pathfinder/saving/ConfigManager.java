package de.julianweinelt.pathfinder.saving;

import java.io.File;

public class ConfigManager {
    private static File configFolder;

    public static void init(File configDir) {
        configFolder = new File(configDir, "pathfinder");
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }
    }

    public static File getConfigFolder() {
        return configFolder;
    }
}
