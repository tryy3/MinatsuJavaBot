package us.tryy3.java.minatsuskype.skypejava;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;

/**
 * Created by dennis.planting on 11/6/2015.
 */
public class ChatListener implements ChatMessageListener {

    public void chatMessageReceived(ChatMessage chatMessage) throws SkypeException {
        try {
            System.out.println(chatMessage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void chatMessageSent(ChatMessage chatMessage) throws SkypeException {
        try {
            System.out.println(chatMessage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
