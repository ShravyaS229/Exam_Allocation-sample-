package src.frontend;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import src.dao.FacultyDAO;
import src.models.Faculty;

public class AdminPage extends Application {

    private TableView<Faculty> table = new TableView<>();
    private FacultyDAO dao = new FacultyDAO();

    @Override
    public void start(Stage stage) {

        TableColumn<Faculty, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(f -> f.getValue().facultyIdProperty().asObject());

        TableColumn<Faculty, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(f -> f.getValue().nameProperty());

        TableColumn<Faculty, String> desigCol = new TableColumn<>("Designation");
        desigCol.setCellValueFactory(f -> f.getValue().designationProperty());

        TableColumn<Faculty, Boolean> absentCol = new TableColumn<>("Absent");
        absentCol.setCellValueFactory(f -> f.getValue().absentProperty());

        TableColumn<Faculty, Boolean> seniorCol = new TableColumn<>("Senior");
        seniorCol.setCellValueFactory(f -> f.getValue().seniorProperty());

        table.getColumns().addAll(idCol, nameCol, desigCol, absentCol, seniorCol);

        loadData();

        Button absentBtn = new Button("Mark Absent");
        absentBtn.setOnAction(e -> mark(true));

        Button presentBtn = new Button("Mark Present");
        presentBtn.setOnAction(e -> mark(false));

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> loadData());

        HBox buttons = new HBox(10, absentBtn, presentBtn, refreshBtn);
        VBox root = new VBox(10, table, buttons);
        root.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.setTitle("Admin - Faculty Management");
        stage.show();
    }

    private void loadData() {
        table.setItems(FXCollections.observableArrayList(dao.getAllFaculties()));
    }

    private void mark(boolean absent) {
        Faculty f = table.getSelectionModel().getSelectedItem();
        if (f != null) {
            dao.markAbsent(f.getFacultyId(), absent);
            loadData();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a faculty first.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
