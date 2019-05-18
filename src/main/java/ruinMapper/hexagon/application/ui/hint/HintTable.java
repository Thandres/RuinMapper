package ruinMapper.hexagon.application.ui.hint;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.ComponentLoader;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.hint.HintStatus;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HintTable extends TableView<HintRow> {
    @FXML
    private TableColumn<HintRow, TextArea> contentColumn;

    @FXML
    private TableColumn<HintRow, TextArea> notesColumn;

    @FXML
    private TableColumn<HintRow, VBox> roomColumn;

    @FXML
    private TableColumn<HintRow, KeywordColumn> keywordColumn;

    @FXML
    private TableColumn<HintRow, HintStatus> statusColumn;

    private Set<HintPort> allHints;

    private Map<String, TagPort> allKeywords;

    public HintTable() {
        this.allKeywords = allKeywords;
        // hooking up custom component to FXML
        ComponentLoader.loadCustomComponent(this,
                "HintTable.fxml");
        setupColumns();
    }

    /*
     * Injecting the context afterwards lets us use the Component
     * in FXML, so the overall design can be held entirely in FXML
     */
    public void injectContext(Set<HintPort> allHints,
                              Map<String, TagPort> allKeywords) {
        this.allHints = allHints;
        this.allKeywords = allKeywords;

        reloadDataFromContext();
    }

    /**
     * Filters out all hints not containing the keywords in filter
     *
     * @param keywordsToShow
     */
    public void applyFilter(List<TagPort> keywordsToShow) {
        if (keywordsToShow.size() > 0) {
            this.getItems().clear();
            Set<HintPort> visibleHints = new HashSet<>(
                    allHints);
            visibleHints.removeIf(
                    hint -> !hint
                            .accessKeyWords()
                            .containsAll(keywordsToShow));

            visibleHints.forEach(hint -> this.getItems()
                    .add(new HintRow(hint,
                            allKeywords)));
        } else {
            reloadDataFromContext();
        }
    }

    public void reloadDataFromContext() {
        this.getItems().clear();
        allHints
                .forEach(hint -> this.getItems()
                        .add(new HintRow(hint,
                                allKeywords)));
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
                new PropertyValueFactory<>(
                        "keywordColumn"));

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status"));
    }
}
