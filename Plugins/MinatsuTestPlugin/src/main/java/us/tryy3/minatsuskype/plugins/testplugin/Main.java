package us.tryy3.minatsuskype.plugins.testplugin;

import us.tryy3.java.minatsuskype.Bot;
import us.tryy3.java.minatsuskype.plugins.Plugin;
import us.tryy3.java.minatsuskype.plugins.PluginDescription;
import us.tryy3.minatsuskype.plugins.minatsupermissions.PermissionsApi;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class Main extends Plugin {
    PermissionsApi permissionsApi;

    @Override
    public void init(Bot bot, File pluginDir) {
        PluginDescription desc = new PluginDescription("TestPlugin", "0.0.1");
        super.init(bot, pluginDir, desc);
    }

    @Override
    public void onStart() {
        if (getBot().get)
        Map js = loadConfig(getPluginFolder() + "/config.json");
        System.out.println(getLogger());
        getLogger().info("Test Plugin has now been loaded");
        getLogger().info("meow");
        js.put("Name", "tryy3");
        saveConfig(getPluginFolder() + "/config.json", js);
    }

    @Override
    public boolean onCommand(String from, String id, String cmd, String[] args) {
        if ()
        return super.onCommand(from, id, cmd, args);
    }
}
