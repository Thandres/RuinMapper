package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class QuestView extends VBox {

    @FXML
    Accordion questAccordion;

    public QuestView() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("QuestView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            questAccordion.getPanes()
                    .add(new QuestTitledPane("Questtitle"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
