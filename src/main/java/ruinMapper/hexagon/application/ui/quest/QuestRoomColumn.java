package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.io.IOException;
import java.util.Set;

public class QuestRoomColumn extends
        TableView<QuestRoomRow> {

    private Set<RoomPort> roomSet;

    public QuestRoomColumn(Set<RoomPort> roomSet) {
        this.roomSet = roomSet;
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "QuestRoomColumn.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            setupTable();
            roomSet.forEach(
                    room -> this.getItems()
                            .add(new QuestRoomRow(room)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {
        TableColumn<QuestRoomRow, String> areaColumn = new TableColumn<>(
                "Area");
        areaColumn.setCellValueFactory(
                new PropertyValueFactory<>("areaName"));
        TableColumn<QuestRoomRow, String> coordinateColumn = new TableColumn<>(
                "Coordinates");
        coordinateColumn.setCellValueFactory(
                new PropertyValueFactory<>("coordinates"));
        TableColumn<QuestRoomRow, String> roomNameColumn = new TableColumn<>(
                "Roomname");
        roomNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("roomName"));
        this.getColumns()
                .addAll(areaColumn, coordinateColumn,
                        roomNameColumn);

    }
}
