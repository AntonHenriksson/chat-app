package se.jensen.anton.chatapp.dao;

import se.jensen.anton.chatapp.model.Message;
import se.jensen.anton.chatapp.service.Connector;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDatabaseDAO implements MessageDAO{
    @Override
    public void saveMessage(Message message) {
        String saveSql = "INSERT INTO messages(user_id, message, timestamp) VALUES (?,?,?)";
        try(Connection conn = Connector.getInstance().getConnection();
        PreparedStatement psmt = conn.prepareStatement(saveSql)){
            psmt.setInt(1,message.getUserId());
            psmt.setString(2,message.getText());
            psmt.setTimestamp(3, Timestamp.valueOf(message.getTimestamp()));
            psmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        String getByIdSql = "SELECT * FROM messages WHERE user_id = ?";
        try(Connection conn = Connector.getInstance().getConnection();
        PreparedStatement psmt = conn.prepareStatement(getByIdSql)){
            psmt.setInt(1,userId);
            try(ResultSet rs = psmt.executeQuery()){
            while(rs.next()){
                String message =  rs.getString("message");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();
                messages.add(new Message(userId,message,timestamp));
            }}
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }
}
