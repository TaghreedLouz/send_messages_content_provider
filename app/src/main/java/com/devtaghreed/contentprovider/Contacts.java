package com.devtaghreed.contentprovider;

public class Contacts {
    String name;
    String number;

    public Contacts(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String setNumber(String number) {
        this.number = number;
        return number;
    }
}
