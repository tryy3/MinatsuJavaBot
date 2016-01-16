package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class ReloadCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public ReloadCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("reload");
        this.setDescription("Reloads all plugins.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.reload")) {
            bot.reloadPlugins();
            connection.sendMessage(channel, "All plugins has now been reloaded.");
        }

        return super.onCommand(connection, user, channel, command, label, args);
    }
}
