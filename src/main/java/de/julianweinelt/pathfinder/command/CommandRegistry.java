package de.julianweinelt.pathfinder.command;

import de.julianweinelt.pathfinder.suggestion.CustomListProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandRegistry {
    private final HashMap<String, List<Command>> commandMap = new HashMap<>();
    private final HashMap<String, CustomListProvider> customListProviderMap = new HashMap<>();

    public void registerCommand(String namespace, Command command) {
        commandMap.computeIfAbsent(namespace, k -> new ArrayList<>()).add(command);
    }
}