package us.tryy3.java.minatsuskype.command;

import java.util.List;

/**
 * Created by dennis.planting on 11/29/2015.
 */
public interface ICommand {
    String getName();
    String getUsage();
    List<String> getAliases();
    List<Command> getChildren();
    Boolean onCommand(String user, String channel, Command command, String label,  String[] args);

    void setName(String name);
    void setUsage(String usage);
    void setChildren(List<Command> children);
    void setAliases(List<String> aliases);
}
