package us.tryy3.java.minatsuskype;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;

/**
 * Created by dennis.planting on 11/7/2015.
 */
public class TCPClient {
    private Socket socket;
    private Bot bot;
    public TCPClient(final String host, final int port, final Bot bot) {
        this.bot = bot;
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
                        System.out.println("Connection try #" + timeout + ", trying again in " + timeOut);
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
            System.out.println("Waiting for reply.");
            JsonArray json = new JsonParser().parse(in.readLine()).getAsJsonArray();
            if (json == null || json.size() <= 0) {
                System.out.println("Something went wrong when sending json.");
                continue;
            }
            bot.read(json);
        }
    }

    public void write(String test) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Writing a message");
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
