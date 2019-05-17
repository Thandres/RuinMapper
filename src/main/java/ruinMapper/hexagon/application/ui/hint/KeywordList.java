package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.util.Set;

public class KeywordList extends VBox {

    private Set<TagPort> keywords;

    private Button addButton;

    //TODO sorting
    public KeywordList(
            Set<TagPort> keywords) {
        this.keywords = keywords;
        this.getChildren()
                .add(new HBox(new Label("Add Keyword..."),
                        new Button("+")));
        keywords.forEach(keyword -> this.getChildren()
                .add(new HBox(
                        new Label(keyword.accessTag()),
                        new Button("-"))));
    }

    private void setupKeywords() {
        addButton = new Button("+");
        this.getChildren()
                .add(new HBox(new Label("Add Keyword..."),
                        addButton));
        for (TagPort keyword : keywords) {
            Button removeButton = new Button("-");
            removeButton.setOnMouseClicked(
                    event -> this.getChildren()
                            .remove(removeButton
                                    .getParent()));
            this.getChildren().add(new HBox(
                    new Label(keyword.accessTag()),
                    new Button("-")));
        }
    }
}
