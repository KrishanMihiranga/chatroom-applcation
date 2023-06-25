package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements java.lang.Runnable {

    public static final List<ClientHandler> clientHandlerList = new ArrayList<>();
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final String clientName;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientName = inputStream.readUTF();
        clientHandlerList.add(this);
    }

    public static void broadcastMessage(String message) throws IOException {
        for (ClientHandler handler : clientHandlerList) {
            handler.sendMessage("SERVER", message);
        }
    }


    @Override
    public void run() {
         while (socket.isConnected()) {
            try {
                String utf = inputStream.readUTF();
                for (ClientHandler handler : clientHandlerList) {
                    if (!handler.clientName.equals(clientName)) {
                        handler.sendMessage(clientName, utf);
                    }
                }

            } catch (IOException e) {
                clientHandlerList.remove(this);
                break;
            }
        }
    }

    public void sendMessage(String sender, String msg) throws IOException {
        outputStream.writeUTF(sender + ": " + msg);
        outputStream.flush();
    }

}
