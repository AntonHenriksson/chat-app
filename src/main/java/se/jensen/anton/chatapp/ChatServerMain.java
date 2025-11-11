package se.jensen.anton.chatapp;

public class ChatServerMain {
    public static void main(String[] args) {
        new ChatServer(5555).start();
    }
}
