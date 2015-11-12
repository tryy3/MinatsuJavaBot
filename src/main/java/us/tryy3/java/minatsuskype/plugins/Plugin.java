package us.tryy3.java.minatsuskype.plugins;

import us.tryy3.java.minatsuskype.Bot;

import java.io.File;

/**
 * Created by dennis.planting on 11/9/2015.
 */
public abstract class Plugin {
    private boolean isEnabled = false;
    private PluginDescription description;
    private File file;
    private File pluginFolder;
    private Bot bot;

    public File getFile() {
        return file;
    }

    public File getPluginFolder() {
        return pluginFolder;
    }

    public PluginDescription getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Bot getBot() {
        return bot;
    }

    public void setDescription(PluginDescription description) {
        this.description = description;
    }

    public void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            if (this.isEnabled) {
                this.onStart();
            } else {
                this.onStop();
            }
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPluginFolder(File pluginFolder) {
        this.pluginFolder = pluginFolder;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void onStart() {
    }
    public void onStop() {
    }

    public void init(Bot bot, File pluginDir) {
        init(bot, pluginDir, null);
    }
    public void init(Bot bot, File datafolder, PluginDescription description) {
        if (description == null) {
            throw new Error("This plugin was not properly initalized.");
        }
        if (description.getName() == null) {
            throw new Error("Name was not set in PluginDescription.");
        }
        if (description.getVersion() == null) {
            throw new Error("Version was not set in PluginDescription.");
        }
        this.bot = bot;
        this.pluginFolder = new File(datafolder + "/" + description.getName());
        this.description = description;
    }

    public boolean onCommand(String from, String id, String cmd, String[] args) {
        return false;
    }
}
