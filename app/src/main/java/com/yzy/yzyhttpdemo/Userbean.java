package com.yzy.yzyhttpdemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzy on 17-12-18.
 */

public class Userbean {

    /**
     * name : yzy
     * age : 26
     * job : Android
     */

    private String name;
    private int age;
    private String job;

    public static Userbean objectFromData(String str) {

        return new Gson().fromJson(str, Userbean.class);
    }

    public static List<Userbean> arrayUserbeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<Userbean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Userbean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
    }
}
