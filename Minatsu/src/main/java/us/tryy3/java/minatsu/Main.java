package us.tryy3.java.minatsu;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public class Main {
    public static void main(String[] args) {
        try {
            new Bot(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
