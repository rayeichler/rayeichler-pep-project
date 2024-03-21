package Service;

import Model.Account;
import Util.ConnectionUtil;
import DAO.AccountDAO;

import java.util.*;
import java.sql.*;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account userRegistration(Account account){
        if((account.getUsername() != null) && (accountDAO.existingUsernameCheck(account) == null) && (account.getPassword().length() > 3)){
            return accountDAO.insertUser(account);
        }
        return null;
    }
}
