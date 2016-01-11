# Minatsu
## Installation instructions

*Currently a bit confusing to setup, but will be improved in future updates*

First grab this repo and download it to your local computer or a server.

### Listener

The listener is a node.js listener.

1. Download and install node.js https://nodejs.org/
2. Navigate to MinatsuSkype/src/main/node
3. Now install the dependecies required (npm install skyweb)
4. Lastly run the server with the command "node net.js (username) (password)" with out the parantheses and quotation marks.

### Bot

1. Make sure you have java 7 or above
2. Compile MinatsuSkype and any plugins you want.
3. If you want plugins then put them in a folder called "plugins" next to MinatsuSkype.jar
4. Then just run MinatsuSkype.jar

If you want to change the port that the TCP Server runs on, then first you need to change the PORT and HOST variable at line 17 and 18 in MinatsuSkype/src/main/node/net.js and line 37 in MinatsuSkype/src/main/java/us/tryy3/java/minatsuskype/Bot.java.

In the future there will a lot easier to add multiple Listeners to same Bot and edit the configs.
