package de.julianweinelt.pathfinder.util;

import de.julianweinelt.pathfinder.PathFinderAPI;
import de.julianweinelt.pathfinder.saving.ConfigManager;

import java.io.*;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class FileDownloader {
    private static String baseStringURL = "https://resources.api.codeblocksmc.de/pathfinder/%s.json";

    public static CompletableFuture<Boolean> downloadPathfinderJSON(String namespace) {
        File file = new File(ConfigManager.getConfigFolder(), namespace + ".json");
        return CompletableFuture.supplyAsync(() -> {
            try (InputStream in = new URL(String.format(baseStringURL, namespace)).openStream();
                 FileOutputStream out = new FileOutputStream(file)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                PathFinderAPI.logger.info("Finished download for {}", namespace);
                return true;

            } catch (IOException e) {
                PathFinderAPI.logger.error("Failed to download {}", namespace, e);
                return false;
            }
        });
    }


    private static CompletableFuture<Boolean> downloadFile(String namespace, File outputFile) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        new Thread(() -> {
            try (InputStream in = new URL(String.format(baseStringURL, namespace)).openStream();
                 FileOutputStream out = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                PathFinderAPI.logger.info("Finished download for {}", namespace);
                future.complete(true);

            } catch (IOException e) {
                PathFinderAPI.logger.error(e.getMessage());
                future.complete(false);
            }
        });
        return future;
    }
}
