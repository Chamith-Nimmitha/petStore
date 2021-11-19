package com.example.petstore;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Search")
public class SearchPet {

    private String name;
    private int age;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
