package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.ComponentLoader;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.util.Set;

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
        ComponentLoader.loadCustomComponent(this,
                "QuestView.fxml");

        setupEventHandler();
        questAccordion.getPanes()
                .add(new QuestTitledPane(
                        context.createQuest(
                                "Questtitle"),
                        questAccordion));
        reloadFromContext();

    }

    public void reloadFromContext() {
        Set<QuestPort> quests = context.accessEveryQuest();
        questAccordion.getPanes().clear();
        quests.forEach(
                questPort -> questAccordion.getPanes()
                        .add(new QuestTitledPane(questPort,
                                questAccordion)));
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
