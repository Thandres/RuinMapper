package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.quest.QuestRoomRow;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.hint.HintStatus;

public class HintRow {
    private TextArea content;
    private TextArea notes;
    private VBox roomColumn;
    private KeywordList keywords;
    private ChoiceBox<HintStatus> statusChoiceBox;

    private final int ROW_HEIGHT = 100;
    private final int TEXT_AREA_WIDTH = 200;

    private HintPort hint;

    public HintRow(HintPort hint) {
        this.hint = hint;
        content = new TextArea();
        content.setPrefHeight(ROW_HEIGHT);
        content.setPrefWidth(TEXT_AREA_WIDTH);
        notes = new TextArea();
        notes.setPrefHeight(ROW_HEIGHT);
        notes.setPrefWidth(TEXT_AREA_WIDTH);
        roomColumn = QuestRoomRowTransposer
                .transposeToColumn(new QuestRoomRow(
                        hint.accessRoom()));
        keywords = new KeywordList(hint.accessKeyWords());
        statusChoiceBox = new ChoiceBox<>();
        statusChoiceBox.getItems()
                .addAll(HintStatus.values());
        statusChoiceBox.getSelectionModel()
                .select(hint.getHintStatus());
    }

    public TextArea getContent() {
        return content;
    }

    public void setContent(TextArea content) {
        this.content = content;
    }

    public TextArea getNotes() {
        return notes;
    }

    public void setNotes(TextArea notes) {
        this.notes = notes;
    }

    public VBox getRoomColumn() {
        return roomColumn;
    }

    public void setRoomColumn(VBox roomColumn) {
        this.roomColumn = roomColumn;
    }

    public KeywordList getKeywords() {
        return keywords;
    }

    public void setKeywords(
            KeywordList keywords) {
        this.keywords = keywords;
    }

    public ChoiceBox<HintStatus> getStatusChoiceBox() {
        return statusChoiceBox;
    }

    public void setStatusChoiceBox(
            ChoiceBox<HintStatus> statusChoiceBox) {
        this.statusChoiceBox = statusChoiceBox;
    }
}
