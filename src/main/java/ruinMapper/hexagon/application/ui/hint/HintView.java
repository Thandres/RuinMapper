package ruinMapper.hexagon.application.ui.hint;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.ComponentLoader;
import ruinMapper.hexagon.domain.context.ContextPort;

public class HintView extends VBox {

    @FXML
    ChoiceBox<String> keywordFilter;

    @FXML
    TabPane activeFilter;

    private ContextPort context;

    public HintView(ContextPort context) {
        this.context = context;
        // hooking up custom component to FXML
        ComponentLoader
                .loadCustomComponent(this, "HintView.fxml");
        setupView();
        keywordFilter.setOnAction(
                event -> activeFilter.getTabs()
                        .add(new Tab(keywordFilter
                                .getValue())));

    }

    private void setupView() {
        context.accessEveryKeyWord()
                .forEach(keyword -> keywordFilter.getItems()
                        .add(keyword.accessTag()));

    }
}
