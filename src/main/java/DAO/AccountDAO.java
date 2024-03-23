package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    public Account addAccount(Account account){
        //List<String> existingUsernames = new ArrayList<String>();
        //existingUsernames = getAllUsernames();
        String newusername = account.getUsername();
        String password = account.getPassword();

        System.out.println("Accountdao addaccount");
        if(newusername.length()>0 && password.length()>3 && checkAccountAvailability(newusername)){// && !existingUsernames.contains(newusername) && password.length()>3){
            System.out.println("Adding Account");
            insertAccount(account);
            return account;
        }else{
            return null;
        }


//        if(newusername != null) !existingUsernames.contains(newusername) && password.length()>3){
  //          insertAccount(account);
    //        return account;
      //  }else{
        //    return null;
       // }
    }

    public boolean checkAccountAvailability(String username){
        List<String> existingUsernames = getAllUsernames();
        boolean doesExist = true;

        for(String user: existingUsernames){
            if(username.equalsIgnoreCase(user)){
                doesExist = false;
            }
        }
        return doesExist;
    }

    public void insertAccount(Account account){
        System.out.println("insertAccount");
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO Account(username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<String> getAllUsernames(){
        Connection connection = ConnectionUtil.getConnection();
        List<String> usernames = new ArrayList<String>();

        try{
            String sql = "SELECT * FROM account";
            //PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                usernames.add(rs.getString("username"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return usernames;
    }
}
