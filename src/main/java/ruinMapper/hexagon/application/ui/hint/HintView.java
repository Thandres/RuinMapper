package ruinMapper.hexagon.application.ui.hint;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HintView extends VBox {

    @FXML
    ChoiceBox<String> keywordFilter;

    @FXML
    TabPane activeFilter;

    public HintView() {
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "HintView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            keywordFilter.getItems().add("tset0");
            keywordFilter.getItems().add("tset1");
            keywordFilter.getItems().add("tset2");
            keywordFilter.setOnAction(
                    event -> activeFilter.getTabs()
                            .add(new Tab(keywordFilter
                                    .getValue())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
