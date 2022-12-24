package com.example.rms.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    private String cageRef;
    private Date dateOfB;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCageRef() {
        return cageRef;
    }

    public void setCageRef(String cageRef) {
        this.cageRef = cageRef;
    }

    public Date getDateOfB() {
        return dateOfB;
    }

    public void setDateOfB(Date dateOfB) {
        this.dateOfB = dateOfB;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    private Long weight;
    private String breed;

}
