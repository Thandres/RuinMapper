package ruinMapper.hexagon.application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ruinMapper.hexagon.domain.ContextSupplierPort;

import java.io.IOException;


public class MainView extends Application {
    @FXML
    private VBox mainWindow;
    @FXML
    private HBox startupOptions;


    private ContextSupplierPort contextSupplierPort;

    public void loadMap(MouseEvent mouseEvent) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        Stage window = new Stage();

//        File file = fileChooser.showDialog(window);
//        ContextSupplierPort contextSupplierPort = new ContextSupplier(file.getAbsolutePath());
        //contextSupplierPort.createNewContext(file.getName());


        try {
            Parent newRoot = FXMLLoader.load(getClass()
                    .getResource(
                            "..\\..\\..\\AreaWindow.fxml"));
            mainWindow.getChildren().add(newRoot);
            mainWindow.getChildren().remove(startupOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource(
                        "..\\..\\..\\MainView.fxml"));
        primaryStage.setTitle("RuinMapper");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public void loadUI(String... args) {
        launch(args);
    }
}
