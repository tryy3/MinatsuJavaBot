package us.tryy3.minatsu.plugins.testplugin;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer.Connection;
import us.tryy3.java.minatsu.plugins.Plugin;
import us.tryy3.java.minatsu.plugins.PluginDescription;
import us.tryy3.java.minatsu.plugins.PluginDescription.DescriptionBuilder;
import us.tryy3.minatsuskype.plugins.minatsupermissions.PermissionsApi;

import java.io.File;
import java.util.Map;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class Main extends Plugin {
    PermissionsApi permissionsApi;

    @Override
    public void init(Bot bot, File pluginDir) {
        PluginDescription desc = new DescriptionBuilder("TestPlugin", "0.0.1").build();
        super.init(bot, pluginDir, desc);
    }

    @Override
    public void onStart() {
        Map js = loadConfig(getPluginFolder() + "/config.json");
        System.out.println(getLogger());
        getLogger().info("Test Plugin has now been loaded");
        getLogger().info("meow");
        js.put("Name", "tryy3");
        saveConfig(getPluginFolder() + "/config.json", js);
    }

    @Override
    public boolean onCommand(Connection connection, String from, String id, String cmd, String[] args) {
        return super.onCommand(connection, from, id, cmd, args);
    }
}
