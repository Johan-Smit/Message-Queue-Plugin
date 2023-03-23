package com.turtleMQ;

public interface MessageReceiver {
    public void handle(String payload);
}
