package ruinMapper.hexagon.application.ui.hint;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.ComponentLoader;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.*;

public class HintView extends VBox {

    @FXML
    private ComboBox<String> keywordComboBox;

    @FXML
    private TabPane activeFilterTabPane;

    @FXML
    private HintTable hintTable;

    private ContextPort context;

    private List<TagPort> activeKeywords;

    private Map<String, TagPort> allKeywords;

    public HintView(ContextPort context) {
        this.context = context;
        this.allKeywords = new HashMap<>();
        activeKeywords = new ArrayList<>();
//        context.accessArea("New Area").accessRoom(0,0).createHint("test1");
//        context.accessArea("New Area").accessRoom(0,0).createHint("test2");
//        context.accessArea("New Area").accessRoom(0,0).createHint("test3");
        // hooking up custom component to FXML
        ComponentLoader
                .loadCustomComponent(this, "HintView.fxml");
        setupView();
        setupEventHandlers();

    }

    public void reloadFromContext() {
        Set<TagPort> keywords = context
                .accessEveryKeyWord();
        if (keywords.size() != keywordComboBox.getItems()
                .size()) {
            keywordComboBox.getItems().clear();
            loadKeywordList();
        }
    }

    private void setupView() {
        loadKeywordList();
        keywordComboBox.setEditable(true);
        keywordComboBox.getItems()
                .sort(String::compareToIgnoreCase);
        keywordComboBox.setPromptText("Select keyword");
        hintTable.injectContext(context.accessEveryHint(),
                allKeywords);
    }

    private void setupEventHandlers() {
        keywordComboBox.setOnHidden(
                event -> {
                    if (somethingSelected()) {
                        activeFilterTabPane.getTabs()
                                .add(new Tab(
                                        keywordComboBox
                                                .getValue()));

                        keywordComboBox.getSelectionModel()
                                .clearSelection();
                        keywordComboBox.getItems()
                                .sort(String::compareToIgnoreCase);
                    }
                });

        keywordComboBox.getEditor()
                .addEventHandler(KeyEvent.KEY_TYPED,
                        event -> ComboboxEditorComparator
                                .sortItemsInDropdown(
                                        keywordComboBox));

        activeFilterTabPane.getTabs().addListener(
                (ListChangeListener<Tab>) c -> {
                    c.next();
                    if (c.getRemovedSize() != 0) {
                        filterRemoved(c);
                    }
                    if (c.getAddedSize() != 0) {
                        filterAdded(c);
                    }
                    hintTable.applyFilter(
                            activeKeywords);
                });
    }

    // Extracted method for visibility
    private void filterAdded(
            ListChangeListener.Change<? extends Tab> c) {
        for (Tab tab : c
                .getAddedSubList()) {
            activeKeywords
                    .add(allKeywords
                            .get(tab
                                    .getText()));
        }
    }

    // Extracted method for visibility
    private void filterRemoved(
            ListChangeListener.Change<? extends Tab> c) {

        for (Tab tab : c.getRemoved()) {
            activeKeywords
                    .removeIf(
                            keyword -> keyword
                                    .accessTag()
                                    .equals(tab
                                            .getText()));
        }
    }


    private void loadKeywordList() {
        context.accessEveryKeyWord()
                .forEach(keyword -> {
                    allKeywords.put(keyword.accessTag(),
                            keyword);
                    keywordComboBox.getItems()
                            .add(keyword.accessTag());
                });
    }

    private boolean somethingSelected() {
        return keywordComboBox.getSelectionModel()
                .getSelectedIndex() != -1;
    }
}

