package se.jensen.anton.chatapp.dao;

import se.jensen.anton.chatapp.model.User;
import se.jensen.anton.chatapp.service.Connector;

import java.sql.*;

public class UserDatabaseDAO implements UserDAO {
    @Override
    public User login(String username, String password) {

        int id = 0;
        String loginUser = "SELECT * FROM users WHERE user_name = ? AND password = ?";
        try (Connection conn = Connector.getInstance().getConnection();
             PreparedStatement psmt = conn.prepareStatement(loginUser)) {
            psmt.setString(1, username);
            psmt.setString(2, password);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("user_id");
                    String dbUsername = rs.getString("user_name");
                    String dbPassword = rs.getString("password");
                    return new User(id, dbUsername, dbPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User register(User user) {
        String insertUser = "INSERT INTO users (user_name, password) VALUES (?, ?)";
        try (Connection conn = Connector.getInstance().getConnection();
             PreparedStatement psmt = conn.prepareStatement(insertUser,
                     Statement.RETURN_GENERATED_KEYS)) {
            psmt.setString(1, user.getUsername());
            psmt.setString(2, user.getPassword());
            psmt.executeUpdate();
            try (ResultSet rs = psmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}