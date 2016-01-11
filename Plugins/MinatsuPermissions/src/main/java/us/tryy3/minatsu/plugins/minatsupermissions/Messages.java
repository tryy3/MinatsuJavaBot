package us.tryy3.minatsu.plugins.minatsupermissions;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennis.planting on 11/15/2015.
 */
public class Messages {
    private Map<String, String> msgs;

    public Messages() {
        msgs = new HashMap<>();

        msgs.put("registeredGroups", "Registered groups:");
        msgs.put("alreadyGroup", "The group %1 already exists.");
        msgs.put("createdGroup", "The group %1 has now been created.");
        msgs.put("notGroup", "The group %1 does not exist.");
        msgs.put("deleteGroup", "The group %1 has now been deleted.");
        msgs.put("groupAlreadyPerms", "The group %1 already has the permission node %2.");
        msgs.put("groupPermAdded", "The group %1 now has access to %2.");
        msgs.put("groupDontPerm", "The group %1 doesn't have the permission node %2.");
        msgs.put("groupHasPerm", "The group %1 has access to %2.");
        msgs.put("groupNoHasPerm", "The group %1 does not have access to %2.");
        msgs.put("groupDelperm", "The group %1 no longer has access to %2.");
        msgs.put("playerAlreadyPerms", "The player %1 already has the permission node %2.");
        msgs.put("playerPermAdded", "The player %1 now has access to %2.");
        msgs.put("playerDontPerm", "The player %1 doesn't have the permission node %2.");
        msgs.put("playerHasPerm", "The player %1 has access to %2.");
        msgs.put("playerNoHasPerm", "The player %1 does not have access to %2.");
        msgs.put("playerDelperm", "The player %1 no longer has access to %2.");
        msgs.put("invalidGroup", "The group %1 does not exist.");
        msgs.put("playerAlreadyGroup", "The player %1 is already a member of %2.");
        msgs.put("playerNowGroup", "The player %1 is now a member of %2.");
        msgs.put("playerNoGroup", "The player %1 is not a member of %2.");
        msgs.put("playerDelGroup", "The player %1 is no longer a member of %2.");
        msgs.put("playerHasGroup", "The player %1 is a member of %2.");
        msgs.put("playerNoHasGroup", "The player %1 is not a member of %2");
        msgs.put("invalidCommand", "The command you tried to execute is invalid, check the help command.");
        msgs.put("noPerm", "You do not have access to this command.");
        msgs.put("reloadPlugin", "The plugin has now been reloaded.");
    }

    public String getMessage(String message, String... args) {
        if (!msgs.containsKey(message)) return null;

        Formatter formatter = new Formatter();
        return formatter.format(msgs.get(message), args).toString();
    }
}
