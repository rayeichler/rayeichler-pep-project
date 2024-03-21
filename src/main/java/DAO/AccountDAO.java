package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    public Account insertUser(Account account){
        Connection connnection = ConnectionUtil.getConnection();
        
        try{
            String sql = "INSERT INTO account(username, password) VALUES(?,?);";
            PreparedStatement preparedStatement = connnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkresultset = preparedStatement.getGeneratedKeys();

            if(pkresultset.next()){
                int generatedId = pkresultset.getInt(1);
                return new Account(generatedId, account.getUsername(), account.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try{
           String sql = "SELECT * FROM Account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return accounts;
    }

    public Account existingUsernameCheck(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
