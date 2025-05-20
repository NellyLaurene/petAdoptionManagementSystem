package org.pet_adoption_system.dao;

import org.pet_adoption_system.config.DBConnection;
import org.pet_adoption_system.model.Adopter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// The Dao includes all the database logic using jdbc (crud operation)
public class AdopterDao {

    // CREATE NEW ADOPTER
    public int addAdopter(Adopter adopter) {
        String sql = "INSERT INTO adopters(first_name, last_name, email, phone_number, location) VALUES(?, ?, ?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, adopter.getFirst_name());
            pst.setString(2, adopter.getLast_name());
            pst.setString(3, adopter.getEmail());
            pst.setString(4, adopter.getPhone_number());
            pst.setString(5, adopter.getlocation());

            int rows_affected = pst.executeUpdate();
            con.close();
            return rows_affected;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    // List all adopters
        // Adopter by ID
    public Adopter getAdopterById(int id){
        String sql = "SELECT * FROM adopters WHERE adopter_id = ?";
        Adopter myAdopter = null;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setInt(1, id);

            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next()){
                myAdopter = new Adopter();
                myAdopter.setAdopter_id(resultSet.getInt("adopter_id"));
                myAdopter.setFirst_name(resultSet.getString("first_name"));
                myAdopter.setLast_name(resultSet.getString("last_name"));
                myAdopter.setEmail(resultSet.getString("email"));
                myAdopter.setPhone_number(resultSet.getString("phone_number"));
                myAdopter.setlocation(resultSet.getString("location"));
            }
            connection.close();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return myAdopter;
    }

        // All Adopters
    public List<Adopter> getAllAdopters(){
        List<Adopter> myAdopterList = new ArrayList<>();
        String sql = "SELECT * FROM adopters";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()){
                Adopter myAdopter = new Adopter();
                myAdopter.setAdopter_id(resultSet.getInt("adopter_id"));
                myAdopter.setFirst_name(resultSet.getString("first_name"));
                myAdopter.setLast_name(resultSet.getString("last_name"));
                myAdopter.setEmail(resultSet.getString("email"));
                myAdopter.setPhone_number(resultSet.getString("phone_number"));
                myAdopter.setlocation(resultSet.getString("location"));

                myAdopterList.add(myAdopter);
            }
            connection.close();

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return myAdopterList;
    }

    // UPDATE ADOPTERS
    public int updateAdopter(Adopter myAdopter){
        String sql = "UPDATE ADOPTERS SET first_name = ?, last_name = ?, email = ?, phone_number = ?, location = ? WHERE adopter_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, myAdopter.getFirst_name());
            pst.setString(2, myAdopter.getLast_name());
            pst.setString(3, myAdopter.getEmail());
            pst.setString(4, myAdopter.getPhone_number());
            pst.setString(5, myAdopter.getlocation());
            pst.setInt(6, myAdopter.getAdopter_id());

            int rowsAffected = pst.executeUpdate();
            con.close();
            return rowsAffected;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    // Delete Adopters
    public int deleteAdopter(int id){
        String sql = "DELETE FROM ADOPTERS WHERE adopter_id = ?";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, id);

            int rows_affected = pst.executeUpdate();
            con.close();
            return rows_affected;

        } catch (SQLException exception){
            exception.printStackTrace();
            return 0;
        }
    }

    // TOTAL ADOPTERS
    public int totalAdopter(){
        String sql = "SELECT COUNT (*) FROM adopters";
        int totalAdopter = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next()){
                totalAdopter = resultSet.getInt(1);
            }
            connection.close();

        } catch (SQLException exception){
            exception.printStackTrace();
            return 0;
        }
        return totalAdopter;
    }

}
