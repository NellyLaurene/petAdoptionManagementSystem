package org.pet_adoption_system.model;

public class Pet {
    private int pet_id;
    private String species;
    private int age;
    private String gender;
    private String size;
    private String status;

    public Pet() {}

    public Pet(int pet_id, String species, int age, String gender, String size, String status) {
        this.pet_id = pet_id;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.status = status;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
