package com.turtleMQ.nodeconnector.handler;

public interface MessageReceiver {
    public void handle(String payload);
}
