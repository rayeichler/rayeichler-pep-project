import Controller.SocialMediaController;
import io.javalin.Javalin;

import Model.Account;
import DAO.AccountDAO;
import Service.AccountService;
import java.util.*;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        Account newuser = new Account("testuser2", "password");
        AccountService accountService = new AccountService();
        Account addedUser = accountService.addUser(newuser);
        System.out.println(addedUser.getUsername());
    }
}
