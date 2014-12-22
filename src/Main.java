import messages.Message;

/**
 * Created by Administrator on 2014/12/22.
 */
public class Main
{
    public static void main(String[] args)
    {
        Client client = Client.getInstance().setHost("127.0.0.1", 5443);
        client.setCallBack(new ClientCallBack()
        {
            @Override
            public void onMessage(Object object)
            {
                if(object instanceof Message) {
                    onMessage((Message) object);
                }
            }

            public void onMessage(Message message)
            {
                System.out.println(message.getMessage());
            }
        });
        client.send(new Message("Client Request 1"));
        client.send(new Message("Client Request 2"));
        client.send(new Message("Client Request 3"));
        client.close();
    }
}
