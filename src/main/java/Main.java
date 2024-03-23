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

        AccountDAO accountDAO = new AccountDAO();
        Account newAccount = new Account("testuser1", "feiodw");
        accountDAO.addAccount(newAccount);
        System.out.println("Should fail");
        Account newAccount2 = new Account("Efewjofif", "ijfowiejf");
        accountDAO.addAccount(newAccount2);
        System.out.println("Should work.");
    }
}
