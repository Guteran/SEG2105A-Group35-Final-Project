package com.example.finalproject.users;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Switch;

import java.util.Locale;

public class User implements Parcelable {
    private String _id, _username, _email, _firstName, _lastName;

    UserType _userType;

    public User(){
        this._username = "Test";
    };


    public User(String id, String userType, String username, String email, String firstName, String lastName) {
        this._id = id;
        this._username = username;
        this._email = email;
        this._firstName = firstName;
        this._lastName = lastName;
        switch (userType.toLowerCase()){
            case "admin":
                this._userType = UserType.ADMIN;
                break;
            case "instructor":
                this._userType = UserType.INSTRUCTOR;
                break;
            case "member":
            default:
                this._userType = UserType.MEMBER;
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

    public UserType getUserType(){
        return this._userType;
    }

    public String getLastName() {
        return this._lastName;
    }

    public void setUsername(String _username) {
        this._username = _username;
    }

    public void setFirstName(String _firstName) {
        this._firstName = _firstName;
    }

    public void setLastName(String _lastName) {
        this._lastName = _lastName;
    }

    public User(Parcel in){
        String[] data = new String[]{this._id, this._username, this._email, this._firstName, this._lastName, this._userType.toString().toLowerCase()};
        in.readStringArray(data);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{this._id, this._username, this._email, this._firstName, this._lastName, this._userType.toString().toLowerCase()});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public User createFromParcel(Parcel in){
            return new User(in);
        }

        public User[] newArray(int size){
            return new User[size];
        }
    };
}

