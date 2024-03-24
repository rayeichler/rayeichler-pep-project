package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.*;

/*
MessageService is the intermediate layer (business logic) between the Javalin controller and the data access object.
There is currently no business logic for this API, hence it is simply a passthrough layer to the DAO.
*/
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

    public List<Message> getAllMessagesByUser(int accountID){
        return messageDAO.getAllMessagesByUser(accountID);
    }

    public Message getMessageById(int messageID){
        return messageDAO.getMessageById(messageID);
    }

    public Message addMessage(Message message){
        return messageDAO.addMessage(message);
    }

    public Message updateMessage(int messageID, String messageText){
        return messageDAO.updateMessage(messageID, messageText);
    }

    public Message deleteMessageById(int messageID){
        return messageDAO.deleteMessageById(messageID);
    }
}
