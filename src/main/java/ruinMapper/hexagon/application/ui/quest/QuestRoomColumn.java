package ruinMapper.hexagon.application.ui.quest;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.io.IOException;
import java.util.List;

public class QuestRoomColumn extends VBox {

    private List<RoomPort> rooms;

    public QuestRoomColumn() {
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "QuestRoomColumn.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.getChildren().add(new RoomRow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
