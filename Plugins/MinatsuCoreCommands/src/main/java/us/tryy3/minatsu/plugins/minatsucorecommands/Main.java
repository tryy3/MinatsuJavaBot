package us.tryy3.minatsu.plugins.minatsucorecommands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.command.CommandManager;
import us.tryy3.java.minatsu.plugins.Plugin;
import us.tryy3.java.minatsu.plugins.PluginDescription;
import us.tryy3.minatsu.plugins.minatsucorecommands.commands.*;

import java.io.File;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class Main extends Plugin {

    @Override
    public void init(Bot bot, File datafolder) {
        PluginDescription desc = new PluginDescription.DescriptionBuilder("CoreCommands", "0.0.1")
                .description("Contains the Core commands for Minatsu")
                .authors("tryy3")
                .build();

        super.init(bot,datafolder,desc);
    }

    @Override
    public void onStart() {
        // Create Command instances.
        CommandManager manager = getBot().getCommandManager();

        manager.registerCommand(new StopCMD("stop"));
        manager.registerCommand(new ReloadCMD("reload"));
        manager.registerCommand(new InfoCMD("info"));
        manager.registerCommand(new ListenersCMD("listener"));
        manager.registerCommand(new HelpCMD("help"));

        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
