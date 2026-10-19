/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author Student
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Message {
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static int totalSent = 0;

    private final String messageId;
    private final int messageNumber;   // 0-based, matches the 00:0:HITHANKS example
    private final String recipient;
    private final String text;
    private final String messageHash;

    public Message(int messageNumber, String recipient, String text) {
        this(generateId(), messageNumber, recipient, text);
    }

    // Second constructor lets unit tests supply a fixed ID
    public Message(String messageId, int messageNumber, String recipient, String text) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }

    private static String generateId() {
        long id = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    public String checkMessageLength() {
        int over = text.length() - 250;
        if (over <= 0) return "Message ready to send.";
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    // "+" then a 1-3 digit country code, then up to 10 digits
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{2,13}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash() {
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    // 1 = send, 2 = disregard, 3 = store
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) sb.append(m.details()).append("\n");
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    public String details() {
        return "Message ID: " + messageId + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient + "\nMessage: " + text + "\n";
    }

    // Writes every stored message to messages.json (rewrites the whole array each time)
    public void storeMessage() {
        storedMessages.add(this);
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json.append("  {\"messageID\": \"").append(esc(m.messageId))
                .append("\", \"messageHash\": \"").append(esc(m.messageHash))
                .append("\", \"recipient\": \"").append(esc(m.recipient))
                .append("\", \"message\": \"").append(esc(m.text)).append("\"}");
            json.append(i < storedMessages.size() - 1 ? ",\n" : "\n");
        }
        json.append("]");
        try {
            Files.write(Paths.get("messages.json"), json.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Could not write messages.json: " + e.getMessage());
        }
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    public String getMessageId() { return messageId; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }

    public static void resetState() {
        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }
}
    
