package com.example.oopii_finalproject.Objects;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class User {
    private final int userId;
    private String username;
    private String email;
    private String password;
    private final String userRole;
    private double balance;
    private boolean isActive;

    private Promotion promotion = null;
    private ArrayList<String> usedPromos = new ArrayList<>();
    private String profilePicture;

    public User(int userId, String username, String email, String password, String userRole, double balance, boolean isActive, String profile) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.balance = balance;
        this.isActive = isActive;
        this.profilePicture = profile;
    }

    public User(int userId, String username, String email, String password, String userRole, double balance) {
        this(userId, username, email, password, userRole, balance, true, "Profile.jpg");
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isAuthenticated() {
        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserRole() {
        return userRole;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public ArrayList<String> getUsedPromos() {
        return usedPromos;
    }

    public void addUsedPromo(String code) {
        usedPromos.add(code);
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
