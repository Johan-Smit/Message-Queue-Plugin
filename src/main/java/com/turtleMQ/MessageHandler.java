package com.turtleMQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class MessageHandler extends Thread {
    
    private ServerSocket server;
    private Socket broker;

    private int port;

    private MessageReceiver messageReceiver;

    private Queue<String> messageQueue;

    public MessageHandler(int port) {
        this.port = port;
        messageQueue = new LinkedList<>();
    }

    private final void initialize(int port) {

        this.port = port;

        try {
            server = new ServerSocket(port);

            System.out.println("Listening for messages on port: " + port);

            broker = server.accept();

        } catch (IOException e) {
            System.out.println("Error initializing: " + e);
        }
    }

    public void run() {

        initialize(port);
        
        while (true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(broker.getInputStream()));
                String message = in.readLine();

                if (messageReceiver != null)
                    messageReceiver.handle(nextMessage());
                
                broker = server.accept();

            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }

    public void register(MessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public String nextMessage() {
        if (messageQueue.isEmpty() == true)
            return null;
        return messageQueue.peek();
    }
}
