package com.uniovi.notaneitor.entities;

//import jakarta.persistence.*; //cambio de version
import javax.persistence.*;
import java.util.Objects;

@Entity
public class Mark {
    @Id
    @GeneratedValue

    private Long id;
    private String description;
    private Double score;

    //declaro su relacion (Muchos a Uno) con la entidad User
    @ManyToOne
    @JoinColumn(name = "user_id") //JoinColumn (COMPLEMENTARIO a mappedBy) para indicar la columna de la tabla que hace referencia a la otra tabla
    private User user;

    public Mark() {
        this.id = null;
        this.description = null;
        this.score = null;
    }

    public Mark(Long id, String description, Double score) {
        this.id = id;
        this.description = description;
        this.score = score;
    }

    public Mark(String description, Double score, User user) {
        super();
        this.description = description;
        this.score = score;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Mark {id=" + this.id + ", description=" + this.description + ", score=" + this.score + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return Objects.equals(id, mark.id);
    }

}