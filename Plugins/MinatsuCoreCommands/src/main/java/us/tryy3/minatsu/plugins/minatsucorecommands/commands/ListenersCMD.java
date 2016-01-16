package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

import java.util.Collections;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class ListenersCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public ListenersCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("listener");
        this.setAliases(Collections.singletonList("listeners"));
        this.setDescription("Show a list of listeners.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.listeners")) {
            //TODO: Implement this
        }

        return super.onCommand(connection, user, channel, command, label, args);
    }
}
