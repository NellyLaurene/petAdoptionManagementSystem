package org.pet_adoption_system.dao;

import org.pet_adoption_system.config.DBConnection;
import org.pet_adoption_system.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDao {
    // CREATE PET
    public int addPet(Pet pet) {
        String sql = "INSERT INTO pets (species, gender, size, age, status) VALUES (?, ?::gender_enum, ?::size_enum, ?, ?::pet_status_enum)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, pet.getSpecies());
            pst.setString(2, pet.getGender());
            pst.setString(3, pet.getSize());
            pst.setInt(4, pet.getAge());
            pst.setString(5, pet.getStatus());

            int rows_affected = pst.executeUpdate();
            con.close();
            return rows_affected;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    // READ PETS
        // GET A PET BY GENDER
    public List<Pet> getPetsByGender(String gender){
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets where gender = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, gender);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pet myPet = new Pet();
                myPet.setPet_id(rs.getInt("pet_id"));
                myPet.setSpecies(rs.getString("species"));
                myPet.setAge(rs.getInt("age"));
                myPet.setGender(rs.getString("gender"));
                myPet.setSize(rs.getString("size"));
                myPet.setStatus(rs.getString("status"));
                pets.add(myPet);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }

     // GET A PET BY SIZE
    public List<Pet> getPetsBySize(String size){
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets where size = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, size);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pet myPet = new Pet();
                myPet.setPet_id(rs.getInt("pet_id"));
                myPet.setSpecies(rs.getString("species"));
                myPet.setAge(rs.getInt("age"));
                myPet.setGender(rs.getString("gender"));
                myPet.setSize(rs.getString("size"));
                myPet.setStatus(rs.getString("status"));
                pets.add(myPet);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }

    // GET A PET BY ID
    public List<Pet> getPetsById(int id){
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets where pet_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pet myPet = new Pet();
                myPet.setPet_id(rs.getInt("pet_id"));
                myPet.setSpecies(rs.getString("species"));
                myPet.setAge(rs.getInt("age"));
                myPet.setGender(rs.getString("gender"));
                myPet.setSize(rs.getString("size"));
                myPet.setStatus(rs.getString("status"));
                pets.add(myPet);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }

        // GET A PET BY STATUS
    public List<Pet> getPetsByStatus(String status){
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets where status = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, status);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pet myPet = new Pet();
                myPet.setPet_id(rs.getInt("pet_id"));
                myPet.setSpecies(rs.getString("species"));
                myPet.setAge(rs.getInt("age"));
                myPet.setGender(rs.getString("gender"));
                myPet.setSize(rs.getString("size"));
                myPet.setStatus(rs.getString("status"));
                pets.add(myPet);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }

        // Get all pets
    public List<Pet> getAllPets(){
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Pet myPet = new Pet();
                myPet.setPet_id(rs.getInt("pet_id"));
                myPet.setSpecies(rs.getString("species"));
                myPet.setAge(rs.getInt("age"));
                myPet.setGender(rs.getString("gender"));
                myPet.setSize(rs.getString("size"));
                myPet.setStatus(rs.getString("status"));
                pets.add(myPet);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pets;
    }

    // UPDATE PET
    public int updatePet(Pet myPet) {
        String sql = "UPDATE pets SET species = ?, age = ?, gender = ?::gender_enum, size = ?::size_enum, status = ?::pet_status_enum WHERE pet_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, myPet.getSpecies());
            pst.setInt(2, myPet.getAge());
            pst.setString(3, myPet.getGender());
            pst.setString(4, myPet.getSize());
            pst.setString(5, myPet.getStatus());
            pst.setInt(6, myPet.getPet_id());

            int rows_affected = pst.executeUpdate();
            con.close();
            return rows_affected;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    // DELETE PET
    public int deletePet(int myPet){
        String sql = "DELETE FROM pets WHERE pet_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, myPet);

            int rows_affected = pst.executeUpdate();
            con.close();
            return rows_affected;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    // ALL PETS COUNT
    public int totalPetsAvailable(){
        String sql = "SELECT COUNT (*) FROM pets";
        int totalPets = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next()){
                totalPets = resultSet.getInt(1);
            }

            connection.close();

        }catch (SQLException exception){
            exception.printStackTrace();
            return 0;
        }
        return totalPets;
    }

    // ALL AVAILABLE PETS
    public int allAvailablePets(){
        String sql = "SELECT COUNT(*) FROM pets WHERE status = 'Available'";
        int totalPets = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next()){
                totalPets = resultSet.getInt(1);
            }
            connection.close();

        } catch (SQLException exception){
            exception.printStackTrace();
            return 0;
        }
        return totalPets;
    }

}
