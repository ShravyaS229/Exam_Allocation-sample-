package src.dao;

import src.models.Subject;
import src.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public List<Subject> getSubjectsBySemester(String semester) {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT * FROM subjects WHERE semester = ? ORDER BY subject_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                subjects.add(new Subject(
                        rs.getString("subject_code"),
                        rs.getString("subject_name"),
                        rs.getString("semester")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
}
