package de.julianweinelt.pathfinder.command;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private final String name;
    private final String description;
    private final List<Argument> args = new ArrayList<>();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addArgument(Argument arg) {
        this.args.add(arg);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Argument> getArgs() { return args; }
}
