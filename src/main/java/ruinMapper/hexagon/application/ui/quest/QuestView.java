package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.io.IOException;

public class QuestView extends VBox {

    @FXML
    Accordion questAccordion;

    @FXML
    Button newQuestBtn;

    @FXML
    TextField newQuestTitle;

    @FXML
    Label validationLabel;

    private ContextPort context;

    public QuestView(ContextPort context) {
        this.context = context;
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("QuestView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            setupEventHandler();
            questAccordion.getPanes()
                    .add(new QuestTitledPane(
                            context.createQuest(
                                    "Questtitle"),
                            questAccordion));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setupEventHandler() {
        newQuestBtn.setOnMouseClicked(
                event -> {
                    if (titleNotEmpty()) {
                        QuestPort newQuest = context
                                .createQuest(newQuestTitle
                                        .getText());
                        questAccordion.getPanes()
                                .add(new QuestTitledPane(
                                        newQuest,
                                        questAccordion));
                        newQuestTitle.clear();
                        validationLabel.setVisible(false);
                    } else {
                        validationLabel.setVisible(true);
                    }
                });
    }

    private boolean titleNotEmpty() {
        return !newQuestTitle.getText()
                .isEmpty();
    }
}
