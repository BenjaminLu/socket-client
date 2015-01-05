import messages.Message;

import java.io.IOException;

/**
 * Created by Administrator on 2014/12/22.
 */
public class Main
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.setHost("127.0.0.1", 5443);
        client.setCallBack(new ClientCallBack()
        {
            @Override
            public void onMessage(Object object)
            {
                if(object instanceof Message) {
                    onMessage((Message) object);
                } else if(object instanceof String) {
                    onMessage((String) object);
                }
            }

            @Override
            public void onSocketClosed()
            {
                System.out.println("Socket Closed");
            }

            public void onMessage(Message message)
            {
                System.out.println(message.getMessage());
            }

            public void onMessage(String message)
            {
                System.out.println(message);
            }
        });
        boolean isConnected = true;
        int i = 0;
        while (isConnected) {
            try {
                client.send("Client Request 1" + i);
                i++;
            } catch (IOException e) {
                isConnected = false;
            }
        }
    }
}
