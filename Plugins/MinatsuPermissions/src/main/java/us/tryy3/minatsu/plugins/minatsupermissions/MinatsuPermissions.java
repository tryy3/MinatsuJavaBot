package us.tryy3.minatsu.plugins.minatsupermissions;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.plugins.Plugin;
import us.tryy3.java.minatsu.plugins.PluginDescription;
import us.tryy3.java.minatsu.TCPServer.Connection;
import us.tryy3.minatsu.plugins.minatsupermissions.exceptions.MultipleDefaultGroupsException;
import us.tryy3.minatsu.plugins.minatsupermissions.exceptions.NoDefaultGroupException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinatsuPermissions extends Plugin {
    private Map groups;
    private Map players;
    private Map config;
    private PermissionHandler permissionHandler;
    private PermissionsApi permissionsApi;
    private Messages messages;

    @Override
    public void init(Bot bot, File pluginDir) {
        PluginDescription desc = new PluginDescription.DescriptionBuilder("MinatsuPermissions", "0.0.1")
                .description("Permission System for Minatsu")
                .authors("tryy3")
                .build();

        super.init(bot, pluginDir, desc);
    }

    /*
        TODO: Load some default groups/players
     */
    @Override
    public void onStart() {
        this.groups = loadDefaultGroups();
        this.players = loadDefaultPlayers();
        this.config = loadDefaultConfig();
        try {
            this.permissionHandler = new PermissionHandler(this);
        } catch (MultipleDefaultGroupsException e) {
            e.printStackTrace();
        } catch (NoDefaultGroupException e) {
            e.printStackTrace();
        }
        this.permissionsApi = new PermissionsApi(this);
        this.messages = new Messages();

        //Register new sub commands;
    }

    @Override
    public void onStop() {
        saveDefaultConfig("groups", this.groups);
        saveDefaultConfig("players", this.players);
        saveDefaultConfig(this.config);
    }

    public Map loadDefaultGroups() {
        File file = new File(getPluginFolder() + "/groups.json");
        Map<String, List<Map<String, Object>>> config;
        if (!file.exists()) {
            config = defaultGroups();
            saveConfig(file, config);
        }
        config = loadConfig(file);
        if (config == null || !config.containsKey("Groups")) {
            config = defaultGroups();
            saveConfig(file, config);
        }
        return config;
    }
    public Map defaultGroups() {
        Map<String, List<Map<String, Object>>> config = new HashMap<>();
        //Default group
        Map<String, Object> group1 = new HashMap<>();
        List<String> permissions1 = new ArrayList<>();
        permissions1.add("essentials.example");
        permissions1.add("essentials.example.*");

        group1.put("Default", true);
        group1.put("Name", "Default");
        group1.put("Permissions", permissions1);

        //Owner
        Map<String, Object> group2 = new HashMap<>();
        List<String> permissions2 = new ArrayList<>();
        permissions2.add("*");

        group2.put("Default", false);
        group2.put("Name", "Owner");
        group2.put("Permissions", permissions2);

        List<Map<String, Object>> groups = new ArrayList<>();

        groups.add(group1);
        groups.add(group2);

        config.put("Groups", groups);
        return config;
    }

    public Map loadDefaultPlayers() {
        File file = new File(getPluginFolder() + "/players.json");
        Map<String, List<Map<String, Object>>> config;
        if (!file.exists()) {
            config = defaultPlayers();
            saveConfig(file, config);
        }
        config = loadConfig(file);
        if (config == null || !config.containsKey("Players")) {
            config = defaultPlayers();
            saveConfig(file, config);
        }
        return config;
    }
    public Map defaultPlayers() {
        Map<String, List<Map<String, Object>>> config = new HashMap();
        //tryyy3
        Map<String, Object> player1 = new HashMap<>();
        List<String> permissions1 = new ArrayList<>();
        permissions1.add("essentials.example");
        permissions1.add("essentials.example.*");

        List<String> groupList = new ArrayList<>();
        groupList.add("Default");
        groupList.add("Owner");

        player1.put("Name", "8:tryyy3");
        player1.put("Permissions", permissions1);
        player1.put("Groups", groupList);

        List<Map<String, Object>> players = new ArrayList<>();

        players.add(player1);

        config.put("Players", players);
        return config;
    }

    public void reload() {
        this.groups = loadDefaultConfig("groups");
        this.players = loadDefaultConfig("players");
        this.config = loadDefaultConfig();
        try {
            this.permissionHandler = new PermissionHandler(this);
        } catch (MultipleDefaultGroupsException e) {
            e.printStackTrace();
        } catch (NoDefaultGroupException e) {
            e.printStackTrace();
        }
        this.messages = new Messages();
    }

    /*
        TODO: Make sure all commands use the PermissionsAPI and not the inbuilt function.
        TODO: Go through everything to make sure it will work.
        TODO: Comment this whole plugin :)
     */
    @Override
    public boolean onCommand(Connection connection, String from, String id, String cmd, String[] args) {
        if (cmd.toLowerCase().equals("mp") || cmd.toLowerCase().equals("minatsupermission") || cmd.toLowerCase().equals("minatsupermissions")) {
            if (args == null) {
                System.out.println("Args is null.");
                help(connection, id);
                return true;
            }
            Player player = null;
            Group group = null;
            if (args.length > 1) {
                player = getPermissionsApi().getPlayer(args[1]);
                group = getPermissionsApi().getGroup(args[1]);
            }
            switch (args.length) {
                case 0:
                    help(connection, id);
                    return true;
                case 1:
                    if (args[0].toLowerCase().equals("help")) {
                        //mp help
                        help(connection, id);
                        return true;
                    }
                    if (args[0].toLowerCase().equals("reload")) {
                        if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.reload")) {
                            reload();
                            connection.sendMessage(from, messages.getMessage("reloadPlugin"));
                            return true;
                        }
                        connection.sendMessage(id, messages.getMessage("noPerm"));
                        return true;
                    }
                    if (args[0].toLowerCase().equals("group") || args[0].toLowerCase().equals("groups")) {
                        //mp group/groups
                        if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.groups")) {
                            connection.sendMessage(id, messages.getMessage("registeredGroups") + "\n" + Utils.groupToString(permissionsApi.getGroups()));
                            return true;
                        }
                        connection.sendMessage(id, messages.getMessage("noPerm"));
                        return true;
                    }
                    //Invalid command
                    connection.sendMessage(id, messages.getMessage("invalidCommand"));
                    return true;
                case 2:
                    if (args[0].toLowerCase().equals("group") || args[0].toLowerCase().equals("groups")) {
                        if (args[1].toLowerCase().equals("list")) {
                            //mp group list
                            if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.groups")) {
                                connection.sendMessage(id, messages.getMessage("registeredGroups") + "\n" + Utils.groupToString(permissionsApi.getGroups()));
                                return true;
                            }
                            connection.sendMessage(id, messages.getMessage("noPerm"));
                            return true;
                        } else if (group != null) {
                            //mp group <group>
                            if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.info")) {
                                connection.sendMessage(id, Utils.groupInfoToString(group));
                                return true;
                            }
                            connection.sendMessage(id, messages.getMessage("noPerm"));
                            return true;
                        }
                    }
                    if (args[0].toLowerCase().equals("player") || args[0].toLowerCase().equals("players")) {
                        if (player != null) {
                            //mp player <player>
                            if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.info")) {
                                connection.sendMessage(id, Utils.playerInfoToString(player));
                                return true;
                            }
                            connection.sendMessage(id, messages.getMessage("noPerm"));
                            return true;
                        }
                    }
                    //Invalid command.
                    connection.sendMessage(id, messages.getMessage("invalidCommand"));
                    return true;
                case 3:
                    if (args[0].toLowerCase().equals("group") || args[0].toLowerCase().equals("groups")) {
                        if (args[1].toLowerCase().equals("create")) {
                            //mp group create <group>
                            if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.create")) {
                                if (permissionsApi.isGroup(args[2])) {
                                    connection.sendMessage(id, messages.getMessage("alreadyGroup", args[2]));
                                    return true;
                                } else {
                                    permissionsApi.createGroup(args[2]);
                                    connection.sendMessage(id, messages.getMessage("createdGroup", args[2]));
                                    return true;
                                }
                            }
                            connection.sendMessage(id, messages.getMessage("noPerm"));
                            return true;
                        } else if (args[1].toLowerCase().equals("delete")) {
                            //mp group delete <group>
                            if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.create")) {
                                if (!permissionsApi.isGroup(args[2])) {
                                    connection.sendMessage(id, messages.getMessage("notGroup", args[2]));
                                    return true;
                                } else {
                                    permissionsApi.deleteGroup(args[2]);
                                    connection.sendMessage(id, messages.getMessage("deleteGroup", args[2]));
                                    return true;
                                }
                            }
                            connection.sendMessage(id, messages.getMessage("noPerm"));
                            return true;
                        } else if (group != null) {
                            if (args[2].toLowerCase().equals("perm") || args[2].toLowerCase().equals("perms")) {
                                //mp group <group> perms
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.perms.info")) {
                                    connection.sendMessage(id, Utils.groupPermsToString(group));
                                    return true;
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[2].toLowerCase().equals("players") || args[2].toLowerCase().equals("player")) {
                                //mp group <group> players
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.players")) {
                                    connection.sendMessage(id, Utils.groupPlayersToString(group));
                                    return true;
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            }
                        }
                    }
                    if (args[0].toLowerCase().equals("player") || args[0].toLowerCase().equals("players")) {
                        if (group != null) {
                            if (args[2].toLowerCase().equals("group") || args[2].toLowerCase().equals("groups")) {
                                //mp player <player> group
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.groups")) {
                                    connection.sendMessage(id, Utils.playerGroupsToString(player));
                                    return true;
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                            } else if (args[2].toLowerCase().equals("perm") || args[2].toLowerCase().equals("perms")) {
                                //mp player <player> perm
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.perms.info")) {
                                    connection.sendMessage(id, Utils.playerPermsToString(player));
                                    return true;
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[2].toLowerCase().equals("info")) {
                                //mp player <player> info
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.info")) {
                                    connection.sendMessage(id, Utils.playerInfoToString(player));
                                    return true;
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            }
                        }
                    }
                    //Invalid command
                    connection.sendMessage(id, messages.getMessage("invalidCommand"));
                    return true;
                case 4:
                    if (args[0].toLowerCase().equals("group") || args[0].toLowerCase().equals("groups")) {
                        if (group != null) {
                            if (args[2].toLowerCase().equals("add")) {
                                //mp group <group> add <perm>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.perms.add")) {
                                    if (permissionsApi.hasGroupPermission(group, args[3])) {
                                        connection.sendMessage(id, messages.getMessage("groupAlreadyPerms", args[1], args[3]));
                                        return true;
                                    }else{
                                        permissionsApi.addGroupPermission(group, args[3]);
                                        connection.sendMessage(id, messages.getMessage("groupPermAdded", args[1], args[3]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[2].toLowerCase().equals("del")) {
                                //mp group <group> del <perm>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.perms.del")) {
                                    if (!permissionsApi.hasGroupPermission(group, args[3])) {
                                        connection.sendMessage(id, messages.getMessage("groupDontPerm", args[1], args[3]));
                                        return true;
                                    }else{
                                        permissionsApi.delGroupPermission(group, args[3]);
                                        connection.sendMessage(id, messages.getMessage("groupDelPerm", args[1], args[3]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[2].toLowerCase().equals("has")) {
                                //mp group <group> has <perm>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.group.perms.has")) {
                                    if (permissionsApi.hasGroupPermission(group, args[3])) {
                                        connection.sendMessage(id, messages.getMessage("groupHasPerm", args[1], args[3]));
                                        return true;
                                    }else{
                                        connection.sendMessage(id, messages.getMessage("groupNoHasPerm", args[1], args[3]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            }
                        }
                    }
                    if (args[0].toLowerCase().equals("player") || args[0].toLowerCase().equals("players")) {
                        if (player != null) {
                            if (args[2].toLowerCase().equals("add")) {
                                //mp player <player> add <perm>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.perms.add")) {
                                    if (permissionsApi.hasPlayerPermission(player, args[3])) {
                                        connection.sendMessage(id, messages.getMessage("playerAlreadyPerms", args[1], args[3]));
                                        return true;
                                    }else{
                                        permissionsApi.addPlayerPermission(player, args[3]);
                                        connection.sendMessage(id, messages.getMessage("playerPermAdded", args[1], args[3]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[2].toLowerCase().equals("del")) {
                                //mp player <player> del <perm>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.perms.del")) {
                                    if (!permissionsApi.hasPlayerPermission(player, args[3])) {
                                        connection.sendMessage(id, messages.getMessage("playerDontPerm", args[1], args[3]));
                                        return true;
                                    }else{
                                        permissionsApi.delPlayerPermission(player, args[3]);
                                        connection.sendMessage(id, messages.getMessage("playerDelPerm", args[1], args[3]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[2].toLowerCase().equals("has")) {
                                //mp player <player> has <perm>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.perms.has")) {
                                    if (permissionsApi.hasPlayerPermission(player, args[3])) {
                                        connection.sendMessage(id, messages.getMessage("playerHasPerm", args[1], args[3]));
                                        return true;
                                    }else{
                                        connection.sendMessage(id, messages.getMessage("playerNoHasPerm", args[1], args[3]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            }
                        }
                    }
                    //invalid command
                    connection.sendMessage(id, messages.getMessage("invalidCommand"));
                    return true;
                case 5:
                    if (args[0].toLowerCase().equals("player") || args[0].toLowerCase().equals("players")) {
                        if (player != null && args[2].toLowerCase().equals("group")) {
                            if (args[3].toLowerCase().equals("add")) {
                                //mp player <player> add group <group>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.group.add")) {
                                    Group g = permissionsApi.getGroup(args[4]);
                                    if (g == null) {
                                        connection.sendMessage(id, messages.getMessage("invalidGroup", args[4]));
                                        return true;
                                    }
                                    if (permissionsApi.hasGroup(player, g)){
                                        connection.sendMessage(id, messages.getMessage("playerAlreadyGroup", args[1], args[4]));
                                        return true;
                                    }else {
                                        permissionsApi.addToGroup(player, group);
                                        connection.sendMessage(id, messages.getMessage("playerNowGroup", args[1], args[4]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[3].toLowerCase().equals("del")) {
                                //mp player <player> del group <group>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.group.del")) {
                                    Group g = permissionsApi.getGroup(args[4]);
                                    if (g == null) {
                                        connection.sendMessage(id, messages.getMessage("invalidGroup", args[4]));
                                        return true;
                                    }
                                    if (!permissionsApi.hasGroup(player, g)){
                                        connection.sendMessage(id, messages.getMessage("playerNoGroup", args[1], args[4]));
                                        return true;
                                    }else {
                                        permissionsApi.removeFromGroup(player, g);
                                        connection.sendMessage(id, messages.getMessage("playerDelGroup", args[1], args[4]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            } else if (args[3].toLowerCase().equals("has")) {
                                //mp player <player> has group <group>
                                if (permissionsApi.hasPlayerPermission(from, "minatsupermissions.player.group.has")) {
                                    Group g = permissionsApi.getGroup(args[4]);
                                    if (g == null) {
                                        connection.sendMessage(id, messages.getMessage("invalidGroup", args[4]));
                                        return true;
                                    }
                                    if (permissionsApi.hasGroup(player, g)){
                                        connection.sendMessage(id, messages.getMessage("playerHasGroup", args[1], args[4]));
                                        return true;
                                    }else {
                                        connection.sendMessage(id, messages.getMessage("playerNoHasGroup", args[1], args[4]));
                                        return true;
                                    }
                                }
                                connection.sendMessage(id, messages.getMessage("noPerm"));
                                return true;
                            }
                        }
                    }
                    //invalid command
                    connection.sendMessage(id, messages.getMessage("invalidCommand"));
                    return true;
                default:
                    help(connection, id);
                    return true;
            }
        }
        return false;
    }

    public void help(Connection connection, String id) {
        System.out.println("Help command ran.");
        System.out.println(id);
        String s = "";
        s +="-|-Help page-|-\n";
        s += "mp group (group) add (perm) - Add a permission node to a group.\n";
        s += "mp group (group) del (perm) - Delete a permission node from a group.\n";
        s += "mp group (group) has (perm) - Check if a group has a specific permission node.\n";
        s += "mp group (group) players - Returns all players in a group.\n";
        s += "mp group (group) perms - Returns all perms that the group has access to\n";
        s += "mp group (group) info - Returns a summary of the existing group.\n";
        s += "mp group (group) - Returns a summary for a existing group.\n";
        s += "mp group create (group) - Create a new group.\n";
        s += "mp group delete (group) - Delete a existing group.\n";
        s += "mp group list - Returns all registered groups.\n";
        s += "mp group - Returns all registered groups.\n";
        s += "mp player (player) group add (group) - Add a group to a player.\n";
        s += "mp player (player) group del (group) - Delete a group from a player.\n";
        s += "mp player (player) group has (group) - Check if a player has a specific group\n";
        s += "mp player (player) add (perm - Add a permission node to a player.\n";
        s += "mp player (player) del (perm) - Delete a permission node from a player.\n";
        s += "mp player (player) has (perm) - Check if a player has access to a permission node.\n";
        s += "mp player (player) groups - Returns all groups the player is a member of.\n";
        s += "mp player (player) perms - Returns all permission nodes the player has access to.\n";
        s += "mp player (player) info - Returns a summary of a player.\n";
        s += "mp player (player) - Returns a summary for a player.";

        System.out.println(s);
        connection.sendMessage(id, s);
    }
    public Map getConfig() {
        return config;
    }

    public Map getGroups() {
        return groups;
    }

    public Map getPlayers() {
        return players;
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public PermissionsApi getPermissionsApi() {
        return permissionsApi;
    }

    public Messages getMessages() {
        return messages;
    }
}
