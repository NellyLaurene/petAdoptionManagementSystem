package org.pet_adoption_system.dao;

import org.pet_adoption_system.config.DBConnection;
import org.pet_adoption_system.model.Pet;
import org.pet_adoption_system.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {

    // CREATE STAFF
    public int addStaff(Staff staff) {
        String sql = "INSERT INTO staff(first_name, last_name, email, phone_number, position, password) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING staff_id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, staff.getFirst_name());
            pst.setString(2, staff.getLast_name());
            pst.setString(3, staff.getEmail());
            pst.setString(4, staff.getPhone_number());
            pst.setString(5, staff.getPosition());
            pst.setString(6, staff.getPassword());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int generatedId = rs.getInt("staff_id");
                staff.setStaff_id(generatedId);
                return 1;
            }

            return 0;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }


    // READ STAFF
        // All Staff
    public List<Staff> getAllStaff(){
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";

        try{
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Staff myStaff = new Staff();

                myStaff.setStaff_id(resultSet.getInt("staff_id"));
                myStaff.setFirst_name(resultSet.getString("first_name"));
                myStaff.setLast_name(resultSet.getString("last_name"));
                myStaff.setEmail(resultSet.getString("email"));
                myStaff.setPhone_number(resultSet.getString("phone_number"));
                myStaff.setPosition(resultSet.getString("position"));

                staffList.add(myStaff);
            }

        } catch (SQLException exception){
            exception.printStackTrace();
        }
            return staffList;
    }

        // Staff by position
    public List<Staff> getStaffByPosition(String position){
        List<Staff> myStaffList = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE position = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, position);

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()){
                Staff myStaff = new Staff();
                myStaff.setStaff_id(resultSet.getInt("staff_id"));
                myStaff.setFirst_name(resultSet.getString("first_name"));
                myStaff.setLast_name(resultSet.getString("last_name"));
                myStaff.setEmail(resultSet.getString("email"));
                myStaff.setPhone_number(resultSet.getString("phone_number"));
                myStaff.setPosition(resultSet.getString("position"));

                myStaffList.add(myStaff);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return myStaffList;
    }

    // GET A STAFF BY ID
    public List<Staff> getStaffById(int id){
        List<Staff> myStaffList = new ArrayList<>();
        String sql = "SELECT * FROM staff where staff_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Staff myStaff = new Staff();
                myStaff.setStaff_id(resultSet.getInt("staff_id"));
                myStaff.setFirst_name(resultSet.getString("first_name"));
                myStaff.setLast_name(resultSet.getString("last_name"));
                myStaff.setEmail(resultSet.getString("email"));
                myStaff.setPhone_number(resultSet.getString("phone_number"));
                myStaff.setPosition(resultSet.getString("position"));

                myStaffList.add(myStaff);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return myStaffList;
    }

    // UPDATE STAFF
    public int updateStaff(Staff staff){
        String sql = "UPDATE staff SET first_name = ?, last_name = ?, email = ?, phone_number = ?, position = ? WHERE staff_id = ?";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)){
            pst.setString(1, staff.getFirst_name());
            pst.setString(2, staff.getLast_name());
            pst.setString(3, staff.getEmail());
            pst.setString(4, staff.getPhone_number());
            pst.setString(5, staff.getPosition());
            pst.setInt(6, staff.getStaff_id());

            int rows_affected = pst.executeUpdate();
            return rows_affected;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    // DELETE STAFF
    public int deleteStaff(int staffId){
        String sql = "DELETE FROM staff WHERE staff_id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setInt(1, staffId);

            int rows_affected = pst.executeUpdate();
            connection.close();
            return rows_affected;

        } catch (SQLException exception){
            exception.printStackTrace();
            return 0;
        }
    }

    // LOGIN ADMIN
    public Staff loginAdmin(String email) {
        String sql = "SELECT * FROM staff WHERE email = ? AND position = 'Admin'";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rst = pst.executeQuery();

            if (rst.next()) {
                Staff staff = new Staff();
                staff.setStaff_id(rst.getInt("staff_id"));
                staff.setFirst_name(rst.getString("first_name"));
                staff.setLast_name(rst.getString("last_name"));
                staff.setEmail(rst.getString("email"));
                staff.setPhone_number(rst.getString("phone_number"));
                staff.setPosition(rst.getString("position"));
                staff.setPassword(rst.getString("password"));
                return staff;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Staff findByEmail(String email) {
        String sql = "SELECT * FROM staff WHERE email = ?";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rst = pst.executeQuery();

            if (rst.next()) {
                Staff staff = new Staff();
                staff.setStaff_id(rst.getInt("staff_id"));
                staff.setFirst_name(rst.getString("first_name"));
                staff.setLast_name(rst.getString("last_name"));
                staff.setEmail(rst.getString("email"));
                staff.setPhone_number(rst.getString("phone_number"));
                staff.setPosition(rst.getString("position"));
                staff.setPassword(rst.getString("password"));
                return staff;
            }
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
