package ruinMapper.hexagon.application.ui.tag;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ruinMapper.hexagon.domain.context.ContextPort;

import java.io.IOException;

public class TagView extends HBox {

    @FXML
    TextField newTagField;

    @FXML
    Button createRoomTagBtn;

    @FXML
    ListView validTags;

    @FXML
    TextField newKeywordField;

    @FXML
    Button createKeywordBtn;

    @FXML
    ListView keywords;

    private ContextPort context;

    public TagView(ContextPort context) {
        this.context = context;
        // hooking up custom component to FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "TagView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
