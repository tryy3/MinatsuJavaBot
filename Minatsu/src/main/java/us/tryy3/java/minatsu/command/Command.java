package us.tryy3.java.minatsu.command;

import java.util.List;

/**
 * Created by dennis.planting on 11/29/2015.
 */
public class Command implements ICommand {
    private String name = null;
    private String usage = null;
    private List<String> aliases = null;
    private List<Command> children = null;

    public Command() {}
    public Command(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUsage() {
        return this.usage;
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public List<Command> getChildren() {
        return this.children;
    }

    @Override
    public Boolean onCommand(String user, String channel, Command command, String label, String[] args) {
        return false;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public void setChildren(List<Command> children) {
        this.children = children;
    }

    @Override
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
