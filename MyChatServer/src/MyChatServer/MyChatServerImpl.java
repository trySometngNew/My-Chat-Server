/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyChatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import MyChatServer.HandleClientImpl;
import MyChatServer.iHandleClient;

/**
 * *
 *
 * @author sourabh_lonikar
 */
public class MyChatServerImpl implements iMyChatServer {

    public static List<String> Users = new ArrayList<>();
    public static List<HandleClientImpl> clientList = new ArrayList<>();

    private MyChatServerImpl() {
    }

    public static MyChatServerImpl getInstance() {
        return MyChatServerHolder.INSTANCE;
    }

    @Override
    public void messageHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This process continuously scans for incoming client connections and calls HandleClient for each client. It also imposes connections limits.
     *
     * @author Sourabh_Lonikar
     * @since v1.0 (07/02/2014)
     * @see HandleClientImpl
     */
    @Override
    public void process() {
        try {
            ServerSocket server = new ServerSocket(CHAT_SERVER_PORT, iMyChatServer.CLIENT_THREAD_LIMIT);
            System.out.println("Server connected...");
            while (true) {
                Socket socket;
                socket = server.accept();
                HandleClientImpl client = new HandleClientImpl(socket);
                clientList.add(client);
            }
        } catch (IOException ex) {
            Logger.getLogger(MyChatServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create new Instance of Chat Server.
     *
     * @param args
     */
    public static void main(String[] args) {
        new MyChatServerImpl().process();
    }

    @Override
    public void exit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Broadcasts message to all users when requested by one user.
     *
     * @param User
     * @param message
     */
    @Override
    public void broadcastMessage(iHandleClient user, String message) {
        for (iHandleClient client : clientList) {
            // Compile errors to be resolved!!
            /** 
            if (!client.equals(user)) {
                client.sendMessage(message);
            } else {
                client.sendMessage("Message was successfully Broadcasted!");
            } */
        }

    }

    private static class MyChatServerHolder {

        private static final MyChatServerImpl INSTANCE = new MyChatServerImpl();
    }
}
