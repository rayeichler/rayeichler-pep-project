package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

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
