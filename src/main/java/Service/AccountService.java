package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.*;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addUser(Account account){
        System.out.println("Accountservice adduser");
        return accountDAO.addAccount(account);
    }
}
