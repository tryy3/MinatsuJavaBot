package us.tryy3.minatsuskype.plugins.testplugin;

import us.tryy3.java.minatsuskype.Bot;
import us.tryy3.java.minatsuskype.plugins.Plugin;
import us.tryy3.java.minatsuskype.plugins.PluginDescription;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class Main extends Plugin {
    @Override
    public void init(Bot bot, File pluginDir) {
        PluginDescription desc = new PluginDescription("TestPlugin", "0.0.1");
        System.out.println(desc);
        super.init(bot, pluginDir, desc);
    }

    @Override
    public void onStart() {
        System.out.println("PLUGIN LOADED WOOOT");
        getBot().getEventManager().registerEvents(new commandListener());
        Map js = loadConfig(getPluginFolder() + "/config.json");
        System.out.println(js);
        js.put("Name", "tryy3");
        saveConfig(getPluginFolder() + "/config.json", js);
    }

    @Override
    public boolean onCommand(String from, String id, String cmd, String[] args) {
        System.out.println("Command found...");
        System.out.println("From: " + from);
        System.out.println("ID: " + id);
        System.out.println("CMD: " + cmd);
        System.out.println("Args: " + Arrays.toString(args));
        return super.onCommand(from, id, cmd, args);
    }
}
