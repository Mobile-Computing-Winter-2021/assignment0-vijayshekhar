package com.example.helloworld.Models;

public class Students {

    String branch1;
    String name1;
    String rollno1;

    public Students(String name, String branch, String rollno) {
        this.branch1 = branch;
        this.name1 = name;
        this.rollno1 = rollno;
    }

    public String getBranch1() {
        return branch1;
    }

    public void setBranch1(String branch1) {
        this.branch1 = branch1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getRollno1() {
        return rollno1;
    }

    public void setRollno1(String rollno1) {
        this.rollno1 = rollno1;
    }
}
