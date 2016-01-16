package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import com.google.gson.JsonArray;
import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.minatsu.plugins.minatsucorecommands.Utils;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class InfoCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public InfoCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("info");
        this.setDescription("Show info about the bot, uptime, memory usage etc.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.info")) {
            String uptime = Utils.uptime(bot.getStartupTime());
            long maxM = Runtime.getRuntime().maxMemory() / 1024 / 1024;
            long totM = Runtime.getRuntime().totalMemory() / 1024 / 1024;
            long freeM = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            String cpu = ""; // TODO: Implement CPU usage in the future.
            int plugins = bot.getPlugins().size();
            int listeners = bot.getTcpServer().getConnections().size();

            JsonArray msgArray = new JsonArray();

            connection.sendMessage(formatMessage(channel,
                    String.format("Uptime: %s", uptime),
                    String.format("Maximum memory: %s MB", maxM),
                    String.format("Allocated memory: %s MB", totM),
                    String.format("Free memory: %s MB", freeM),
                    String.format("Plugin count: %s", plugins),
                    String.format("Listener count: %s", listeners)));
        }

        return true;
    }

    public JsonArray formatMessage(String channel, String... msg) {
        JsonArray array = new JsonArray();

        for (String s : msg) {
            JsonArray a = new JsonArray();
            a.add(channel);
            a.add(s);
            array.add(a);
        }
        return array;
    }
}
