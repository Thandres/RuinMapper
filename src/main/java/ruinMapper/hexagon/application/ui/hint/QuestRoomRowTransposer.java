package ruinMapper.hexagon.application.ui.hint;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ruinMapper.hexagon.application.ui.quest.QuestRoomRow;

public class QuestRoomRowTransposer {
    // QuestRoomRow already has all the info we need for HintTable
    public static VBox transposeToColumn(
            QuestRoomRow rowToTranspose) {
        VBox transposed = new VBox();
        transposed.getChildren()
                .addAll(new Label(
                                rowToTranspose.getAreaName()),
                        new Label(rowToTranspose
                                .getCoordinates()),
                        new Label(rowToTranspose
                                .getRoomName()));
        return transposed;
    }
}
