package com.example.dungan_lakaw;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String age;
    private String address;

    public User() {
        // Default constructor required for Firebase Realtime Database
    }

    public User(String fullName, String email, String password, String age, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
