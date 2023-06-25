package Server;

import controller.ChatServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static Server server;
    private final ServerSocket serverSocket;


    private Server() throws IOException {
        serverSocket = new ServerSocket(3002);
        System.out.println("server started");
    }


    public static Server getServerSocket() throws IOException {
        return server == null ? server = new Server() : server;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            System.out.println("listening...");
            try {
                Socket accepted = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(accepted);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

