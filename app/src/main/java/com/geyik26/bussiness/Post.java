package com.geyik26.bussiness;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id_number")
    private long id;
    private String password;
    private String gender;
    private String surname;
    private String password_confirmation;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private String address;
    String created_at;

    public Post(long id, String password, String gender, String surname, String password_confirmation, String name, String email, String address) {
        this.id = id;
        this.password = password;
        this.gender = gender;
        this.surname = surname;
        this.password_confirmation = password_confirmation;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
