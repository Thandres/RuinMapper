package ruinMapper.hexagon.application.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ruinMapper.hexagon.application.DomainAdapter;
import ruinMapper.hexagon.application.ui.area.AreaView;
import ruinMapper.hexagon.application.ui.hint.HintView;
import ruinMapper.hexagon.application.ui.quest.QuestView;
import ruinMapper.hexagon.application.ui.tag.TagView;
import ruinMapper.hexagon.domain.context.ContextPort;

import java.io.File;


public class ContextView extends Application {
    @FXML
    VBox mainWindow;

    @FXML
    TabPane tabs;

    @FXML
    HBox startupOptions;

    Tab areaTab;


    Tab questTab;


    Tab hintTab;

    Tab tagTab;

    private ContextPort context;

    private DomainAdapter domainAdapter;

    public void loadMap(MouseEvent mouseEvent) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        Stage window = new Stage();

//        File file = fileChooser.showDialog(window);
//        domainAdapter = new DomainAdapter(
//                file.getAbsolutePath());
//        domainAdapter
//                .createNewContext(file.getName());

        domainAdapter = new DomainAdapter(
                "D:\\Repos\\map");
        if (new File("D:\\Repos\\map\\Map.json").exists()) {
            context = domainAdapter
                    .loadContextByName("Map");
        } else {

            context = domainAdapter.createNewContext("Map");
        }
        if (mainWindow.getChildren()
                .contains(startupOptions)) {
            mainWindow.getChildren().remove(startupOptions);
        } else {
            // cleanup of other context without too much fuss
            tabs.getTabs()
                    .removeAll(areaTab, hintTab, questTab,
                            tagTab);
        }
        // create new Tabs instead of setting content on predefined Tabs because the first
        // Visible Tab wouldnt have any content before changing the active Tab to another
        areaTab = new Tab("Areas", new AreaView());
        hintTab = new Tab("Hints", new HintView(context));
        questTab = new Tab("Quests",
                new QuestView(context));
        tagTab = new Tab("Tags", new TagView(context));
        tabs.getTabs()
                .addAll(areaTab, hintTab, questTab, tagTab);
        tabs.setVisible(true);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader
                .load(getClass().getResource(
                        "ContextView.fxml"));
        primaryStage.setTitle("RuinMapper");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public void loadUI(String... args) {
        launch(args);
    }
}
