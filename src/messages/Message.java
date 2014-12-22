package messages;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/12/22.
 */
public class Message implements Serializable
{
    private String message = "message";
    public Message(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
