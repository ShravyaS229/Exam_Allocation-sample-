package src.frontend;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import src.dao.AllocationDAO;
import src.dao.FacultyDAO;
import src.models.AllocationResult;
import src.models.Faculty;

import java.util.List;

public class FacultyPage extends Application {

    private TableView<AllocationResult> table = new TableView<>();
    private FacultyDAO facultyDAO = new FacultyDAO();
    private AllocationDAO allocationDAO = new AllocationDAO();

    @Override
    public void start(Stage stage) {

        Label label = new Label("Faculty Allocation Page");

        // Dropdown to select faculty
        ComboBox<Faculty> facultyCombo = new ComboBox<>();
        List<Faculty> faculties = facultyDAO.getAllFaculties(); // matches your DAO
        facultyCombo.setItems(FXCollections.observableArrayList(faculties));

        // ===== Table Columns =====
        TableColumn<AllocationResult, Integer> roomCol = new TableColumn<>("Room No");
        roomCol.setCellValueFactory(f -> new javafx.beans.property.ReadOnlyObjectWrapper<>(f.getValue().getRoomNo()));

        TableColumn<AllocationResult, String> dateCol = new TableColumn<>("Exam Date");
        dateCol.setCellValueFactory(f -> new javafx.beans.property.ReadOnlyStringWrapper(f.getValue().getExamDate()));

        TableColumn<AllocationResult, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(f -> new javafx.beans.property.ReadOnlyStringWrapper(f.getValue().getTime()));

        TableColumn<AllocationResult, String> semCol = new TableColumn<>("Semester");
        semCol.setCellValueFactory(f -> new javafx.beans.property.ReadOnlyStringWrapper(f.getValue().getSemester()));

        TableColumn<AllocationResult, String> subCol = new TableColumn<>("Subject");
        subCol.setCellValueFactory(f -> new javafx.beans.property.ReadOnlyStringWrapper(f.getValue().getSubject()));

        TableColumn<AllocationResult, String> facCol = new TableColumn<>("Faculty");
        facCol.setCellValueFactory(f -> new javafx.beans.property.ReadOnlyStringWrapper(f.getValue().getFacultyName()));

        table.getColumns().addAll(roomCol, dateCol, timeCol, semCol, subCol, facCol);

        // ===== Button =====
        Button showBtn = new Button("Show Allocation");
        showBtn.setOnAction(e -> {
            Faculty selected = facultyCombo.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Use the correct DAO method
                List<AllocationResult> allocations = allocationDAO.getAllocationsForFaculty(selected.getName());
                table.setItems(FXCollections.observableArrayList(allocations));
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a faculty first.");
                alert.showAndWait();
            }
        });

        VBox root = new VBox(10, label, facultyCombo, showBtn, table);
        root.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Faculty Allocation Page");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
