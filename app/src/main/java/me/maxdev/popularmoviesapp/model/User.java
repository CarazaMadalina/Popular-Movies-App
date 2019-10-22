package me.maxdev.popularmoviesapp.model;

public class User {
    public String id;
    public String userName;
    public String email;
    public String password;
    public String phone;
    public  String confirm_password;

    public User(String id, String userName, String email, String password, String phone, String confirm_password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone= phone;
        this.confirm_password = confirm_password;
    }
}
