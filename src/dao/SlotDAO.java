package src.dao;

import src.models.Slot;
import src.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDAO {

    public List<Slot> getAllSlots() {
        List<Slot> slots = new ArrayList<>();
        String query = "SELECT slot_id, exam_date, semester, time FROM slots ORDER BY exam_date, time";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                slots.add(new Slot(
                        rs.getInt("slot_id"),
                        rs.getString("exam_date"),
                        rs.getString("semester"),
                        rs.getString("time")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }

    // NEW: Get slots by semester
    public List<Slot> getSlotsBySemester(String semester) {
        List<Slot> slots = new ArrayList<>();
        String query = "SELECT slot_id, exam_date, semester, time FROM slots WHERE semester = ? ORDER BY exam_date, time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, semester);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                slots.add(new Slot(
                        rs.getInt("slot_id"),
                        rs.getString("exam_date"),
                        rs.getString("semester"),
                        rs.getString("time")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slots;
    }
}
