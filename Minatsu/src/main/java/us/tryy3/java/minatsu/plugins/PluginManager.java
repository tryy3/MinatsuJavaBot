package us.tryy3.java.minatsu.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennis.planting on 12/5/2015.
 */
public class PluginManager {
    Map<String, Plugin> plugins = new HashMap<>();

    public PluginManager(Map<String, Plugin> plugins) {
        this.plugins = plugins;
    }

    public Map<String, Plugin> getPlugins() {
        return plugins;
    }

    public boolean pluginIsLoaded(String name) {
        return plugins.containsKey(name);
    }

    public Plugin getPlugin(String name) {
        return this.plugins.get(name);
    }

    public boolean disablePlugin(String name) {
        if (!plugins.containsKey(name)) return false;
        plugins.remove(name);
        return true;
    }
}
