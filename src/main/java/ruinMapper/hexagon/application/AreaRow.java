package ruinMapper.hexagon.application;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AreaRow extends HBox {
    public AreaRow() {
        this.getChildren()
                .addAll(createAreaTitleAndGridColumn(),
                        createButtonColumn(),
                        createNoteColumn());
    }

    private VBox createAreaTitleAndGridColumn() {
        VBox areaTitleColumn = new VBox();
        HBox areaTitleRow = new HBox();
        areaTitleRow.setSpacing(5);
        areaTitleColumn.setSpacing(5);
        Label areaName = new Label("Areaname");
        ComboBox<String> areaList = new ComboBox<>();
        areaList.getItems().add("test");
        Button createAreaBtn = new Button("New Area");
        areaTitleRow.getChildren()
                .addAll(areaName, areaList, createAreaBtn);
        AreaGrid areaGrid = new AreaGrid();
        areaTitleColumn.getChildren()
                .addAll(areaTitleRow, areaGrid);
        return areaTitleColumn;
    }

    private VBox createButtonColumn() {
        VBox roomButtonColumn = new VBox();
        roomButtonColumn.setSpacing(5);
        Label label = new Label("Options:");
        Button newRoomBtn = new Button("New Room");
        newRoomBtn.setPrefWidth(100);
        Button deleteRoomBtn = new Button("DeleteRoom");
        deleteRoomBtn.setPrefWidth(100);
        roomButtonColumn.getChildren()
                .addAll(label, newRoomBtn, deleteRoomBtn);
        return roomButtonColumn;
    }

    private VBox createNoteColumn() {
        VBox noteColumn = new VBox();
        noteColumn.setSpacing(5);
        Label label = new Label("Notes of Area");
        TextArea notes = new TextArea();
        notes.setPrefHeight(250);
        notes.setPrefWidth(250);
        noteColumn.getChildren()
                .addAll(label, notes);
        return noteColumn;
    }
}
