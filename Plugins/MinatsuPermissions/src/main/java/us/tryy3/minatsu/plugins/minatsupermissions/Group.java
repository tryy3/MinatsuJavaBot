package us.tryy3.minatsu.plugins.minatsupermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis.planting on 11/15/2015.
 */
public class Group {
    private  String name;
    private List<String> permissions;
    private List<Player> playerList;

    public Group(String name, List<String> permissions) {
        this.name = name;
        this.permissions = permissions;
        this.playerList = new ArrayList<Player>();
    }

    public boolean hasPermission(String p) {
        for (String s : permissions) {
            if (s.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean addPermission(String p) {
        if (hasPermission(p)) return false;

        permissions.add(p);
        return true;
    }

    public boolean delPermission(String p) {
        if (!hasPermission(p)) return false;

        permissions.remove(p);
        return true;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean hasPlayer(Player player) {
        for (Player p : playerList) {
            if (p.equals(player)) {
                return true;
            }
        }

        return false;
    }

    public boolean addPlayer(Player player) {
        if (hasPlayer(player)) return false;

        playerList.add(player);
        return true;
    }

    public boolean delPlayer(Player player) {
        if (!hasPlayer(player)) return false;

        playerList.remove(player);
        return true;
    }

    public List<Player> getPlayers() {
        return playerList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
