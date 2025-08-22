package de.julianweinelt.pathfinder.command;

import java.util.ArrayList;
import java.util.List;

public class Argument {
    private final String name;
    private final String type;
    private final String description;
    private String listName;
    private final List<String> suggestions = new ArrayList<>();
    private final List<Argument> args = new ArrayList<>();

    public Argument(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public void addSubArg(Argument arg) {
        args.add(arg);
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }

    public String getListName() {
        return listName;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public List<Argument> getArgs() {
        return args;
    }
}