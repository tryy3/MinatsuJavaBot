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
    MinatsuPermissions minatsuPermissions;

    public PermissionsApi(MinatsuPermissions minatsuPermissions) {
        this.minatsuPermissions = minatsuPermissions;
    }

    /* Groups */
    public boolean addGroupPermission(Group group, String perm) {
        boolean r = group.addPermission(perm);
        minatsuPermissions.getPermissionHandler().saveGroups();
        return r;
    }

    public boolean delGroupPermission(Group group, String perm) {
        boolean r = group.delPermission(perm);
        minatsuPermissions.getPermissionHandler().saveGroups();
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
        if (minatsuPermissions.getPermissionHandler().isGroup(groupName)) return false;

        minatsuPermissions.getPermissionHandler().addGroup(new Group(groupName, new ArrayList<String>()));
        minatsuPermissions.getPermissionHandler().saveGroups();
        return true;
    }

    public boolean deleteGroup(String group) {
        return deleteGroup(getGroup(group));
    }
    public boolean deleteGroup(Group group) {
        minatsuPermissions.getPermissionHandler().delGroup(group);
        minatsuPermissions.getPermissionHandler().saveGroups();
        return true;
    }

    public boolean isGroup(String groupName) {
        return minatsuPermissions.getPermissionHandler().isGroup(groupName);
    }

    public Group getGroup(String groupname) {
        return minatsuPermissions.getPermissionHandler().getGroup(groupname);
    }

    /* Players */
    public boolean addPlayerPermission(Player player, String perm) {
        boolean r = player.addPermission(perm);
        minatsuPermissions.getPermissionHandler().savePlayers();
        return r;
    }

    public boolean delPlayerPermission(Player player, String perm) {
        boolean r = player.delPermission(perm);
        minatsuPermissions.getPermissionHandler().savePlayers();
        return r;
    }

    public boolean hasPlayerPermission(String player, String perm) {
        Player p = getPlayer(player);
        return (p != null) ? p.hasPermission(perm) : false;
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

        minatsuPermissions.getPermissionHandler().savePlayers();

        return (playerBol && groupBol);
    }

    public boolean removeFromGroup(Player player, Group group) {
        boolean playerBol = player.delGroup(group);
        boolean groupBol = group.delPlayer(player);

        minatsuPermissions.getPermissionHandler().savePlayers();

        return (playerBol && groupBol);
    }

    public boolean hasGroup(Player player, Group group) {
        return player.hasGroup(group);
    }

    public Player getPlayer(String player) {
        return minatsuPermissions.getPermissionHandler().getPlayer(player);
    }

    /* Misc */
    public List<Group> getGroups() {
        return minatsuPermissions.getPermissionHandler().getGroupList();
    }
}
