package us.tryy3.java.minatsuskype;

import com.google.gson.JsonArray;
import us.tryy3.java.minatsuskype.events.onChatEvent;
import us.tryy3.java.minatsuskype.manager.*;
import us.tryy3.java.minatsuskype.plugins.Plugin;
import us.tryy3.java.minatsuskype.plugins.PluginDescription;

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
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private EventManager eventManager;
    private PluginManager pluginManager;
    private CommandManager commandManager;
    private Map<String, Plugin> plugins;
    private TCPClient tcpClient;

    public Bot() throws MalformedURLException {
        this.plugins = new HashMap<>();
        this.tcpClient = new TCPClient("127.0.0.1", 1337, this);

        this.configManager = new ConfigManager();
        this.databaseManager = new DatabaseManager();
        this.eventManager = new EventManager();
        this.pluginManager = new PluginManager();
        this.commandManager = new CommandManager();

        File pluginDir = new File("plugins");

        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
            System.out.println("Creating plugins folder.");
        }

        if (pluginDir.exists() && pluginDir.isFile()) {
            throw new Error("Plugins folder is a file.");
        }

        System.out.println("Folder found.");

        File[] fileList = pluginDir.listFiles(new FileFilter() {
            public boolean accept(File path) {
                return path.getPath().toLowerCase().endsWith(".jar");
            }
        });

        System.out.println(fileList.length + " plugins found.");

        URL[] urls = new URL[fileList.length];

        for (int i = 0; i < fileList.length; i++) {
            urls[i] = fileList[i].toURI().toURL();
        }

        URLClassLoader urlClassLoader = new URLClassLoader(urls);

        ServiceLoader<Plugin> pluginLoader = ServiceLoader.load(Plugin.class, urlClassLoader);

        System.out.println("Plugins: " + urlClassLoader.toString());

        for (Plugin plugin : pluginLoader) {
            plugin.init(this, pluginDir);
            PluginDescription desc = plugin.getDescription();
            System.out.println("Plugin: " + desc);
            if (this.plugins.containsKey(desc.getName())) {
                System.out.println("The plugin " + desc.getName() + " already exists.");
            } else {
                this.plugins.put(desc.getName(), plugin);
                System.out.println(desc.toString());
                plugin.onStart();
            }
        }
    }

    public void read(JsonArray array) {
        switch (array.get(0).getAsString()) {
            case "onMessageEvent": {
                if (array.size() <= 1) {
                    System.out.println("Message event called, but no args.");
                    return;
                }
                JsonArray ja = array.get(1).getAsJsonArray();
                if (ja.size() < 3) {
                    System.out.println("Not enough arguments was sent.");
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
                    System.out.println("knownCommand: " + knownCommand);

                    System.out.println("Plugins: " + getPlugins().size());
                    for (Plugin plugin : getPlugins().values()) {
                        if (plugin.onCommand(from, id, command, args)) {
                            System.out.println("Return was true");
                            knownCommand = true;
                        }
                        System.out.println("Return was false");
                    }
                    System.out.println("knownCommand: " + knownCommand);
                    if (!knownCommand) {
                        getTcpClient().writeMessage(id, "Unknown command, please check the help page.");
                    }
                    break;
                }
                getEventManager().callEvents(new onChatEvent(msg, from, id));
                break;
            }
        }
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public Map<String, Plugin> getPlugins() {
        return plugins;
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }
}
