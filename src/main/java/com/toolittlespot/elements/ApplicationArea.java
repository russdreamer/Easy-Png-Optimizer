package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.FileMap;
import main.java.com.toolittlespot.controller.Main;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.com.toolittlespot.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationArea {
    /* UI */
    private Main mainController;
    private Scene scene;
    private StackPane topPanel;
    private StackPane bottomPanel;
    private BorderPane root;
    private GridElement grid;
    private DraggableElement draggableBox;
    private ButtonGroupElement buttons;
    private MenuButtonElement languageButton;

    /* engine side */
    private FileMap fileMap;
    private List<FileElement> convertedFiles;
    private List<FileElement> unconvertedFiles;
    private List<Process> processList;
    private ExecutorService executorService;
    public static String userLanguage;

    {
        fileMap = new FileMap();
        processList = Collections.synchronizedList(new ArrayList<>());
        convertedFiles = Collections.synchronizedList(new ArrayList<>());
        unconvertedFiles = Collections.synchronizedList(new ArrayList<>());
    }

    public ApplicationArea(Main main) {
        this.mainController = main;
    }


    public StackPane getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(StackPane topPanel) {
        this.topPanel = topPanel;
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
        ScrollPane scrollPanel = new ScrollPane();
        scrollPanel.setContent(grid.getGrid());
        scrollPanel.setFitToWidth(true);
        topPanel.getChildren().addAll(scrollPanel, draggableBox.getDraggableField());
        bottomPanel.getChildren().addAll(buttons.getButtons(), languageButton.getMenuButton());
        root.setTop(topPanel);
        root.setBottom(bottomPanel);
        scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
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

    public List<Process> getProcessList() {
        return processList;
    }

    public ExecutorService getExecutorService() {
        if (executorService == null || executorService.isTerminated()) {
            return executorService = Executors.newWorkStealingPool();
        }
        else return executorService;
    }

    public List<FileElement> getConvertedFiles() {
        return convertedFiles;
    }

    public List<FileElement> getUnconvertedFiles() {
        return unconvertedFiles;
    }

    public MenuButtonElement getLanguageButton() {
        return languageButton;
    }

    public void setLanguageButton(MenuButtonElement languageButton) {
        this.languageButton = languageButton;
    }

    public void configurateLayouts() {
        double buttonsHeight = buttons.getCompress().getHeight();
        this.topPanel.setPrefHeight(Constants.SCENE_HEIGHT - buttonsHeight);

        /* app buttons positions */
        buttons.setAlignment();
    }

    public Main getMainController() {
        return mainController;
    }
}
