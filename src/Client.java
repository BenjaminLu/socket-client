import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/22.
 */
public class Client
{
    private static ObjectOutputStream sender;
    private static Client client = new Client();
    private static ClientCallBack callBack;
    private static ObjectInputStream receiver;
    private static Socket socket;
    private Client() {}

    public static Client getInstance()
    {
        return client;
    }

    public Client setHost(String ip, int port)
    {
        try {
            socket = new Socket(ip, port);
            sender = (new ObjectOutputStream(socket.getOutputStream()));
            receiver = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public void send(Object object)
    {
        try {
            sender.writeObject(object);
            sender.flush();
            Object readObject = receiver.readObject();
            callBack.onMessage(readObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setCallBack(ClientCallBack callBack)
    {
        this.callBack = callBack;
    }

    public void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
