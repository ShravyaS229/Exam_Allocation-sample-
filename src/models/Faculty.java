package src.models;

import javafx.beans.property.*;

public class Faculty {
    private IntegerProperty facultyId;
    private StringProperty name;
    private StringProperty designation;
    private BooleanProperty senior;
    private BooleanProperty absent;

    public Faculty(int facultyId, String name, String designation, boolean isSenior, boolean isAbsent) {
        this.facultyId = new SimpleIntegerProperty(facultyId);
        this.name = new SimpleStringProperty(name);
        this.designation = new SimpleStringProperty(designation);
        this.senior = new SimpleBooleanProperty(isSenior);
        this.absent = new SimpleBooleanProperty(isAbsent);
    }

    public int getFacultyId() { return facultyId.get(); }
    public String getName() { return name.get(); }
    public String getDesignation() { return designation.get(); }
    public boolean isSenior() { return senior.get(); }
    public boolean isAbsent() { return absent.get(); }

    public IntegerProperty facultyIdProperty() { return facultyId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty designationProperty() { return designation; }
    public BooleanProperty seniorProperty() { return senior; }
    public BooleanProperty absentProperty() { return absent; }
}
