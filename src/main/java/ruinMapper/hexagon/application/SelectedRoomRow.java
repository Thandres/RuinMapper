package ruinMapper.hexagon.application;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class SelectedRoomRow extends VBox {
    public SelectedRoomRow() {
        this.getChildren().addAll(createNameAndTagRow());
    }

    private HBox createNameAndTagRow() {
        HBox nameAndTagRow = new HBox();
        nameAndTagRow.setPrefHeight(50);
        nameAndTagRow.setSpacing(5);
        Label label = new Label("Roomname");
        ComboBox<String> validTagList = new ComboBox<>();
        validTagList.getItems().addAll("Tags");
        validTagList.getSelectionModel().selectFirst();
        TabPane tagList = new TabPane();
        HBox.setHgrow(tagList, Priority.ALWAYS);
        tagList.getTabs().add(new Tab("Tag1"));
        nameAndTagRow.getChildren()
                .addAll(label, validTagList, tagList);
        return nameAndTagRow;
    }

}
