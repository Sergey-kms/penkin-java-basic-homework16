package ru.penkin.java.basic.homework16;

public class ServerApplication {
    public static void main(String[] args) {
        Server server = new Server(8189);
        server.start();
    }
}