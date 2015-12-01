package us.tryy3.java.minatsuskype.logger;

/**
 * Created by dennis.planting on 12/1/2015.
 */
public enum Level {
    SEVERE("Severe"),
    WARNING("Warning"),
    INFO("Info"),
    CONFIG("Config"),
    FINE("Fine"),
    FINER("Finer"),
    FINEST("Finest"),
    ALL("All");

    String name;

    Level(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
