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
    private List<TagPort> validTagList;


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

        validTagList = new ArrayList<>(
                context.accessValidTags());
        validTagList.sort(Comparator
                .comparing(TagPort::accessTag));
        validTags.getItems().clear();
        validTagList.forEach(tag -> validTags.getItems()
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
                    validTagList.add(roomTag);
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
                            validTagList.get(index));
                    validTagList.remove(index);
                    validTags.getSelectionModel()
                            .clearSelection();
                    deleteRoomTagBtn.setDisable(true);
                });
        validTags.onMouseClickedProperty()
                .setValue(event -> {
                    if (isItemSelected(validTags)) {
                        deleteRoomTagBtn.setDisable(false);
                    }
                });
        keywords.onMouseClickedProperty()
                .setValue(event -> {
                    if (isItemSelected(keywords)) {
                        deleteKeywordBtn.setDisable(false);
                    }
                });

    }

    private boolean isItemSelected(ListView listView) {
        return listView.getSelectionModel()
                .getSelectedIndex() != -1;
    }
}
