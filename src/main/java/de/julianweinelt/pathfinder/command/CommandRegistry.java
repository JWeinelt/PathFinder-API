package de.julianweinelt.pathfinder.command;

import de.julianweinelt.pathfinder.suggestion.CustomListProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommandRegistry {
    private final HashMap<String, List<Command>> commandMap = new HashMap<>();
    private final HashMap<String, CustomListProvider> customListProviderMap = new HashMap<>();

    public void registerCommand(String namespace, Command command) {
        commandMap.computeIfAbsent(namespace, k -> new ArrayList<>()).add(command);
    }

    public List<String> getCustomListSuggestions(String list) {
        CustomListProvider clp = customListProviderMap.getOrDefault(list, null);
        if (clp == null) return new ArrayList<>();
        else return clp.onSuggest();
    }

    public List<String> suggest(String namespace, String command, String[] args) {
        List<Command> nameSpaceCommands = commandMap.get(namespace);
        if (nameSpaceCommands == null) {
            return new ArrayList<>();
        }
        Command cmd = getCommand(namespace, command);
        if (cmd == null) {
            return new ArrayList<>();
        }
        if (args.length == 0) {
            List<String> suggestions = new ArrayList<>();
            for (Argument a : cmd.getArgs()) {
                suggestions.add(a.getName());
            }
            return suggestions;
        } else {
            return suggestForArgs(cmd.getArgs(), args, 0);
        }
    }
    private List<String> suggestForArgs(List<Argument> argList, String[] input, int index) {
        if (index >= input.length) {
            List<String> suggestions = new ArrayList<>();
            for (Argument a : argList) {
                suggestions.add(a.getName());
            }
            return suggestions;
        }

        String currentInput = input[index];

        for (Argument a : argList) {
            if (a.getName().equalsIgnoreCase(currentInput)) {
                if (index == input.length - 1) {
                    List<String> suggestions = new ArrayList<>();
                    if (a.getSuggestions() != null) suggestions.addAll(a.getSuggestions());
                    suggestions.addAll(getSuggestionsByType(a.getType()));
                    return suggestions;
                } else {
                    return suggestForArgs(a.getArgs(), input, index + 1);
                }
            }
        }

        List<String> suggestions = new ArrayList<>();
        for (Argument a : argList) {
            if (a.getName().toLowerCase().startsWith(currentInput.toLowerCase())) {
                suggestions.add(a.getName());
            }
        }
        return suggestions;
    }

    private List<String> getSuggestionsByType(String type) {
        if (type == null || type.isEmpty()) return Collections.emptyList();
        switch (type.toLowerCase()) {
            case "player":
                return getCustomListSuggestions("players");
            case "entity":
                return getCustomListSuggestions("entities");
            case "coordinate":
                return Collections.singletonList("~");
            case "list":
                throw new IllegalArgumentException("list commands have to specify a custom list name.");
            default:
                return Collections.emptyList();
        }
    }

    private List<String> getSuggestionsByType(String type, String listName) {
        if (type == null || type.isEmpty()) return Collections.emptyList();
        switch (type.toLowerCase()) {
            case "player":
                return getCustomListSuggestions("players");
            case "entity":
                return getCustomListSuggestions("entities");
            case "coordinate":
                return Collections.singletonList("~");
            case "list":
                return getCustomListSuggestions(listName);
            default:
                return Collections.emptyList();
        }
    }



    @Nullable
    public Command getCommand(String namespace, String command) {
        List<Command> nameSpaceCommands = commandMap.get(namespace);
        if (nameSpaceCommands == null) {
            return null;
        }

        for (Command cmd : nameSpaceCommands) {
            if (cmd.getName().equalsIgnoreCase(command)) {
                return cmd;
            }
        }
        return null;
    }
}