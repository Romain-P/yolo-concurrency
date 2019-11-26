package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer extends Thread {
    private final TcpServerHandlers handlers;
    private final ExecutorService worker;
    private final int port;
    private final int limit;

    public TcpServer(int port, int limit, int poolSize) {
        this.port = port;
        this.limit = limit;
        this.handlers = new TcpServerHandlers();
        this.worker = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        ServerSocket server;

        try {
            server = new ServerSocket(port, limit);
            server.setReuseAddress(true);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        while (!isInterrupted()) {
            Socket newConnection;
            try {
                newConnection = server.accept();
            } catch (IOException e) { continue; }

            try {
                worker.execute(new TcpClientContext(newConnection, handlers));
            } catch (Exception e) {}
        }
    }
}
