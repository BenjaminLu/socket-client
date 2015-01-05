import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2014/12/22.
 */
public class Client
{
    private ObjectOutputStream sender;
    private ClientCallBack callBack;
    private ObjectInputStream receiver;
    private Socket socket;
    private boolean isConnected = false;
    public Client() {}

    public void setHost(String ip, int port)
    {
        try {
            socket = new Socket(ip, port);
            isConnected = true;
            sender = new ObjectOutputStream(socket.getOutputStream());
            receiver = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
                isConnected = false;
                callBack.onSocketClosed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (isConnected) {
                    Object readObject = null;
                    try {
                        readObject = receiver.readObject();
                    } catch (IOException e) {
                        try {
                            socket.close();
                            isConnected = false;
                            callBack.onSocketClosed();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            isConnected = false;
                        }
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    callBack.onMessage(readObject);
                }
            }
        }).start();
    }

    public void send(Object object) throws IOException
    {
        sender.writeObject(object);
        sender.flush();
    }

    public void setCallBack(ClientCallBack callBack)
    {
        this.callBack = callBack;
    }

    public void close()
    {
        try {
            socket.close();
            callBack.onSocketClosed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
