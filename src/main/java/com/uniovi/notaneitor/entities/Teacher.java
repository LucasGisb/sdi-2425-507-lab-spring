package com.uniovi.notaneitor.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Teacher {

    private Long id;

    private String dni;

    private String name;
    private String surname;
    private String category;

    public Teacher() {
        this.id = null;
        this.dni = null;
        this.name = null;
        this.surname = null;
        this.category = null;
    }

    public Teacher(Long id, String dni, String name, String surname, String category) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Teacher {id= " + this.id + ", dni=" + this.dni + ", name=" + this.name + ", surname=" + this.surname + ", category=" + this.category +  "}";
    }


}