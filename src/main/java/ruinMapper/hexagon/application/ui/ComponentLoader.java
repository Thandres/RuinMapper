package ruinMapper.hexagon.application.ui;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ComponentLoader {
    public static <T> void loadCustomComponent(
            T customComponent, String fxmlFile) {
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                customComponent.getClass().getResource(
                        fxmlFile));

        fxmlLoader.setRoot(customComponent);
        fxmlLoader.setController(customComponent);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            //TODO proper error handling
            e.printStackTrace();
        }
    }
}
