package us.tryy3.java.minatsu;

import com.google.gson.JsonArray;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.java.minatsu.command.CommandManager;
import us.tryy3.java.minatsu.events.Event;
import us.tryy3.java.minatsu.events.onChatEvent;
import us.tryy3.java.minatsu.logger.Logger;
import us.tryy3.java.minatsu.plugins.Plugin;
import us.tryy3.java.minatsu.plugins.PluginDescription;
import us.tryy3.java.minatsu.plugins.PluginManager;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class Bot {
    private Event event;
    private CommandManager commandManager;
    private Map<String, Plugin> plugins;
    private TCPServer tcpServer;
    private Options options;
    private PluginManager pluginManager;
    private String version;
    private final Logger logger = new Logger(null);
    private Long startupTime = new GregorianCalendar().getTimeInMillis();

    public Bot(String version, Options options) throws MalformedURLException {
        logger.info("Initalizing Minatsu Version " + version);
        this.options = options;
        this.version = version;
        this.plugins = new HashMap<>();
        this.tcpServer = new TCPServer(options, this);
        this.event = new Event();
        this.commandManager = new CommandManager();

        File pluginDir = new File(options.getPluginPath());

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
            logger.fine("Creating plugins folder.");
        }

        if (pluginDir.exists() && pluginDir.isFile()) {
            throw new Error("Plugins folder is a file.");
        }

        logger.fine("Folder found.");

        //TODO: Add some sort of dependency system and order system.

        loadPlugins(pluginDir);

        this.pluginManager = new PluginManager(plugins);
    }

    private void loadPlugins(File file) {
        File[] fileList = file.listFiles(new FileFilter() {
            public boolean accept(File path) {
                return path.getPath().toLowerCase().endsWith(".jar");
            }
        });

        logger.info(fileList.length + " plugin(s) found.");

        URL[] urls = new URL[fileList.length];

        for (int i = 0; i < fileList.length; i++) {
            try {
                urls[i] = fileList[i].toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        URLClassLoader urlClassLoader = new URLClassLoader(urls);

        ServiceLoader<Plugin> pluginLoader = ServiceLoader.load(Plugin.class, urlClassLoader);

        this.plugins = new HashMap<>();

       for (Plugin plugin : pluginLoader) {
            plugin.init(this, file);
            PluginDescription desc = plugin.getDescription();

            if (this.plugins.containsKey(desc.getName())) {
                logger.severe("The plugin " + desc.getName() + " already exists.");
            } else {
                this.plugins.put(desc.getName(), plugin);
                logger.info("Enabling plugin %s version %s", desc.getName(), desc.getVersion());
                plugin.onStart();
            }
        }
    }

    private void unloadPlugins() {
        for (Plugin plugin : plugins.values()) {
            plugin.onStop();
        }
    }

    public void reloadPlugins() {
        File pluginDir = new File(options.getPluginPath());

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
            logger.fine("Creating plugins folder.");
        }

        if (pluginDir.exists() && pluginDir.isFile()) {
            throw new Error("Plugins folder is a file.");
        }

        logger.fine("Folder found.");

        unloadPlugins();
        loadPlugins(pluginDir);
    }

    public void stop() {
        //broadcastAll("Bot is shutting down now.");
        unloadPlugins();

        getTcpServer().stop();
    }

    public void read(TCPServer.Connection connection, JsonArray array) {
        switch (array.get(0).getAsString()) {
            case "onMessageEvent": {
                if (array.size() <= 1) {
                    logger.severe("TCPServer sent message event but no args was sent.");
                    return;
                }
                JsonArray ja = array.get(1).getAsJsonArray();
                if (ja.size() < 3) {
                    logger.severe("TCPServer sent message event but not enough args was sent.");
                    return;
                }
                String msg = ja.get(2).getAsString();
                String from = ja.get(1).getAsString();
                String id = ja.get(0).getAsString();

                if (ja.get(2).getAsString().indexOf("!") == 0) {
                    String[] splitmsg = msg.split(" ");

                    String command = splitmsg[0].substring(1);
                    String[] args = splitmsg.length > 1 ? Arrays.copyOfRange(splitmsg, 1, splitmsg.length) : null;

                    Boolean knownCommand = false;

                    for (Plugin plugin : getPlugins().values()) {
                        if (plugin.onCommand(connection, from, id, command, args)) {
                            knownCommand = true;
                        }
                    }

                    for (Command cmd : commandManager.getCommands()) {
                        if (cmd.getName().equals(command)) {
                            knownCommand = cmd.onCommand(connection, from, id, cmd, command, args);
                            break;
                        } else {
                            for (String s : cmd.getAliases()) {
                                if (s.equals(command)) {
                                    knownCommand = cmd.onCommand(connection, from, id, cmd, command, args);
                                    break;
                                }
                            }
                        }
                    }

                    if (!knownCommand) {
                        connection.sendMessage(id, "Unknown command, please check the help page.");
                    }
                    break;
                }
                getEvent().callEvents(new onChatEvent(msg, from, id));
                break;
            }
        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Event getEvent() {
        return event;
    }

    public Map<String, Plugin> getPlugins() {
        return plugins;
    }

    public TCPServer getTcpServer() {
        return tcpServer;
    }

    public Long getStartupTime() {
        return startupTime;
    }
}
