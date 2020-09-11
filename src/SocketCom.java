import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketCom {
    private String URL = "localhost";
    private int PORT = 6969;



    public SocketCom(String URL, int PORT) {
        this.URL = URL;
        this.PORT = PORT;
    }

    public SocketCom() {
    }

    public void sendMessage(MessagePacket messagePacket) throws IOException {
        Socket socket = new Socket(this.URL, PORT);
        var stream = new ObjectOutputStream(socket.getOutputStream());
        stream.writeObject(messagePacket);
        stream.flush();
        stream.close();
        socket.close();
    }



    public MessagePacket getMessage() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(this.PORT);
        System.out.println("Waiting for client to connect...");
        Socket socket = serverSocket.accept();
        System.out.println("One client connected.");
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        MessagePacket messagePacket = (MessagePacket) objectInputStream.readObject();
        System.out.println("Message received");
        objectInputStream.close();
        socket.close();
        serverSocket.close();
        return messagePacket;
    }

}
