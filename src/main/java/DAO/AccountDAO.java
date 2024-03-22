package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    public Account addAccount(Account account){
        List<String> existingUsernames = new ArrayList<String>();
        existingUsernames = getAllUsernames();

        if((account.getUsername() != null) && !(existingUsernames.contains(account.getUsername())) && (account.getPassword().length()>3)){
            insertAccount(account);
            return account;
        }else{
            return null;
        }
    }

    private void insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO Account(username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(0, account.getUsername());
            preparedStatement.setString(1, account.getPassword());

            preparedStatement.executeUpdate(sql);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private List<String> getAllUsernames(){
        Connection connection = ConnectionUtil.getConnection();
        List<String> usernames = new ArrayList<String>();

        try{
            String sql = "SELECT username FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                usernames.add(rs.getString("username"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return usernames;
    }
}
