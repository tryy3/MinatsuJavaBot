package us.tryy3.java.minatsu;

import java.util.Arrays;

/**
 * Created by tryy3 on 2016-01-09.
 */
public class Options {
    private String pluginPath = "./plugins";
    private int port = 6868;

    public Options(String[] args) {
        System.out.println(Arrays.toString(args));
    }

    private void checkPath(String s) {
        pluginPath = (s != null && s.equalsIgnoreCase("")) ? s : pluginPath;
    }
    private void checkPort(String s) {
        port = (isInteger(s)) ? Integer.parseInt(s) : port;
    }

    public String getPluginPath() {
        return pluginPath;
    }
    public int getPort() {
        return port;
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}