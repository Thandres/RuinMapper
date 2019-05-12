package ruinMapper.hexagon.application.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class QuestView extends VBox {


    public QuestView() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("QuestView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
