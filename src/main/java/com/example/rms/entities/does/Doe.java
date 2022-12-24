package com.example.rms.entities.does;

import com.example.rms.entities.AbstractEntity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Doe extends AbstractEntity {

    private boolean mated;
    private boolean pregnant;
    private LocalDate localDate;

    public boolean isMated() {
        return mated;
    }

    public void setMated(boolean mated) {
        this.mated = mated;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
