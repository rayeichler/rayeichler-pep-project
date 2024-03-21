package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        //My Enpoints
        app.get("/accounts", this::getAllAccountsHandler);
        app.post("/accounts", this::postAccountHandler);

        app.get("/messages", this::getAllMessagesHandler);

        return app;
    }

    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getAllAccountsHandler(Context ctx){
        List<Account> accounts = accountService.getAllAccounts();
        ctx.json(accounts);
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.userRegistration(account);
        if(newAccount != null){
            ctx.json(mapper.writeValueAsString(newAccount));
        }else{
            ctx.status(400);
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}