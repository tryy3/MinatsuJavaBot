package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class StopCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public StopCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("stop");
        this.setDescription("Stops the bot.");
    }

    @Override
    public Boolean onCommand(final TCPServer.Connection connection, String user, final String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.stop")) {
            new Thread(new Runnable() {
                public void run() {
                    for (int i = 3; i >= 0; i--) {
                        if (i > 1) {
                            connection.sendMessage(channel, "Restarting the bot in " + i + " seconds.");
                        } else if (i == 1) {
                            connection.sendMessage(channel, "Restarting the bot in 1 second.");
                        } else {
                            connection.sendMessage(channel, "Restarting the bot now.");
                            bot.stop();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }).start();
        }

        return true;
    }
}
