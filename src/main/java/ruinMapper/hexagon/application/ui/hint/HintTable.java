package ruinMapper.hexagon.application.ui.hint;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.ComponentLoader;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.hint.HintStatus;

public class HintTable extends TableView<HintRow> {
    @FXML
    TableColumn<HintRow, TextArea> contentColumn;

    @FXML
    TableColumn<HintRow, TextArea> notesColumn;

    @FXML
    TableColumn<HintRow, VBox> roomColumn;

    @FXML
    TableColumn<HintRow, KeywordList> keywordColumn;

    @FXML
    TableColumn<HintRow, HintStatus> statusColumn;

    private ContextPort context;

    public HintTable() {
        // hooking up custom component to FXML
        ComponentLoader.loadCustomComponent(this,
                "HintTable.fxml");
        setupColumns();
    }

    /*
     * Injecting the context afterwards lets us use the Component
     * in FXML, so the overall design can be held entirely in FXML
     */
    public void injectContext(ContextPort context) {
        this.context = context;
        reloadDataFromContext();
    }

    public void reloadDataFromContext() {
        this.getItems().clear();
        context.accessEveryHint()
                .forEach(hint -> this.getItems()
                        .add(new HintRow(hint)));
    }

    // looks for getter/setter in HintRow with the name of the String literal
    // see documentation of TableView for additional information
    private void setupColumns() {
        contentColumn.setCellValueFactory(
                new PropertyValueFactory<>("content"));

        notesColumn.setCellValueFactory(
                new PropertyValueFactory<>("notes"));

        roomColumn.setCellValueFactory(
                new PropertyValueFactory<>("room"));

        keywordColumn.setCellValueFactory(
                new PropertyValueFactory<>("keywords"));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status"));
    }
}
