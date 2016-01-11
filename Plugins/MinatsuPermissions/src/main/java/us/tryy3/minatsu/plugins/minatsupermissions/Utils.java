package us.tryy3.minatsu.plugins.minatsupermissions;

import java.util.List;

/**
 * Created by dennis.planting on 11/15/2015.
 */
public class Utils {
    public static String groupToString(List<Group> groups) {
        String s = "";
        for (Group g : groups) {
            s += "  " + g.getName() + "\n";
        }
        return s.substring(0, s.length()-1);
    }

    public static String groupInfoToString(Group group) {
        String s = "";
        s += "Group Name: " + group.getName() + "\n";
        s += groupPermsToString(group);
        return s;
    }
    public static String groupPermsToString(Group group) {
        String s = "";
        s += "Permissions:\n";
        List<String> perms = group.getPermissions();
        for (int i = 0; i < perms.size(); i++) {
            s += "  (" + (i +1) + ") " + perms.get(i) + "\n";
        }
        return s;
    }
    public static String groupPlayersToString(Group group) {
        String s = "";
        s += "Members:\n";
        for (Player player : group.getPlayers()) {
            s+= "  " + player.getName() + "\n";
        }
        return s;
    }

    public static String playerToString(List<Player> players) {
        String s = "";
        for (Player g : players) {
            s += g.getName() + ", ";
        }
        return s.substring(0, s.length()-1);
    }

    public static String playerInfoToString(Player player) {
        String s = "";
        s += "Player Name: " + player.getName() + "\n";
        s += playerPermsToString(player);
        s += playerGroupsToString(player);
        return s;
    }
    public static String playerPermsToString(Player player) {
        String s = "";
        s += "Permissions:\n";
        List<String> perms = player.getPermissions();
        for (int i = 0; i < perms.size(); i++) {
            s += "(" + (i + 1) + ") " + perms.get(i) + "\n";
        }
        return s;
    }
    public static String playerGroupsToString(Player player) {
        String s = "";
        s += "Groups:\n";
        List<String> perms = player.getPermissions();
        for (Group group : player.getGroups()) {
            s += "  " + group.getName() + "\n";
        }
        return s;
    }
}
