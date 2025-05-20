package org.pet_adoption_system.model;

// Models only have plain java classes(POJOs) that matches the database tables
public class Adopter {
    private int adopter_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String location;

    public Adopter(){}

    public Adopter(int adopter_id, String first_name, String last_name, String email, String phone_number, String location) {
        this.adopter_id = adopter_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.location = location;
    }

    public int getAdopter_id() {
        return adopter_id;
    }

    public void setAdopter_id(int adopter_id) {
        this.adopter_id = adopter_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }
}
