package ruinMapper.hexagon.application.ui.area;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AreaGrid extends GridPane {

    private Map<Point, RoomPort> pointRoomMap;

    public AreaGrid() {
        pointRoomMap = new HashMap<>();
        setupGrid();
    }

    private void setupGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button btn = new Button();
                btn.setMinHeight(20);
                btn.setMinWidth(50);
                btn.setDisable(true);
                if (i == 4 && j == 4) {
                    btn.setDisable(false);
                }
                this.add(btn, i, j);
            }
        }
        this.setVgap(2);
        this.setHgap(2);
    }

}
