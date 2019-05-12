package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;

import java.io.IOException;

public class QuestTitledPane extends TitledPane {

    public QuestTitledPane(String title) {
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "QuestTitledPane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.setContent(new QuestRow());
            this.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
