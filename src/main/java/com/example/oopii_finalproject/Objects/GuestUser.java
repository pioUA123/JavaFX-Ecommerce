package com.example.oopii_finalproject.Objects;

public class GuestUser extends User {

    public GuestUser() {
        super(-1, "Guest", "guest", "guest", "GUEST", 0);
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }
}
