package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class AccountDAO {
    public Account addAccount(Account account){
        String newusername = account.getUsername();
        String password = account.getPassword();

        if(newusername.length()>0 && password.length()>3 && checkAccountAvailability(newusername)){
            Connection connection = ConnectionUtil.getConnection();
            try{
                String sql = "INSERT INTO Account(username, password) VALUES (?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, newusername);
                preparedStatement.setString(2, password);

                preparedStatement.executeUpdate();
                ResultSet pkResultSet = preparedStatement.getGeneratedKeys();
                if(pkResultSet.next()){
                    int generatedID = (int) pkResultSet.getLong(1);
                    return new Account(generatedID, newusername, password);
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Account verifyAccount(Account account){
        List<String> existingUsernames = getAllUsernames();
        String username = account.getUsername();
        if(existingUsernames.contains(username)){
            Connection connection = ConnectionUtil.getConnection();

            try{
                String sql = "SELECT * FROM Account WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    int accountId = resultSet.getInt("account_id");
                    String existingPassword = resultSet.getString("password");
                    if(account.getPassword().equals(existingPassword)){
                        return new Account(accountId, username, existingPassword);
                    }
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private boolean checkAccountAvailability(String username){
        List<String> existingUsernames = getAllUsernames();
        boolean isAvailable = true;

        for(String user: existingUsernames){
            if(username.equalsIgnoreCase(user)){
                isAvailable = false;
            }
        }

        return isAvailable;
    }

    private List<String> getAllUsernames(){
        Connection connection = ConnectionUtil.getConnection();
        List<String> usernames = new ArrayList<String>();

        try{
            String sql = "SELECT * FROM account";
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
