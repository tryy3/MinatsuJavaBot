package us.tryy3.minatsuskype.plugins.minatsupermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis.planting on 11/15/2015.
 */
public class Player {
    private String name;
    private List<Group> groups;
    private List<String> permissions;

    public Player(String name, List<String> permissions) {
        this.groups = new ArrayList<Group>();
        this.permissions = permissions;
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

        this.permissions.add(p);
        return true;
    }

    public boolean delPermission(String p) {
        if (!hasPermission(p)) return false;

        this.permissions.remove(p);
        return true;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Group getGroup(Group g) {
        for (Group group : groups) {
            if (group.equals(g)) {
                return group;
            }
        }
        return null;
    }

    public boolean hasGroup(Group g) {
        return groups.contains(g);
    }

    public boolean addGroup(Group g) {
        if (hasGroup(g)) return false;

        groups.add(g);
        return true;
    }

    public boolean delGroup(Group g) {
        if (!hasGroup(g)) return false;

        groups.remove(g);
        return true;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }
}
