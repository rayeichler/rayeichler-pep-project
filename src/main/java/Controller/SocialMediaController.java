package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.AccountService;

import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
Contains the handlers and endpoints for the Social Media API.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        messageService = new MessageService();
        accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postNewUserHandler);
        app.post("/login", this::postLoginHandler);

        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);

        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::patchMessageHandler);

        return app;
    }
    /*
    postNewUserHandler will read-in the data required for a new account, create the account if params are met, 
    and return error code 400 if unsuccessful for any reason.
    */
    private void postNewUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addUser(account);
        if(addedAccount != null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }
    /*
    postLoginHandler will accept the data required to authenticate and authorize an account login and will return 
    the account object if successful or a 401 unauthorized if not. 
    */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account authorizedAccount = accountService.verifyAccount(account);
        if(authorizedAccount != null){
            ctx.json(mapper.writeValueAsString(authorizedAccount));
        }
        else{
            ctx.status(401);
        }
    }
    /*
    getMessagesByUserHandler accepts a parameter from the url (account_id) and queries the Message database for all 
    posted_by fields that match returning a list (empty if no messages found).
    */
    private void getMessagesByUserHandler(Context ctx){
        int accountID = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));
        List<Message> messages = messageService.getAllMessagesByUser(accountID);

        ctx.json(messages);
    }
    /*
    getAllMessagesHandler queries the Message database for all messages that are persisted in the database (empty if none found).
    */
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages, Message.class);
    }
    /*
    getMessageByIdHandler querries the Message database for a specific message, taking a message_id parameter from the url
    exporting a json object with the message (empty if none) 
    */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int messageID = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message message = messageService.getMessageById(messageID);
        if(message != null){
            ctx.json(message, Message.class);
        }
    }
    /*
    postMessageHandler accepts a message object in the form of a POST request body and will create a new message if the
    specifications are met, returning the new message if successful or a 400 error code if not.
    */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message postedMessage = messageService.addMessage(message);
        if(postedMessage != null){
            ctx.json(mapper.writeValueAsString(postedMessage));
        }else{
            ctx.status(400);
        }
    }
    /* 
    patchMessageHandler updates the text of a message based on a PATCH request with the new message text in the body. 
    If the new message is between 1 and 255 characters (inclusive) an update will be made to the database. The new message
    will then be in the response if successful, or a error code 400 if not.
    */
    private void patchMessageHandler(Context ctx) throws JsonProcessingException{
        int messageID = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(messageID, newMessage.getMessage_text());

        if(updatedMessage != null){
            ctx.json(updatedMessage, Message.class);
        }else{
            ctx.status(400);
        }
    }
    /*
    deleteMessageByIdHandler will execute a delete statement on the database, returning the deleted message.
    */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int messageID = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message message = messageService.deleteMessageById(messageID);
        if(message != null){
            ctx.json(message, Message.class);
        }
    }
}