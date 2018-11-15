package main.java.com.toolittlespot.controller;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.toolittlespot.elements.*;
import main.java.com.toolittlespot.events.*;
import main.java.com.toolittlespot.utils.AppUtils;
import main.java.com.toolittlespot.utils.SystemOS;

import java.util.Locale;


public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        String language = Locale.getDefault().getDisplayLanguage();
        createApplication(primaryStage, language);
    }

    public void restart(String language) {
        createApplication(primaryStage, language);
    }

    private void createApplication(Stage primaryStage, String language) {
        ApplicationArea application = new ApplicationArea(this);

        application.setSystemOS(AppUtils.getSystemOS());
        if (application.getSystemOS() == SystemOS.MAC) {
            AppUtils.setAppIcon();
        }
        AppUtils.createTempFiles();

        application.userLanguage = language;
        application.setFilesPanel(new StackPane());
        application.setBottomPanel(new StackPane());

        GridElement gridElement = new GridElement(4);
        gridElement.createGrid();
        application.setGrid(gridElement);

        DraggableElement dragElement = new DraggableElement();
        application.setDraggableBox(dragElement);

        ButtonGroupElement buttons = new ButtonGroupElement(new StackPane());
        application.setButtons(buttons);

        MenuButtonElement languageButton = new MenuButtonElement(new MenuButton(), application);
        application.setLanguageButton(languageButton);

        application.setMenuBar(new MenuBarElement(this));

        /* get all necessary nodes for events setting */
        VBox dragTarget = dragElement.getDraggableField();
        Button convertButton = buttons.getCompress();
        Button removeAllButton = buttons.getClearAll();
        Button replaceButton = buttons.getReplace();
        Button saveAsButton = buttons.getSaveAs();
        Button stopButton = buttons.getStop();

        /* set all application events */
        dragTarget.setOnDragOver(new DragOverEvent(application));
        dragTarget.setOnDragDropped(new DragDroppedEvent(application));
        dragTarget.setOnDragExited(new DragExitedEvent(application));
        convertButton.setOnMouseClicked(new ConvertClickedEvent(application));
        removeAllButton.setOnMouseClicked(new RemoveAllClickedEvent(application));
        replaceButton.setOnMouseClicked(new ReplaceClickedEvent(application));
        saveAsButton.setOnMouseClicked(new SaveAsClickedEvent(application));
        stopButton.setOnMouseClicked(new StopClickedEvent(application));

        application.build();

        primaryStage.setTitle("EASY.png");
        primaryStage.setScene(application.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
        application.configurateLayouts();

        if (! AppUtils.isCurrentVersionLast()){
            application.getMenuBar().getUpdate().fire();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}
