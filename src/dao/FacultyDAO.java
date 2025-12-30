package src.dao;

import src.models.Faculty;
import src.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    // Get all faculties
    public List<Faculty> getAllFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        String query = "SELECT * FROM faculty";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                faculties.add(new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getString("name"),
                        rs.getString("designation"),
                        rs.getBoolean("is_senior"),
                        rs.getBoolean("is_absent")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    // Mark a faculty absent or present
    public void markAbsent(int facultyId, boolean absent) {
        String query = "UPDATE faculty SET is_absent = ? WHERE faculty_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setBoolean(1, absent);
            pstmt.setInt(2, facultyId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
