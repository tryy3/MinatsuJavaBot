package us.tryy3.java.minatsuskype;

import com.google.gson.JsonArray;
import us.tryy3.java.minatsuskype.events.onChatEvent;
import us.tryy3.java.minatsuskype.manager.*;
import us.tryy3.java.minatsuskype.plugins.Plugin;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class Bot {
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private EventManager eventManager;
    private PluginManager pluginManager;
    private CommandManager commandManager;
    private ServiceLoader<Plugin> plugins;
    private TCPClient tcpClient;

    public Bot() throws MalformedURLException {
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

        System.out.println(urls);

        URLClassLoader urlClassLoader = new URLClassLoader(urls);

        System.out.println(urlClassLoader);

        ServiceLoader<Plugin> pluginLoader = ServiceLoader.load(Plugin.class, urlClassLoader);

        this.plugins = pluginLoader;

        System.out.println(pluginLoader);
        Iterator<Plugin> apit = pluginLoader.iterator();
        System.out.println(apit.hasNext());
        while (apit.hasNext()) {
            Plugin plugin = apit.next();
            plugin.init(this, pluginDir);
            System.out.println(plugin.getDescription());
        }
    }

    public void read(JsonArray array) {
        System.out.println(array.toString());
        System.out.println(array.get(0).getAsString());
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
                System.out.println(msg);
                String from = ja.get(1).getAsString();
                System.out.println(from);
                String id = ja.get(0).getAsString();
                System.out.println(id);

                if (ja.get(2).getAsString().indexOf("!") == 0) {
                    String[] splitmsg = msg.split(" ");
                    System.out.println(Arrays.toString(splitmsg));
                    String command = splitmsg[0].substring(1);
                    System.out.println(command);
                    String[] args = splitmsg.length > 1 ? Arrays.copyOfRange(splitmsg, 1, splitmsg.length) : null;
                    System.out.println(Arrays.toString(args));

                    Iterator<Plugin> plugins = getPlugins().iterator();

                    Boolean knownCommand = false;
                    while (plugins.hasNext()) {
                        Plugin plugin = plugins.next();
                        if (plugin.onCommand(from, id, command, args)) {
                            knownCommand = true;
                        }
                    }
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

    public ServiceLoader<Plugin> getPlugins() {
        return plugins;
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }
}
