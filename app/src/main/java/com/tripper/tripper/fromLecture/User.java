package com.tripper.tripper.fromLecture;

import com.google.gson.Gson;

public class User {

    int ID;
    String name;
    int age;

    public User(int ID, String name, int age) {
        this.ID = ID;
        this.name = name;
        this.age = age;
    }

    public static User fromJson(String json){

        return (new Gson()).fromJson(json, User.class);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toJson(){
        return (new Gson()).toJson(this);
    }
}
