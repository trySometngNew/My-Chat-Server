/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyChatServer;

/**
 * Interface for handling clients
 *
 * @ server side. Includes methods pertaining to manipulating and interacting with each client.
 * @author sourabh_lonikar
 */
public interface iHandleClient extends Runnable{

    public abstract void sendMessage(HandleClientImpl msrOriginator, String message);

    public abstract void receiveMessage(HandleClientImpl msrOriginator, String message);
    
    public abstract void startConversation(HandleClientImpl client);
    
    public abstract void exitConversation();
}
