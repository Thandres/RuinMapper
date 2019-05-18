package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Map;

public class KeywordColumn extends VBox {

    private HintPort hint;


    private Map<String, TagPort> allKeywords;

    private ComboBox<String> addKeywordComboBox;

    //TODO sorting
    public KeywordColumn(HintPort hint,
                         Map<String, TagPort> allKeywords) {
        this.hint = hint;
        this.allKeywords = allKeywords;


        setupKeywords();
        setupEventHandler();
    }

    private void setupKeywords() {
        addKeywordComboBox = new ComboBox<>();
        addKeywordComboBox.setPromptText("Add keyword");
        addKeywordComboBox.setEditable(true);

        allKeywords.values().forEach(
                keyword -> addKeywordComboBox.getItems()
                        .add(keyword.accessTag()));
        addKeywordComboBox.getItems()
                .sort(String::compareToIgnoreCase);

        this.getChildren()
                .add(addKeywordComboBox);
        for (TagPort keyword : hint.accessKeyWords()) {
            addRow(keyword.accessTag());
        }
    }

    private void setupEventHandler() {
        addKeywordComboBox.setOnHidden(
                event -> {
                    if (itemSelected()) {
                        hint.addKeyWord(allKeywords.get(
                                addKeywordComboBox
                                        .getValue()));
                        addRow(addKeywordComboBox
                                .getValue());
                        addKeywordComboBox
                                .getSelectionModel()
                                .clearSelection();
                        addKeywordComboBox.getItems()
                                .sort(String::compareToIgnoreCase);
                    }
                });
        addKeywordComboBox.getEditor().addEventHandler(
                KeyEvent.KEY_TYPED,
                event -> {
                    ComboboxEditorComparator
                            .sortItemsInDropdown(
                                    addKeywordComboBox);
                });
    }

    private void addRow(String keyword) {
        Button removeButton = new Button("-");
        Label keyLabel = new Label(keyword);
        HBox row = new HBox(keyLabel, removeButton);
        HBox.setHgrow(keyLabel, Priority.ALWAYS);
        removeButton.setOnMouseClicked(event -> {
            this.getChildren().remove(row);
            hint.removeKeyWord(
                    allKeywords.get(keyLabel.getText()));
        });
        this.getChildren().add(row);
    }

    private boolean itemSelected() {
        return addKeywordComboBox
                .getSelectionModel()
                .getSelectedIndex() != -1;
    }
}
