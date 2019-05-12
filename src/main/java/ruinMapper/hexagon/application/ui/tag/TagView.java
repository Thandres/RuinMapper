package ruinMapper.hexagon.application.ui.tag;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.tag.TagPort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TagView extends HBox {

    @FXML
    TextField newTagField;

    @FXML
    Button createRoomTagBtn;

    @FXML
    Button deleteRoomTagBtn;

    @FXML
    ListView<String> validTags;

    @FXML
    TextField newKeywordField;

    @FXML
    Button createKeywordBtn;

    @FXML
    Button deleteKeywordBtn;

    @FXML
    ListView<String> keywords;

    private ContextPort context;

    private List<TagPort> keywordList;
    private List<TagPort> roomTagList;


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
            setupEventhandlers();
            reloadFromDomain();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }

    }

    protected void reloadFromDomain() {
        keywordList = new ArrayList<>(
                context.accessEveryKeyWord());
        keywordList.sort(Comparator
                .comparing(TagPort::accessTag));
        keywords.getItems().clear();

        keywordList.forEach(tag -> keywords.getItems()
                .add(tag.accessTag()));
        roomTagList = new ArrayList<>(
                context.accessValidTags());
        roomTagList.sort(Comparator
                .comparing(TagPort::accessTag));
        validTags.getItems().clear();
        roomTagList.forEach(tag -> validTags.getItems()
                .add(tag.accessTag()));
    }

    private void setupEventhandlers() {
        createKeywordBtn.setOnMouseClicked(
                event -> {
                    TagPort keyword = this.context
                            .createKeyword(
                                    newKeywordField
                                            .getText());
                    keywordList.add(keyword);
                    keywords.getItems()
                            .add(keyword.accessTag());
                });
        createRoomTagBtn.setOnMouseClicked(
                event -> {
                    TagPort roomTag = this.context
                            .createValidTag(
                                    newTagField
                                            .getText());
                    roomTagList.add(roomTag);
                    validTags.getItems()
                            .add(roomTag.accessTag());
                });
        deleteKeywordBtn.setOnMouseClicked(
                event -> {
                    int index = keywords
                            .getSelectionModel()
                            .getSelectedIndex();
                    keywords.getItems().remove(index);
                    this.context.deleteKeyword(
                            keywordList.get(index));
                    keywordList.remove(index);
                    deleteKeywordBtn.setDisable(true);
                });
        deleteRoomTagBtn.setOnMouseClicked(
                event -> {
                    int index = validTags
                            .getSelectionModel()
                            .getSelectedIndex();
                    validTags.getItems().remove(index);
                    this.context.deleteValidTag(
                            roomTagList.get(index));
                    roomTagList.remove(index);
                    deleteKeywordBtn.setDisable(true);
                });

        keywords.onMouseClickedProperty()
                .setValue(event ->
                        deleteKeywordBtn.setDisable(false));
        validTags.onMouseClickedProperty()
                .setValue(event ->
                        deleteRoomTagBtn.setDisable(false));
    }
}
