package org.pet_adoption_system.dao;

import org.pet_adoption_system.config.DBConnection;
import org.pet_adoption_system.model.Adoption;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptionDao {

    public int addAdoption(Adoption adoption) {
        String sql = "INSERT INTO adoptions (pet_id, adopter_id, adoption_date, adoption_fee, status, staff_id) VALUES (?, ?, ?, ?, ?::adoption_status_enum, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, adoption.getPet_id());
            pst.setInt(2, adoption.getAdopter_id());
            pst.setDate(3, Date.valueOf(adoption.getAdoption_date()));
            pst.setBigDecimal(4, adoption.getAdoption_fee());
            pst.setString(5, adoption.getStatus());
            pst.setInt(6, adoption.getStaff_id());

            int rowsAffected = pst.executeUpdate();
            con.close();
            return rowsAffected;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // Get all adoptions
    public List<Adoption> getAllAdoptions() {
        List<Adoption> adoptions = new ArrayList<>();
        String sql = "SELECT * FROM adoptions";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Adoption adoption = new Adoption();
                adoption.setAdoption_id(rs.getInt("adoption_id"));
                adoption.setPet_id(rs.getInt("pet_id"));
                adoption.setAdopter_id(rs.getInt("adopter_id"));
                adoption.setAdoption_date(rs.getDate("adoption_date").toLocalDate());
                adoption.setAdoption_fee(rs.getBigDecimal("adoption_fee"));
                adoption.setStatus(rs.getString("status"));
                adoption.setStaff_id(rs.getInt("staff_id"));
                adoptions.add(adoption);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return adoptions;
    }

    // Get adoptions by status
    public List<Adoption> getAdoptionsByStatus(String status) {
        List<Adoption> adoptions = new ArrayList<>();
        String sql = "SELECT * FROM adoptions WHERE status = ?::adoption_status_enum";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, status);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Adoption adoption = new Adoption();
                adoption.setAdoption_id(rs.getInt("adoption_id"));
                adoption.setPet_id(rs.getInt("pet_id"));
                adoption.setAdopter_id(rs.getInt("adopter_id"));
                adoption.setAdoption_date(rs.getDate("adoption_date").toLocalDate());
                adoption.setAdoption_fee(rs.getBigDecimal("adoption_fee"));
                adoption.setStatus(rs.getString("status"));
                adoption.setStaff_id(rs.getInt("staff_id"));
                adoptions.add(adoption);
            }

            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return adoptions;
    }

    // UPDATE ADOPTION
    public int updateAdoption(Adoption adoption) {
        String sql = "UPDATE adoptions SET pet_id = ?, adopter_id = ?, adoption_date = ?, adoption_fee = ?, status = ?::adoption_status_enum, staff_id = ? WHERE adoption_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, adoption.getPet_id());
            pst.setInt(2, adoption.getAdopter_id());
            pst.setDate(3, Date.valueOf(adoption.getAdoption_date()));
            pst.setBigDecimal(4, adoption.getAdoption_fee());
            pst.setString(5, adoption.getStatus());
            pst.setInt(6, adoption.getStaff_id());
            pst.setInt(7, adoption.getAdoption_id());

            int rowsAffected = pst.executeUpdate();
            con.close();
            return rowsAffected;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // DELETE ADOPTION
    public int deleteAdoption(int adoption_id) {
        String sql = "DELETE FROM adoptions WHERE adoption_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, adoption_id);

            int rowsAffected = pst.executeUpdate();
            con.close();
            return rowsAffected;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    // PENDING ADOPTIONS
    public int pendingAdoption(){
        String sql = "SELECT COUNT(*) FROM adoptions WHERE status = 'Pending'::adoption_status_enum";
        int totalAdoptions = 0;

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet resultSet = pst.executeQuery();

            if(resultSet.next()){
                totalAdoptions = resultSet.getInt(1);
            }
            connection.close();

        } catch (SQLException exception){
            exception.printStackTrace();
            return 0;
        }
        return totalAdoptions;
    }

    // Get adoption by ID
    public Adoption getAdoptionById(int adoptionId) {
        String sql = "SELECT * FROM adoptions WHERE adoption_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, adoptionId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Adoption adoption = new Adoption();
                adoption.setAdoption_id(rs.getInt("adoption_id"));
                adoption.setPet_id(rs.getInt("pet_id"));
                adoption.setAdopter_id(rs.getInt("adopter_id"));
                adoption.setAdoption_date(rs.getDate("adoption_date").toLocalDate());
                adoption.setAdoption_fee(rs.getBigDecimal("adoption_fee"));
                adoption.setStatus(rs.getString("status"));
                adoption.setStaff_id(rs.getInt("staff_id"));
                con.close();
                return adoption;
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}