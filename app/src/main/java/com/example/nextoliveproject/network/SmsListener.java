package com.example.nextoliveproject.network;

public interface SmsListener {
    public void messageReceived(String Sender, String messageText);
}
