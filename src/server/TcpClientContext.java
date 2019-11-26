package server;

import protocol.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClientContext implements Runnable {
    private final Socket socket;
    private final TcpServerHandlers handlers;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    public TcpClientContext(Socket socket, TcpServerHandlers handlers) throws IOException {
        this.socket = socket;
        this.handlers = handlers;
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
            try {
                Object received = input.readObject();
                handlers.handle(this, received);
            } catch (Exception ignored) {}
            finally {
                try {
                    socket.close();
                } catch (IOException e1) {}
            }
    }

    public void write(Message message) {
        try {
            this.output.writeObject(message);
        } catch (IOException e) {}
    }
}
