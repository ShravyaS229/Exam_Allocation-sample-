package src.dao;

import src.models.AllocationResult;
import src.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AllocationDAO {

    // Save allocation
    public void saveAllocation(AllocationResult ar) {
        String sql = "INSERT INTO allocation_result (exam_date, time, room_no, semester, subject, faculty_name, designation) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ar.getExamDate());
            ps.setString(2, ar.getTime());
            ps.setInt(3, ar.getRoomNo());
            ps.setString(4, ar.getSemester());
            ps.setString(5, ar.getSubject());
            ps.setString(6, ar.getFacultyName());
            ps.setString(7, ar.getDesignation());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get allocations for a specific faculty
    public List<AllocationResult> getAllocationsForFaculty(String facultyName) {
        List<AllocationResult> list = new ArrayList<>();
        String sql = "SELECT * FROM allocation_result WHERE faculty_name = ? ORDER BY exam_date, time, room_no";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facultyName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AllocationResult ar = new AllocationResult(
                        rs.getString("exam_date"),
                        rs.getString("time"),
                        rs.getInt("room_no"),
                        rs.getString("semester"),
                        rs.getString("subject"),
                        rs.getString("faculty_name"),
                        rs.getString("designation")
                );
                list.add(ar);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
