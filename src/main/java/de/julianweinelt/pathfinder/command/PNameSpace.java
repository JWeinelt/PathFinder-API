package de.julianweinelt.pathfinder.command;

import java.util.ArrayList;
import java.util.List;

public class PNameSpace {
    private String nameSpace;
    private List<Command> commands = new ArrayList<>();

    public PNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
