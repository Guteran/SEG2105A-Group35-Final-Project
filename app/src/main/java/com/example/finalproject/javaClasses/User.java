package com.example.finalproject.javaClasses;

public class User {
    private String _id, _username, _email, _firstName, _lastName, _userType;

    public User(){
        this._username = "Default_User";
    }

    public User(String id, String userType, String username, String email, String firstName, String lastName) {
        this._id = id;
        this._username = username;
        this._email = email;
        this._firstName = firstName;
        this._lastName = lastName;
        switch (userType.toLowerCase()){
            case "admin":
                this._userType = "admin";
                break;
            case "instructor":
                this._userType = "instructor";
                break;
            case "member":
            default:
                this._userType = "member";
        }
    }

    public String getId() {
        return this._id;
    }

    public String getUsername() {
        return this._username;
    }

    public String getEmail() {
        return this._email;
    }

    public String getFirstName() {
        return this._firstName;
    }

    public String getUserType(){
        return this._userType;
    }

    public String getLastName() {
        return this._lastName;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setUsername(String username) {
        this._username = username;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public void setUserType(String userType) {
        this._userType = userType;
    }

}

