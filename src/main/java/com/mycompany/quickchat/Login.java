/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author Student
 */
public class Login {
    private String username;
    private String password;
    private String cellphone;
    private String firstName;
    private String lastName;
    
    public Login(String username, String password, String cellphone, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public boolean checkUserName(){
        return username.contains("_") && username.length() <= 5;
    }
    public boolean checkPasswordComplexity(){
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }
            
            if (Character.isDigit(character)) {
                hasNumber = true;
            }
            
            if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }
        
        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }
    
    public boolean checkCellPhoneNumber() {
        return cellphone.matches("^\\+27[0-9]{9}$");
    }
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more that five characters in length.";
        }
        
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number and a special character.";
        }
        
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cellphone number successfully added.\n";
    }
    
    public boolean loginUser(String username, String password){
        return this.username.equals(username) && this.password.equals(password);
    }
    public String returnLoginStatus() {
        if (loginUser(username, password)){
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        }else{
            return "Username or password incorrect, please try again.";
        }
    }
}
