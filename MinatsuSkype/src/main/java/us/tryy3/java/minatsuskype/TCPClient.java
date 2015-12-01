package us.tryy3.java.minatsuskype;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import us.tryy3.java.minatsuskype.logger.PluginLogger;

import java.io.*;
import java.net.*;

/**
 * Created by dennis.planting on 11/7/2015.
 */
public class TCPClient {
    private Socket socket;
    private PluginLogger logger = new PluginLogger(null);
    // TODO: Change this to a TCP Server and make listeners host TCP Clients instead.
    public TCPClient(final String host, final int port, final Bot bot) {
        logger.info("Initalizing TCP server on %s:%s", host, port);
        final Thread[] threads = new Thread[1];
        threads[0] = new Thread(new Runnable() {
            public void run() {
                boolean runConnect = true;
                int timeout = 0;
                while(runConnect) {
                    try {
                        connect(host, port, bot);
                        runConnect = false;
                        timeout = 0;
                    } catch (IOException e) {
                        int timeOut;
                        timeout++;
                        if (timeout <= 6) {
                            timeOut = timeout * 10;
                        } else {
                            timeOut = 60;
                        }
                        logger.info("Connection try #" + timeout + ", trying again in " + timeOut);
                        try {
                            threads[0].sleep(timeOut * 1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        threads[0].start();
    }

    public boolean connect(String host, int port, Bot bot) throws IOException {
        socket = new Socket(host, port);
        while(true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            logger.info("TCP connection found at %s:%s, waiting for reply.", host, port);
            JsonArray json = new JsonParser().parse(in.readLine()).getAsJsonArray();
            if (json == null || json.size() <= 0) {
                logger.severe("Got a TCP request, but the json was invalid.");
                continue;
            }
            bot.read(json);
        }
    }

    public void write(String test) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            logger.info("Writing a message to TCP Clients.");
            out.writeUTF(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessage(String id, String msg) {
        JsonArray writeArgs = new JsonArray();
        JsonArray msgArgs = new JsonArray();

        msgArgs.add(id);
        msgArgs.add(msg);

        writeArgs.add("sendMessage");
        writeArgs.add(msgArgs);

        write(writeArgs.toString());
    }
}
