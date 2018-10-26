package com.toolittlespot.controller;

import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.ButtonGroupElement;
import com.toolittlespot.elements.DraggableElement;
import com.toolittlespot.elements.GridElement;
import com.toolittlespot.events.*;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    public static Runtime runtime;

    @Override
    public void start(Stage primaryStage) {
        createApplication(primaryStage);
    }

    private void createApplication(Stage primaryStage) {
        ApplicationArea application = new ApplicationArea();
        application.setTopPanel(new StackPane());


        GridElement gridElement = new GridElement(4);
        gridElement.createGrid();
        application.setGrid(gridElement);

        DraggableElement dragElement = new DraggableElement();
        application.setDraggableBox(dragElement);

        ButtonGroupElement buttons = new ButtonGroupElement(new StackPane());
        application.setButtons(buttons);

        /* get all necessary nodes for events setting */
        VBox dragTarget = dragElement.getDraggableField();
        Button convertButton = buttons.getConvert();
        Button removeAllButton = buttons.getRemoveAll();
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}
