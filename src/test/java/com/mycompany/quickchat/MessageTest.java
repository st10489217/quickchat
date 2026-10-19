/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author Student
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    private static final String ID = "0012345678";
    private static final String TEXT1 = "Hi Mike, can you join us for dinner tonight?";

    @BeforeEach
    void reset() { Message.resetState(); }

    @Test
    void messageLengthSuccess() {
        Message m = new Message(ID, 0, "+27718693002", TEXT1);
        assertEquals("Message ready to send.", m.checkMessageLength());
    }

    @Test
    void messageLengthFailure() {
        Message m = new Message(ID, 0, "+27718693002", "a".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", m.checkMessageLength());
    }

    @Test
    void recipientSuccess() {
        Message m = new Message(ID, 0, "+27718693002", TEXT1);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    void recipientFailure() {
        Message m = new Message(ID, 1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", m.checkRecipientCell());
    }

    @Test
    void messageHashTestCase1() {
        Message m = new Message(ID, 0, "+27718693002", TEXT1);
        assertEquals("00:0:HITONIGHT", m.createMessageHash());
    }

    @Test
    void messageHashLoop() {
        String[] texts = { TEXT1, "Hi Keegan, did you receive the payment?" };
        String[] expected = { "00:0:HITONIGHT", "00:1:HIPAYMENT" };
        for (int i = 0; i < texts.length; i++) {
            Message m = new Message(ID, i, "+27718693002", texts[i]);
            assertEquals(expected[i], m.createMessageHash());
        }
    }

    @Test
    void messageIdCreated() {
        Message m = new Message(0, "+27718693002", TEXT1);
        assertTrue(m.checkMessageID());
        assertEquals("Message ID generated: " + m.getMessageId(), "Message ID generated: " + m.getMessageId());
        assertEquals(10, m.getMessageId().length());
    }

    @Test
    void messageSentOptions() {
        Message m = new Message(ID, 0, "+27718693002", TEXT1);
        assertEquals("Message successfully sent.", m.sentMessage(1));
        assertEquals("Press 0 to delete the message.", m.sentMessage(2));
        assertEquals("Message successfully stored.", m.sentMessage(3));
    }

    @Test
    void totalMessagesCounts() {
        new Message(ID, 0, "+27718693002", TEXT1).sentMessage(1);
        new Message(ID, 1, "+27718693002", "Hi Keegan, did you receive the payment?").sentMessage(2);
        assertEquals(1, Message.returnTotalMessages());
    }
}
