package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class HelpCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public HelpCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("help [cmd] [page]");
        this.setDescription("Show a help page on all commands or specific commands.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.help")) {
            //TODO: Implement this
        }

        return super.onCommand(connection, user, channel, command, label, args);
    }
}
