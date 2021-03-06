/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyChatServer;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * This server will allow maximum defined simultaneous conversations with only
 * single client involved per conversation. <code> This limitation will be 
 * withdrawn as and when MyChatServer version 1.0 is ready </code>
 * @author sourabh_lonikar
 **/
public interface iMyChatServer {
    /**
     * Limit number of simultaneous chat conversations
     */
    public static final int CLIENT_THREAD_LIMIT = 10;
    public static final int CHAT_SERVER_PORT = 8080;
    public void messageHandler();
    public void broadcastMessage(iHandleClient client, String message);
    public void process();
    public void exit();
       
    /**
     * Start server
     * Start endless loop
     * Check client limit
     * set chat/user limit
     * Incorporate client n/w socket
     * Register User
     * Greet him to indicate he is registered and can use client
     * Show name and status of other users
     * Receive message and second chat user.
     * Send message.
     * 
     * 
     * 
     * 
     */
}
