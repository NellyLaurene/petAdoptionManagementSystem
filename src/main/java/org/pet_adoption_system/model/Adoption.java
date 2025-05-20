package org.pet_adoption_system.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Adoption {
    private int adoption_id;
    private int pet_id;
    private int adopter_id;
    private LocalDate adoption_date;
    private BigDecimal adoption_fee;
    private String status;
    private int staff_id;

    public Adoption() {}

    public Adoption(int adoption_id, int pet_id, int adopter_id, LocalDate adoption_date, BigDecimal adoption_fee, String status, int staff_id) {
        this.adoption_id = adoption_id;
        this.pet_id = pet_id;
        this.adopter_id = adopter_id;
        this.adoption_date = adoption_date;
        this.adoption_fee = adoption_fee;
        this.status = status;
        this.staff_id = staff_id;
    }

    public int getAdoption_id() {
        return adoption_id;
    }

    public void setAdoption_id(int adoption_id) {
        this.adoption_id = adoption_id;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public int getAdopter_id() {
        return adopter_id;
    }

    public void setAdopter_id(int adopter_id) {
        this.adopter_id = adopter_id;
    }

    public LocalDate getAdoption_date() {
        return adoption_date;
    }

    public void setAdoption_date(LocalDate adoption_date) {
        this.adoption_date = adoption_date;
    }

    public BigDecimal getAdoption_fee() {
        return adoption_fee;
    }

    public void setAdoption_fee(BigDecimal adoption_fee) {
        this.adoption_fee = adoption_fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }
}
