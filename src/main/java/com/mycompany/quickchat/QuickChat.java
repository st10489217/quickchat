/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

/**
 *
 * @author Student
 */
import java.util.Scanner;

public class QuickChat {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

 // Registration
Login login;
while (true) {
    System.out.print("Enter first name: ");
    String firstName = in.nextLine().trim();
    System.out.print("Enter last name: ");
    String lastName = in.nextLine().trim();
    System.out.print("Enter username: ");
    String username = in.nextLine().trim();
    System.out.print("Enter password: ");
    String password = in.nextLine().trim();
    System.out.print("Enter cell number (e.g. +27831234567): ");
    String cellphone = in.nextLine().trim();

    login = new Login(username, password, cellphone, firstName, lastName);
    String result = login.registerUser();
    System.out.println(result);
    if (result.startsWith("Username successfully captured")) {
        break;
    }
}

// Login (3 attempts)
boolean loggedIn = false;
int attempts = 0;
while (!loggedIn && attempts < 3) {
    System.out.print("Log in - username: ");
    String u = in.nextLine().trim();
    System.out.print("Log in - password: ");
    String p = in.nextLine().trim();
    loggedIn = login.loginUser(u, p);
    if (!loggedIn) {
        System.out.println("Username or password incorrect, please try again.");
        attempts++;
    }
}

if (!loggedIn) {
    System.out.println("Too many failed attempts. You must log in successfully before sending messages.");
    return;
}
System.out.println(login.returnLoginStatus());

        System.out.println("Welcome to QuickChat.");
        System.out.print("How many messages do you wish to enter? ");
        int limit = Integer.parseInt(in.nextLine().trim());
        int entered = 0;

        while (true) {
            System.out.println("\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
            String option = in.nextLine().trim();

            if (option.equals("1")) {
                if (entered >= limit) {
                    System.out.println("You have reached your limit of " + limit + " messages.");
                    continue;
                }

                String recipient;
                while (true) {
                    System.out.print("Recipient cell number (e.g. +27718693002): ");
                    recipient = in.nextLine().trim();
                    String r = new Message(entered, recipient, "x").checkRecipientCell();
                    System.out.println(r);
                    if (r.startsWith("Cell phone number successfully")) break;
                }

                String text;
                while (true) {
                    System.out.print("Message: ");
                    text = in.nextLine();
                    if (text.trim().isEmpty()) continue;
                    if (text.length() <= 250) break;
                    System.out.println("Please enter a message of less than 250 characters.");
                    System.out.println(new Message(entered, recipient, text).checkMessageLength());
                }
                System.out.println("Message sent");

                Message msg = new Message(entered, recipient, text);
                System.out.println("Message ID generated: " + msg.getMessageId());
                System.out.println("1) Send Message  2) Disregard Message  3) Store Message to send later");
                int choice = Integer.parseInt(in.nextLine().trim());
                System.out.println(msg.sentMessage(choice));
                if (choice == 2) in.nextLine(); // user presses 0 to delete
                if (choice == 1) System.out.println(msg.details());
                entered++;

            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (option.equals("3")) {
                System.out.println("Total messages sent: " + Message.returnTotalMessages());
                break;
            } else {
                System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }
}