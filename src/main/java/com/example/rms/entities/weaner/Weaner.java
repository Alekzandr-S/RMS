package com.example.rms.entities.weaner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Weaner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String cageRef;
    private LocalDate dOB;
    private String sire;
    private String parentDoe;


    public String getCageRef() {
        return cageRef;
    }

    public void setCageRef(String cageRef) {
        this.cageRef = cageRef;
    }

    public LocalDate getdOB() {
        return dOB;
    }

    public void setdOB(LocalDate dOB) {
        this.dOB = dOB;
    }

    public String getSire() {
        return sire;
    }

    public void setSire(String sire) {
        this.sire = sire;
    }

    public String getParentDoe() {
        return parentDoe;
    }

    public void setParentDoe(String parentDoe) {
        this.parentDoe = parentDoe;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    private int initialCount;
    private int currentCount;
}
