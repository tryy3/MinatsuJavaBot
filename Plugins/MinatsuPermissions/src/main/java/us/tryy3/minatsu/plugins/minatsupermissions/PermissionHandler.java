package us.tryy3.minatsu.plugins.minatsupermissions;

import us.tryy3.minatsu.plugins.minatsupermissions.exceptions.MultipleDefaultGroupsException;
import us.tryy3.minatsu.plugins.minatsupermissions.exceptions.NoDefaultGroupException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dennis.planting on 11/15/2015.
 */
public class PermissionHandler {
    private MinatsuPermissions minatsuPermissions;

    private List<Group> groupList;
    private List<Player> playerList;
    private Group defaultGroup;

    public PermissionHandler(MinatsuPermissions minatsuPermissions) throws MultipleDefaultGroupsException, NoDefaultGroupException {
        this.minatsuPermissions = minatsuPermissions;
        init();
    }

    public void init() throws MultipleDefaultGroupsException, NoDefaultGroupException {
        loadGroups();
        loadPlayers();
    }

    public void savePlayers() {
        Map<String, List<Map<String, Object>>> players = new HashMap<>();

        List<Map<String, Object>> playersList = new ArrayList<>();

        for (Player p : this.playerList) {
            Map<String, Object> player = new HashMap<>();

            player.put("Name", p.getName());
            player.put("Permissions", p.getPermissions());
            player.put("Groups", p.getGroups());
            playersList.add(player);
        }
        players.put("Players", playersList);

        minatsuPermissions.saveDefaultConfig("players", players);
    }

    public void saveGroups() {
        Map<String, List<Map<String, Object>>> groups = new HashMap<>();

        List<Map<String, Object>> groupsList = new ArrayList<>();

        for (Group g : this.groupList) {
            Map<String, Object> group = new HashMap<>();

            group.put("Name", g.getName());
            group.put("Permissions", g.getPermissions());

            groupsList.add(group);
        }
        groups.put("Groups", groupsList);

        minatsuPermissions.saveDefaultConfig("groups", groups);
    }

    public void loadPlayers() {
        this.playerList = new ArrayList<>();

        Map players = minatsuPermissions.getPlayers();
        List<Map<String, Object>> p = (List<Map<String, Object>>) players.get("Players");
        for (Map<String, Object> map : p) {
            Player player = new Player((String) map.get("Name"), (ArrayList<String>) map.get("Permissions"));
            List<Group> groupList = new ArrayList<>();
            for (String s : (ArrayList<String>) map.get("Groups")) {
                Group group = getGroup(s);
                groupList.add(group);
                player.addGroup(group);
                group.addPlayer(player);
            }
            if (!player.hasGroup(defaultGroup)) {
                this.minatsuPermissions.getPermissionsApi().addToGroup(player, defaultGroup);
            }
            this.playerList.add(player);
        }
    }

    public void loadGroups() throws MultipleDefaultGroupsException, NoDefaultGroupException {
        this.groupList = new ArrayList<>();

        Map groups = minatsuPermissions.getGroups();
        List<Map<String, Object>> g = (List<Map<String, Object>>) groups.get("Groups");
        for (Map<String, Object> map : g) {
            Group group = new Group((String) map.get("Name"), (List<String>) map.get("Permissions"));
            if (defaultGroup == null && (boolean) map.get("Default")) {
                defaultGroup = group;
            } else if (defaultGroup != null && (boolean) map.get("Default")) {
                throw new MultipleDefaultGroupsException("Found one or more default groups.");
            }
            groupList.add(group);
        }
        if (defaultGroup == null) {
            throw new NoDefaultGroupException("Can't find a default group.");
        }
    }

    public boolean isGroup(String group) {
        for (Group groups : groupList) {
            if (groups.getName().equals(group)) {
                return true;
            }
        }
        return false;
    }

    public Group getGroup(String group) {
        for (Group groups : groupList) {
            if (groups.getName().equals(group)) {
                return groups;
            }
        }
        return null;
    }

    public Player getPlayer(String player) {
        for (Player players : playerList) {
            if (players.getName().equals(player)) {
                return players;
            }
        }
        return null;
    }

    public void addGroup(Group group) {
        groupList.add(group);
    }

    public void delGroup(Group group) {

    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
