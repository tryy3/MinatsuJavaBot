# Permission System
## Features
- API
- Groups
- Permissions
- Commands
- Negation
- Regex (possibly)

## API
### Group
- addGroupPermission() - Add a perm node to a specific group.
- delGroupPermission() - Delete a perm node from a specific group.
- hasGroupPermission() - Check if group have a specific perm node.
- getGroupPermissions() - Get all perm nodes from a group.
- getPlayers() - Get all players in a group (might not be included).
- createGroup() - Create a new group.
- deleteGroup() - Delete a existing group.
- isGroup() - Check if a group exists.

### Player
- addPlayerPermission() - Add a perm node to a specific player.
- delPlayerPermission() - Delete a perm node from a specific player.
- hasPlayerPermission() - Check if player has a specific perm (will also check from group)
- getPlayerPermissions() - Get all the players perms (will return 2 seperated arrays, one for player perms and one for group perms).
- getGroups() - Get all groups the player is in.
- addToGroup() - Add a player to a specific group.
- removeFromGroup() - Remove a player from a specific group.
- hasGroup() - Check if the player is in a group.

### Misc
- getGroups() - Get all groups.

## Commands
### Group
- cmd (group) add (perm)
- cmd (group) del (perm)
- cmd (group) has (perm)
- cmd (group) players
- cmd (group) create
- cmd (group) delete

### Player
- cmd (player) add (perm)
- cmd (player) del (perm)
- cmd (player) has (perm)
- cmd (player) groups
- cmd (player) group add (group)
- cmd (player) group del (group)
- cmd (player) group has (group)

### Misc
- getGroups()