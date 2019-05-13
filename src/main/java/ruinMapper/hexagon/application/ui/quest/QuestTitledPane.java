package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.io.IOException;

public class QuestTitledPane extends TitledPane {

    private QuestPort quest;

    public QuestTitledPane(QuestPort quest) {
        this.quest = quest;

        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "QuestTitledPane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.setContent(new QuestRow());
            this.setText(this.quest.accessTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
