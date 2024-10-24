package org.example.personas;

import java.io.Serializable;

public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name = "";
    private int age = 0;


    public Persona(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public Persona() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }



}
