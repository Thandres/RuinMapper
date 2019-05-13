package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.io.IOException;

public class QuestRow extends HBox {

    @FXML
    TextArea description;

    @FXML
    TextArea notes;

    @FXML
    QuestRoomColumn roomColumn;

    private QuestPort quest;

    public QuestRow(QuestPort quest) {
        this.quest = quest;
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("QuestRow.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            setupEventHandler();

            description.setText(
                    this.quest.accessDescription());
            notes.setText(this.quest.accessNotes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupEventHandler() {

    }
}
