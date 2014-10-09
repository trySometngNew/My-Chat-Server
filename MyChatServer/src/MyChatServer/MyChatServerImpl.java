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
import com.sun.corba.se.spi.logging.CORBALogDomains;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * *
 *
 * @author sourabh_lonikar
 */
public class MyChatServerImpl implements iMyChatServer {

    public static List<String> Users = new ArrayList<>();
    public static Map<HandleClientImpl, Integer> clientListMap = new HashMap<>();
    public static Map<Integer, Integer[]> conversationMap = new HashMap<>();
    private int[] freePorts = new int[4];

    private MyChatServerImpl() {
    }

    public static MyChatServerImpl getInstance() {
        return MyChatServerHolder.INSTANCE;
    }

    @Override
    public void messageHandler() {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This process continuously scans for incoming client connections and calls 
     * HandleClient for each client. It also imposes connections limits.
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
            int conversationId = 1;
            while (true) {
                Socket socket;
                socket = server.accept();
                HandleClientImpl client = new HandleClientImpl(socket);
                // Port number which is mapped to acti8ve conversation id
                int portNumber = 0;
                if(clientListMap.size()>=(MyChatServerImpl.CLIENT_THREAD_LIMIT / 2)) {
                    // limit of simultaneous conversations threshold reached
                   Logger.getLogger(MyChatServerImpl.class.getName()).log(Level.INFO, 
                           "Additional connections are not currently possible.");
                        
                } else {
                    // Some port unused. Use them for new connection
                    for(int i=0; i<freePorts.length; i++){
                        if(freePorts[i]!=0) {
                            portNumber = freePorts[i];
                            freePorts[i] = 0;
                            break;
                        }
                    }   
                    clientListMap.put(client, portNumber);
                }
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
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method is called upon by any client when it sstarts active conversation.
     * This will take up free ports as well as start conversation. 
     * It will also inform both parties of start of conversation.
     * Same port numbers will be allocated to chatting partners.
     */
    public void startConversation(int conversationId) {
        Integer[] conversationInfo = new Integer[3];
        int port = 0;
        for(int i=0; i<this.freePorts.length; i++) {
            if(this.freePorts[i]!=0) {
                port = this.freePorts[0];
                this.freePorts[0] = 0;
            }
        }
        
        
    }    
    
    /**
     * This method is called upon by any client when it ends active conversation.
     * This will free up occupied ports as well as save conversation in files 
     * for records. It will also inform both parties of end of conversation.
     * Same port numbers will be allocated to chatting partners.
     */
    public void endConversation(int conversationId) {
        Integer[] conversationInfo = MyChatServerImpl.conversationMap.remove(conversationId);
        int port = conversationInfo[0];
        for(int i=0; i<this.freePorts.length; i++){
            if(this.freePorts[i]==0) {
                this.freePorts[i] = port;
            }
        }
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
