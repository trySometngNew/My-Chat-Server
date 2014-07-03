/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyChatServer;

/**
 * Chat Client Interface
 * @author sourabh_lonikar
 */
public interface iClient {
    public void getChatPartner();
    public void sendMessage();
    public void receiveMessage();
    public void setStatusMessage();
    public void exit();
}
