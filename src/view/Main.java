package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/LoginView.fxml"));

        // Create Scene
        Scene scene = new Scene(root, 800, 600);

        // Configure Stage
        primaryStage.setTitle("Student Information System");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}