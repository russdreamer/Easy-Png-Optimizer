package main.java.com.toolittlespot.elements;

import javafx.scene.control.MenuButton;
import main.java.com.toolittlespot.utils.FileMap;
import main.java.com.toolittlespot.controller.Main;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.com.toolittlespot.utils.Constants;
import main.java.com.toolittlespot.utils.SystemOS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationArea {
    /* UI */
    private Main mainController;
    private Scene scene;
    private StackPane filesPanel;
    private StackPane bottomPanel;
    private GridElement grid;
    private DraggableElement draggableBox;
    private ButtonGroupElement buttons;
    private LanguageButtonElement languageButton;
    private MenuBarElement menuBar;

    /* engine side */
    private SystemOS systemOS;
    private FileMap fileMap;
    private List<ImageElement> optimizedFiles;
    private List<ImageElement> unoptimizedFiles;
    private List<Process> processList;
    private ExecutorService executorService;
    public static String userLanguage;

    {
        fileMap = new FileMap();
        processList = Collections.synchronizedList(new ArrayList<>());
        optimizedFiles = Collections.synchronizedList(new ArrayList<>());
        unoptimizedFiles = Collections.synchronizedList(new ArrayList<>());
    }

    public ApplicationArea(Main main, String language) {
        this.mainController = main;
        filesPanel = new StackPane();
        bottomPanel = new StackPane();
        userLanguage = language;
        draggableBox = new DraggableElement();
        buttons = new ButtonGroupElement(new StackPane());
        languageButton = new LanguageButtonElement(new MenuButton(), this);
        menuBar = new MenuBarElement(mainController);
        grid = new GridElement(4);
        grid.createGrid();
    }

    public void setFilesPanel(StackPane filesPanel) {
        this.filesPanel = filesPanel;
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

    public void setBottomPanel(StackPane bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    /**
     * build javaFX application from set elements
     */
    public void build(){
        BorderPane root = new BorderPane();
        ScrollPane scrollPanel = new ScrollPane();
        scrollPanel.setContent(grid.getGrid());
        scrollPanel.setFitToWidth(true);
        filesPanel.getChildren().addAll( scrollPanel, draggableBox.getDraggableField());
        bottomPanel.getChildren().addAll(buttons.getButtons(), languageButton.getMenuButton());

        BorderPane topPanel = new BorderPane();
        topPanel.setTop(menuBar.getMenuBar());
        topPanel.setBottom(filesPanel);

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

    public List<Process> getProcessList() {
        return processList;
    }

    public ExecutorService getExecutorService() {
        if (executorService == null || executorService.isTerminated()) {
            return executorService = Executors.newWorkStealingPool();
        }
        else return executorService;
    }

    public List<ImageElement> getOptimizedFiles() {
        return optimizedFiles;
    }

    public List<ImageElement> getUnoptimizedFiles() {
        return unoptimizedFiles;
    }

    public LanguageButtonElement getLanguageButton() {
        return languageButton;
    }

    public void setLanguageButton(LanguageButtonElement languageButton) {
        this.languageButton = languageButton;
    }

    /**
     * set alignment and elements' sizes after application started
     */
    public void configureLayouts() {
        double buttonsHeight = buttons.getCompress().getHeight();
        double menuHeight = menuBar.getMenuBar().getHeight();
        this.filesPanel.setPrefHeight(Constants.SCENE_HEIGHT - buttonsHeight - menuHeight);

        buttons.setAlignment();
    }

    public Main getMainController() {
        return mainController;
    }

    public SystemOS getSystemOS() {
        return systemOS;
    }

    public void setSystemOS(SystemOS systemOS) {
        this.systemOS = systemOS;
    }

    public void setMenuBar(MenuBarElement menuBar) {
        this.menuBar = menuBar;
    }

    public MenuBarElement getMenuBar() {
        return menuBar;
    }
}
