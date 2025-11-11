package se.jensen.anton.chatapp.dao;

import se.jensen.anton.chatapp.model.User;

public interface UserDAO {
    User login(String username, String password);

    User register(User user);
}
