package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.io.IOException;
import java.util.Set;

public class QuestRoomTable extends
        TableView<QuestRoomRow> {
    @FXML
    TableColumn<QuestRoomRow, String> areaColumn;

    @FXML
    TableColumn<QuestRoomRow, String> coordinateColumn;

    @FXML
    TableColumn<QuestRoomRow, String> roomColumn;
    private Set<RoomPort> roomSet;

    public QuestRoomTable(Set<RoomPort> roomSet) {
        this.roomSet = roomSet;
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "QuestRoomTable.fxml"));

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

        areaColumn.setCellValueFactory(
                new PropertyValueFactory<>("areaName"));

        coordinateColumn.setCellValueFactory(
                new PropertyValueFactory<>("coordinates"));

        roomColumn.setCellValueFactory(
                new PropertyValueFactory<>("roomName"));
    }
}
