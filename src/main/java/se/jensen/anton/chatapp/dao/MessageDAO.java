package se.jensen.anton.chatapp.dao;

import se.jensen.anton.chatapp.model.Message;

import java.util.List;

public interface MessageDAO {
    void saveMessage(Message message);

    List<Message> getMessagesByUserId(int userId);
}
