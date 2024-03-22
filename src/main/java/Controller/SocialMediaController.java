package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

//My Code
import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.AccountService;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//End My Code
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    //My Code
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        messageService = new MessageService();
        accountService = new AccountService();
    }

    //End My Code
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //Start My Code
        //My Endpoints
        app.get("/", ctx -> ctx.result("Welcome to Ray's Social Media App"));
        app.post("/register", this::postNewUserHandler);
        app.get("/messages", this::getAllMessagesHandler);

        //End My Code
        return app;
    }

    private void postNewUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addUser(account);
    }

    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages, Message.class);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}