package client;

import protocol.Message;
import protocol.messages.RequestResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class TcpClient {
    private final int port;

    public TcpClient(int port) {
        this.port = port;
    }

    public void write(Message message, Consumer<RequestResponseMessage> onResponse) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("127.0.0.1", port);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        output.writeObject(message);
        RequestResponseMessage response = (RequestResponseMessage) input.readObject();
        onResponse.accept(response);
    }
}
