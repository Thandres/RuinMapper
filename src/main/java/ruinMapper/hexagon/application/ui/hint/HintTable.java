package ruinMapper.hexagon.application.ui.hint;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import ruinMapper.hexagon.domain.hint.HintStatus;

import java.io.IOException;
import java.util.Collections;

public class HintTable extends TableView<HintRow> {
    public HintTable() {
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "HintTable.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            setupColumns();
            this.getItems().add(new HintRow(new TextArea(),
                    new TextArea(),
                    "", new KeywordList(
                    Collections.singletonList("tag")),
                    HintStatus.NO_IDEA));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupColumns() {
        TableColumn<HintRow, TextArea> contentColumn = new TableColumn<>(
                "Content");
        contentColumn.setCellValueFactory(
                new PropertyValueFactory<>("content"));

        TableColumn<HintRow, TextArea> notesColumn = new TableColumn<>(
                "Notes");
        notesColumn.setCellValueFactory(
                new PropertyValueFactory<>("notes"));

        TableColumn<HintRow, String> roomColumn = new TableColumn<>(
                "Room");
        roomColumn.setCellValueFactory(
                new PropertyValueFactory<>("room"));

        TableColumn<HintRow, String> keywordColumn = new TableColumn<>(
                "Keywords");
        keywordColumn.setCellValueFactory(
                new PropertyValueFactory<>("keywords"));

        TableColumn<HintRow, String> statusColumn = new TableColumn<>(
                "Status");
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        this.getColumns().addAll(contentColumn, notesColumn,
                roomColumn, keywordColumn, statusColumn);
    }
}
