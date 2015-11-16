package us.tryy3.minatsuskype.plugins.testplugin;

import us.tryy3.java.minatsuskype.events.Listener;
import us.tryy3.java.minatsuskype.events.onCommandEvent;

/**
 * Created by dennis.planting on 11/10/2015.
 */
public class commandListener implements Listener {
    public void onCommand(onCommandEvent event) {
        System.out.println("Event fired");
    }
}
