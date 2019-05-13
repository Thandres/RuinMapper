package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.quest.QuestStatus;

import java.io.IOException;

public class QuestTitledPane extends TitledPane {

    @FXML
    private Button deleteBtn;

    @FXML
    ComboBox<QuestStatus> status;

    private QuestPort quest;

    private Accordion parent;


    public QuestTitledPane(QuestPort quest,
                           Accordion parent) {
        this.quest = quest;
        this.parent = parent;

        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "QuestTitledPane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            setupEventHandler();
            setupComponents();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupComponents() {
        this.setContent(new QuestRow(quest));
        this.setText(quest.accessTitle());
        status.getItems().addAll(QuestStatus.values());
        status.getSelectionModel()
                .select(quest.accessQuestStatus());
    }

    private void setupEventHandler() {
        deleteBtn.setOnMouseClicked(
                event -> {
                    quest.deleteQuest();
                    parent.getPanes().remove(this);
                });

        status.onActionProperty().setValue(event -> quest
                .changeQuestStatus(status.getValue()));
    }
}
