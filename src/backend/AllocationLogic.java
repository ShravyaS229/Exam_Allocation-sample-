package src.backend;

import src.dao.FacultyDAO;
import src.dao.RoomDAO;
import src.dao.SlotDAO;
import src.dao.SubjectDAO;
import src.models.Faculty;
import src.models.Room;
import src.models.Slot;
import src.models.Subject;
import src.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class AllocationLogic {

    private FacultyDAO facultyDAO = new FacultyDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private SlotDAO slotDAO = new SlotDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    public void generateAllocation(String semester) {
        List<Faculty> faculties = facultyDAO.getAllFaculties();
        List<Room> rooms = roomDAO.getAllRooms();
        List<Slot> slots = slotDAO.getSlotsBySemester(semester);
        List<Subject> subjects = subjectDAO.getSubjectsBySemester(semester);

        if(subjects.isEmpty() || slots.isEmpty()) {
            System.out.println("No slots or subjects available for semester " + semester);
            return;
        }

        Map<String, Boolean> facultyAssigned = new HashMap<>();

        for (Faculty f : faculties) {
            facultyAssigned.put(f.getName(), false);
        }

        int roomIndex = 0;
        int facultyIndex = 0;
        int subjectIndex = 0;

        try (Connection conn = DBConnection.getConnection()) {
            String insertQuery = "INSERT INTO allocation_result (exam_date, time, room_no, semester, subject, faculty_name, designation) VALUES (?, ?, ?, ?, ?, ?, ?)";

            for (Slot slot : slots) {
                for (Room room : rooms) {
                    if(subjectIndex >= subjects.size()) subjectIndex = 0;

                    Subject subject = subjects.get(subjectIndex);

                    // Find next available faculty
                    Faculty assignedFaculty = null;
                    for(int i=0; i<faculties.size(); i++){
                        Faculty f = faculties.get(facultyIndex % faculties.size());
                        facultyIndex++;
                        if(!facultyAssigned.get(f.getName())) {
                            assignedFaculty = f;
                            facultyAssigned.put(f.getName(), true);
                            break;
                        }
                    }

                    if(assignedFaculty == null){
                        System.out.println("Not enough faculties available for subject: " + subject.getName());
                        continue;
                    }

                    // Insert allocation
                    try (PreparedStatement ps = conn.prepareStatement(insertQuery)) {
                        ps.setString(1, slot.getExamDate());
                        ps.setString(2, slot.getTime());
                        ps.setInt(3, room.getRoomNo());
                        ps.setString(4, semester);
                        ps.setString(5, subject.getName());
                        ps.setString(6, assignedFaculty.getName());
                        ps.setString(7, assignedFaculty.getDesignation());
                        ps.executeUpdate();
                    }

                    subjectIndex++;
                }

                // Reset faculty availability for next slot
                for (Faculty f : faculties) {
                    facultyAssigned.put(f.getName(), false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Allocation completed for semester " + semester);
    }
}
