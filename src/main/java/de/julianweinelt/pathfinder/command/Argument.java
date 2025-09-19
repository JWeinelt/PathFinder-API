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
    public boolean matches(String input) {
        if (name != null && name.equalsIgnoreCase(input)) {
            return true;
        }

        if (suggestions == null) return false;
        for (String suggestion : suggestions) {
            if (suggestion.equalsIgnoreCase(input)) {
                return true;
            }
        }

        switch (type.toLowerCase()) {
            case "int":
                try {
                    Integer.parseInt(input);
                    return true;
                } catch (NumberFormatException ignored) {}
                break;
            case "double":
                try {
                    Double.parseDouble(input);
                    return true;
                } catch (NumberFormatException ignored) {}
                break;
            case "boolean":
                if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                    return true;
                }
                break;
            case "string":
                return true;
        }

        return false;
    }

}