package ruinMapper.hexagon.application.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.io.IOException;

public class QuestRow extends HBox {

    @FXML
    QuestRoomColumn roomColumn;

    @FXML
    ComboBox status;

    @FXML
    TextArea description;

    @FXML
    TextArea notes;

    @FXML
    Button deleteBtn;

    private QuestPort quest;

    public QuestRow() {//TODO Databinding to QuestPort
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("QuestRow.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
