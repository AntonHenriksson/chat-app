package se.jensen.anton.chatapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.jensen.anton.chatapp.dao.MessageDAO;
import se.jensen.anton.chatapp.dao.MessageDatabaseDAO;
import se.jensen.anton.chatapp.dao.UserDAO;
import se.jensen.anton.chatapp.dao.UserDatabaseDAO;
import se.jensen.anton.chatapp.model.Message;
import se.jensen.anton.chatapp.model.User;
import se.jensen.anton.chatapp.service.Connector;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDAODatabase {

    @BeforeEach
    public void startDatabase() throws SQLException {
        String createUsersSql = "CREATE TABLE users (user_id INT AUTO_INCREMENT PRIMARY KEY," +
                " user_name VARCHAR(20) NOT NULL, password VARCHAR(20) NOT NULL)";
        String createMessagesSql = "CREATE TABLE messages (user_id INT," +
                " message VARCHAR(300) NOT NULL, timestamp DATETIME NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users(user_id))";
        try (Connection conn = Connector.getInstance().getConnection();
             PreparedStatement psmt = conn.prepareStatement(createUsersSql);
             PreparedStatement psmt2 = conn.prepareStatement(createMessagesSql)) {
            psmt.executeUpdate();
            psmt2.executeUpdate();
        }
    }

    @Test
    public void runTest() {
        MessageDAO messageDAO = new MessageDatabaseDAO();
        UserDAO UserDAO = new UserDatabaseDAO();
        User user = UserDAO.register(new User("testname", "password"));
        Message message1 = new Message(user.getId(), "Message one", LocalDateTime.now());
        Message message2 = new Message(user.getId(), "Message two", LocalDateTime.now());
        messageDAO.saveMessage(message1);
        messageDAO.saveMessage(message2);
        assertEquals(2, messageDAO.getMessagesByUserId(user.getId()).size());
    }

    @AfterEach
    public void cleanDatabase() throws SQLException {
        String cleanMessages = "DROP TABLE IF EXISTS messages";
        String cleanUsers = "DROP TABLE IF EXISTS users";
        try (Connection conn = Connector.getInstance().getConnection();
             PreparedStatement psmt = conn.prepareStatement(cleanMessages);
             PreparedStatement psmt2 = conn.prepareStatement(cleanUsers)) {
            psmt.executeUpdate();
            psmt2.executeUpdate();
        }
    }
}