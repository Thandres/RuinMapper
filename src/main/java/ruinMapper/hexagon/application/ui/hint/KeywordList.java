package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class KeywordList extends VBox {

    private List<String> keywords;

    public KeywordList(
            List<String> keywords) {
        this.keywords = keywords;
        this.getChildren()
                .add(new HBox(new Label("Add Keyword..."),
                        new Button("+")));
        keywords.forEach(keyword -> this.getChildren()
                .add(new HBox(new Label(keyword),
                        new Button("-"))));
    }
}
