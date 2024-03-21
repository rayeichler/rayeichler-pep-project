package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    public Account insertUser(Account account){
        Connection connnection = ConnectionUtil.getConnection();
        
        try{
            String sql = "INSERT INTO account(username, password) VALUES(?,?);";
            PreparedStatement preparedStatement = connnection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
