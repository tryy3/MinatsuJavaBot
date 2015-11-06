# Minecraft Alerts
## Features
- Able to alert if a server goes down.
- Able to return simple info like TPS, mem and even mem/cpu usage on the dedi.
- Able to setup scheduled alerts.

## Commands
- cmd check (server) - Check if server is added.
- cmd add (server) (ip) (port) - Add a new server.
- cmd del (server) - Remove a server.
- cmd get (server) - Returns both front and back info.
- cmd get front (server) - Returns all front info aka the mc server.
- cmd get front tps (server) - Returns the current tps (1 min, 5min, 15min).
- cmd get front mem (server) - Returns the current mem (Average, total, free).
- cmd get front disk (server) - Returns the disk usage of the server (will return all worlds, plugin and total usage).
- cmd get front uptime (server) - Returns the uptime of the mc server.
- cmd get back (server) - Returns all back info aka the dedi/vps itself.
- cmd get back mem (server) - Returns the current mem usage (Average, total, free).
- cmd get back cpu (server) - Returns the current cpu usage.
- cmd get back disk (server) - Returns the disk usage of the whole dedi/vps.
- cmd get back uptime (server) - Returns the uptime of the dedi/vps.

## Notes
- Some of this commands will require plugins to be installed on the server.
- This plugin requires a extra software to be installed on the dedi/vps that will start a TCP server.
- Current plans only include a extra software, I might make a bukkit plugin to get just front info.