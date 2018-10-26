package com.toolittlespot.elements;

import com.toolittlespot.FileMap;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import static com.toolittlespot.Constants.*;

public class ApplicationArea {
    private Scene scene;
    private StackPane topPanel;
    private StackPane bottomPanel;
    private BorderPane root;
    private GridElement grid;
    private DraggableElement draggableBox;
    private ButtonGroupElement buttons;
    private FileMap fileMap;

    {
        fileMap = new FileMap();
    }


    public StackPane getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(StackPane topPanel) {
        this.topPanel = topPanel;
        this.topPanel.setPrefHeight(SCENE_HEIGHT - 35);
    }

    public GridElement getGrid() {
        return grid;
    }

    public void setGrid(GridElement grid) {
        this.grid = grid;
    }

    public DraggableElement getDraggableBox() {
        return draggableBox;
    }

    public void setDraggableBox(DraggableElement draggableBox) {
        this.draggableBox = draggableBox;
    }

    public ButtonGroupElement getButtons() {
        return buttons;
    }

    public void setButtons(ButtonGroupElement buttons) {
        this.buttons = buttons;
    }

    public BorderPane getRoot() {
        return root;
    }

    public StackPane getBottomPanel() {
        return bottomPanel;
    }

    public void setBottomPanel(StackPane bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    public void build(){
        root = new BorderPane();
        topPanel.getChildren().addAll(grid.getGrid(), draggableBox.getDraggableField());
        setBottomPanel(buttons.getButtons());
        root.setTop(bottomPanel);
        root.setBottom(topPanel);
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }

    public Scene getScene() {
        return scene;
    }

    public FileMap getFileMap() {
        return fileMap;
    }

    public void setFileMap(FileMap fileMap) {
        this.fileMap = fileMap;
    }
}
