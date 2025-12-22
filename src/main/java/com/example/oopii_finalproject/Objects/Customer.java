package com.example.oopii_finalproject.Objects;

public class Customer extends User {
    private boolean isAuthenticated = true;

    public Customer(int customerId, String username, String email, String password) {
        super(customerId, username, email, password, "CUSTOMER", 0);
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.isAuthenticated = authenticated;
    }
}
