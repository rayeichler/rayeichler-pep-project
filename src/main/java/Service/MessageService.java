package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.*;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
      return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageID){
        return messageDAO.getMessageById(messageID);
    }

    public Message updateMessage(int messageID, String messageText){
        return messageDAO.updateMessage(messageID, messageText);
    }

    public Message deleteMessageById(int messageID){
        return messageDAO.deleteMessageById(messageID);
    }

    public Message addMessage(Message message){
        return messageDAO.addMessage(message);
    }
}
