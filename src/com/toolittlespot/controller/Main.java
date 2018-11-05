package com.toolittlespot.controller;

import com.toolittlespot.elements.*;
import com.toolittlespot.events.*;
import com.toolittlespot.language.LangMap;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        String language = Locale.getDefault().getDisplayLanguage();
        createApplication(primaryStage, language);
    }

    private void createApplication(Stage primaryStage, String language) {
        ApplicationArea application = new ApplicationArea();
        application.userLanguage = language;
        application.setTopPanel(new StackPane());
        application.setBottomPanel(new StackPane());

        GridElement gridElement = new GridElement(4);
        gridElement.createGrid();
        application.setGrid(gridElement);

        DraggableElement dragElement = new DraggableElement();
        application.setDraggableBox(dragElement);

        ButtonGroupElement buttons = new ButtonGroupElement(new StackPane());
        application.setButtons(buttons);

        MenuButtonElement languageButton = new MenuButtonElement(new MenuButton());
        application.setLanguageButton(languageButton);

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

        primaryStage.setTitle("Easy png optimizer");
        primaryStage.setScene(application.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
        application.configurateLayouts();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}
