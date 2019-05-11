package ruinMapper.hexagon.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class FXMLFactory {
    public static VBox getAreaWindow() throws
            IOException {
        return FXMLLoader.load(
                getResource(
                        "..\\..\\..\\AreaWindow.fxml"));
    }
}
