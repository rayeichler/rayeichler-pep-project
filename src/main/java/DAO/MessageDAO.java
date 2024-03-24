package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.util.*;
import java.sql.*;

public class MessageDAO {
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return messages;
    }

    public Message getMessageById(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            System.out.println("Message by ID");
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, messageID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println("Message Found");
                return new Message(resultSet.getInt("message_id"), 
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        Message message = getMessageById(messageID);

        if(message != null){
            try{
                String sql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, messageID);
                preparedStatement.executeUpdate();

                return message;
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Message addMessage(Message message){
        String messageText = message.getMessage_text();
        int posted_by = message.getPosted_by();
        long time_posted_epoch = message.getTime_posted_epoch();

        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql1 = "SELECT account_id FROM Account WHERE account_id = ?;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, posted_by);

            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if(resultSet1.next() && messageText.length() > 0 && messageText.length() < 256){
                try{
                    String sql2 = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);

                    preparedStatement2.setInt(1, posted_by);
                    preparedStatement2.setString(2, messageText);
                    preparedStatement2.setLong(3, time_posted_epoch);

                    preparedStatement2.executeUpdate();
                    ResultSet pkResultSet = preparedStatement2.getGeneratedKeys();

                    if(pkResultSet.next()){
                        int generated_message_id = (int) pkResultSet.getLong(1);
                        return new Message(generated_message_id, posted_by, messageText, time_posted_epoch);
                    }

                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
