package us.tryy3.minatsu.plugins.minatsucorecommands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.command.CommandManager;
import us.tryy3.java.minatsu.plugins.Plugin;
import us.tryy3.java.minatsu.plugins.PluginDescription;
import us.tryy3.minatsu.plugins.minatsucorecommands.commands.*;
import us.tryy3.minatsu.plugins.minatsupermissions.MinatsuPermissions;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

import java.io.File;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class MinatsuCoreCommands extends Plugin {

    @Override
    public void init(Bot bot, File datafolder) {
        PluginDescription desc = new PluginDescription.DescriptionBuilder("CoreCommands", "0.0.1")
                .description("Contains the Core commands for Minatsu")
                .authors("tryy3")
                .dependency("MinatsuPermissions")
                .build();

        super.init(bot,datafolder,desc);
    }

    @Override
    public void onStart() {
        // Create Command instances.
        CommandManager manager = getBot().getCommandManager();

        Bot bot = getBot();
        if (bot.getPluginManager().getPlugin("MinatsuPermissions") == null || !(bot.getPluginManager().getPlugin("MinatsuPermissions") instanceof MinatsuPermissions)) {
            this.unload();
            throw new Error("Can't find MinatsuPermissions");
        }
        PermissionsApi api = ((MinatsuPermissions) bot.getPluginManager().getPlugin("MinatsuPermissions")).getPermissionsApi();

        manager.registerCommand(new StopCMD("stop", bot, api));
        manager.registerCommand(new ReloadCMD("reload", bot, api));
        manager.registerCommand(new InfoCMD("info", bot, api));
        manager.registerCommand(new ListenersCMD("listener", bot, api));
        manager.registerCommand(new HelpCMD("help", bot, api));

        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
