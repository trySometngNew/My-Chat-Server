/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class takes care of all responsibilities for each client @ server side.
 * It maintains all sessions information, state of client. It uses 
 * <code>MyChatServerImpl</code> for imposing some server side constraints like 
 * number of active users.
 * @see MyChatServerImpl
 * @author sourabh_lonikar
 */
public class HandleClientImpl implements iHandleClient {

    private String name;
    private HandleClientImpl chatPartner;
    private String statusMessage;
    private String chatPartnerStatusMessage;
    BufferedReader input;
    PrintWriter output;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HandleClientImpl getChatPartnerName() {
        return chatPartner;
    }

    public void setChatPartnerName(HandleClientImpl chatPartnerName) {
        this.chatPartner = chatPartnerName;
    }

    public HandleClientImpl(Socket client) throws IOException {
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);
        output.println("Welcome to Sourabh's Chat Server. Please enter your name...");
        String userName = input.readLine();
        MyChatServerImpl.Users.add(userName);
        output.println("Welcome " + userName + ". Nnjoy chatting. Get going...");
        Thread t = new Thread();
        t.setName("Thread_" + userName);
        t.start();
    }

    public HandleClientImpl getChatPartner() {
        return chatPartner;
    }

    public void setChatPartner(HandleClientImpl chatPartner) {
        this.chatPartner = chatPartner;
    }

    public String getChatPartnerStatusMessage() {
        return chatPartnerStatusMessage;
    }

    public void setChatPartnerStatusMessage(String chatPartnerStatusMessage) {
        this.chatPartnerStatusMessage = chatPartnerStatusMessage;
    }

    public boolean pingMe(HandleClientImpl chatPartner) throws IOException {
        if (MyChatServerImpl.CLIENT_THREAD_LIMIT < MyChatServerImpl.Users.size()) {
            this.output.println(chatPartner.getName() + " has pinged you ! Do  u wish to chat with him (Y/N)?");
            String response = this.input.readLine();
            if (response.equalsIgnoreCase("y")) {
                MyChatServerImpl.Users.add(chatPartner.getName());
                MyChatServerImpl.clientList.add(chatPartner);
                this.setChatPartnerName(chatPartner);
                this.setChatPartnerStatusMessage(chatPartner.getStatusMessage());
                return true;
            } else {
                // Does not wish to chat.
                chatPartner.sendMessage(this, this.getName() + "has chosen not to engage with you currently in conversation.");
                return false;
            }
        } else {
            // Upper threshold reached for number of chat clients.
            return false;
        }
    }

    @Override
    public void startConversation(HandleClientImpl client) {
        for (HandleClientImpl chatPartner : MyChatServerImpl.clientList) {
            if (chatPartner.name.equalsIgnoreCase(client.name)) {
                chatPartner.receiveMessage(client, name);
            }
        }
    }

    @Override
    public void exitConversation() {
        
    }

    @Override
    public void run() {
        String line;
        while (true) {
            try {
                line = input.readLine();
                if (line.equalsIgnoreCase("EXIT")) {
                    // close connection
                    output.println("Closing connection with " + this.getChatPartnerName());
                    this.getChatPartnerName().output.println(this.getName()+ " has chosen to close this chat. Good Bye !");
                    MyChatServerImpl.Users.remove(this.chatPartner.getName());
                    MyChatServerImpl.clientList.remove(this.chatPartner);
                    break;
                } else if (name.equalsIgnoreCase(line)) {
                    // Test connection by ping oneself
                    output.println("OK");
                } else if(name.contains("Ping")) {
                    String chatPartner = name.substring(name.indexOf("Ping") + "Ping ".length()));
                    MyChatServerImpl.getInstance().
                    
                }else {
                    // send message to chat partner
                    this.getChatPartnerName().sendMessage(this, name);
                }
            } catch (IOException ex) {
                Logger.getLogger(HandleClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method passes message to
     * <code>receiveMessage</code> of chat partner.
     *
     * @param message
     */
    @Override
    public void sendMessage(HandleClientImpl msrOriginator, String message) {
        this.getChatPartnerName().receiveMessage(this, message);
    }

    /**
     * Displays <tt>message</tt> received to current user.
     *
     * @param msrOriginator
     * @param message
     */
    @Override
    public void receiveMessage(HandleClientImpl msrOriginator, String message) {
        this.output.println(message);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HandleClientImpl other = (HandleClientImpl) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
