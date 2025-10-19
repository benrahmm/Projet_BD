package com.belec.app.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User {
    private String mail;
    private String firstName;
    private String lastName;
    private String address;

    public User(String mail, String firstName, String lastName, String address) {
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public static HashMap<String, User> retrieveFromDatabase(ResultSet resultSet) {
        HashMap<String, User> users = new HashMap<>();
        try {
            while (!resultSet.isAfterLast()){
                resultSet.next();
                String mail = resultSet.getString("mail");
                String firstName = resultSet.getString("nom");
                String lastName = resultSet.getString("prenom");
                String address = resultSet.getString("adresse");
                users.put(mail, new User(mail, firstName, lastName, address));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}
