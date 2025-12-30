package src.models;

public class AllocationResult {
    private int allocationId; // auto_increment
    private String examDate;
    private String time;
    private int roomNo;
    private String semester;
    private String subject;
    private String facultyName;
    private String designation;

    // Constructor without allocationId (for inserts)
    public AllocationResult(String examDate, String time, int roomNo, String semester,
                            String subject, String facultyName, String designation) {
        this.examDate = examDate;
        this.time = time;
        this.roomNo = roomNo;
        this.semester = semester;
        this.subject = subject;
        this.facultyName = facultyName;
        this.designation = designation;
    }

    // Getters & setters
    public int getAllocationId() { return allocationId; }
    public void setAllocationId(int allocationId) { this.allocationId = allocationId; }

    public String getExamDate() { return examDate; }
    public void setExamDate(String examDate) { this.examDate = examDate; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}
