package us.tryy3.minatsu.plugins.minatsupermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis.planting on 11/15/2015.
 */
/*
    TODO: Go through all functions and support both String and Group/Player
    TODO: Support regex in hasPermission
 */
public class PermissionsApi {
    Main main;

    public PermissionsApi(Main main) {
        this.main = main;
    }

    /* Groups */
    public boolean addGroupPermission(Group group, String perm) {
        boolean r = group.addPermission(perm);
        main.getPermissionHandler().saveGroups();
        return r;
    }

    public boolean delGroupPermission(Group group, String perm) {
        boolean r = group.delPermission(perm);
        main.getPermissionHandler().saveGroups();
        return r;
    }

    public boolean hasGroupPermission(Group group, String perm) {
        return group.hasPermission(perm);
    }

    public List<String> getGroupPermission(Group group) {
        return group.getPermissions();
    }

    public List<Player> getPlayers(Group group) {
        return group.getPlayers();
    }

    public boolean createGroup(String groupName) {
        if (main.getPermissionHandler().isGroup(groupName)) return false;

        main.getPermissionHandler().addGroup(new Group(groupName, new ArrayList<String>()));
        main.getPermissionHandler().saveGroups();
        return true;
    }

    public boolean deleteGroup(String group) {
        return deleteGroup(getGroup(group));
    }
    public boolean deleteGroup(Group group) {
        main.getPermissionHandler().delGroup(group);
        main.getPermissionHandler().saveGroups();
        return true;
    }

    public boolean isGroup(String groupName) {
        return main.getPermissionHandler().isGroup(groupName);
    }

    public Group getGroup(String groupname) {
        return main.getPermissionHandler().getGroup(groupname);
    }

    /* Players */
    public boolean addPlayerPermission(Player player, String perm) {
        boolean r = player.addPermission(perm);
        main.getPermissionHandler().savePlayers();
        return r;
    }

    public boolean delPlayerPermission(Player player, String perm) {
        boolean r = player.delPermission(perm);
        main.getPermissionHandler().savePlayers();
        return r;
    }

    public boolean hasPlayerPermission(String player, String perm) {
        return getPlayer(player).hasPermission(perm);
    }
    public boolean hasPlayerPermission(Player player, String perm) {
        return player.hasPermission(perm);
    }

    public List<String> getPlayerPermissions(Player player) {
        return player.getPermissions();
    }

    public List<Group> getPlayerGroups(Player player) {
        return player.getGroups();
    }

    public boolean addToGroup(Player player, Group group) {
        boolean playerBol = player.addGroup(group);
        boolean groupBol = group.addPlayer(player);

        main.getPermissionHandler().savePlayers();

        return (playerBol && groupBol);
    }

    public boolean removeFromGroup(Player player, Group group) {
        boolean playerBol = player.delGroup(group);
        boolean groupBol = group.delPlayer(player);

        main.getPermissionHandler().savePlayers();

        return (playerBol && groupBol);
    }

    public boolean hasGroup(Player player, Group group) {
        return player.hasGroup(group);
    }

    public Player getPlayer(String player) {
        return main.getPermissionHandler().getPlayer(player);
    }

    /* Misc */
    public List<Group> getGroups() {
        return main.getPermissionHandler().getGroupList();
    }
}
