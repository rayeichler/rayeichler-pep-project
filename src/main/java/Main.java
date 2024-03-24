import Controller.SocialMediaController;
import io.javalin.Javalin;

import Model.Account;
import DAO.AccountDAO;
import DAO.MessageDAO;
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

        MessageDAO messageDAO = new MessageDAO();
        System.out.println(messageDAO.getMessageById(323943));
    }
}
