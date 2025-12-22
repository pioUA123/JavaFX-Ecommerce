package com.example.oopii_finalproject.Objects;

public class Administrator extends User {

    private Administrator(String username, String email, String password) {
        super(1, username, email, password, "ADMINISTRATOR", 0);
    }

}
