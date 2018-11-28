package main.java.com.toolittlespot.controller;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.toolittlespot.elements.*;
import main.java.com.toolittlespot.events.*;
import main.java.com.toolittlespot.utils.AppUtils;
import main.java.com.toolittlespot.utils.SystemOS;

import static main.java.com.toolittlespot.utils.Constants.USER_LANGUAGE;


public class Main extends Application {

    private Stage primaryStage;
    private ApplicationArea application;

    @Override
    public void start(Stage primaryStage) {
        AppUtils.loadStateFile();
        this.primaryStage = primaryStage;
        createApplication(primaryStage, (String) AppUtils.userState.get(USER_LANGUAGE));
    }

    /**
     * restart whole application with chosen language
     * @param language language to use as default
     */
    public void restart(String language) {
        createApplication(primaryStage, language);
    }

    private void createApplication(Stage primaryStage, String language) {
        application = new ApplicationArea(this, language);

        application.setSystemOS(AppUtils.getSystemOS());
        setAppIconIfMacOS();
        AppUtils.createTempFiles();
        setEvents();
        application.build();

        primaryStage.setTitle("EASY.png");
        primaryStage.setScene(application.getScene());
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest((e)-> AppUtils.saveState() );
        primaryStage.show();

        application.configureLayouts();
        AppUtils.runUpdater(application);
    }

    /**
     * set general gui events
     */
    private void setEvents() {
        VBox dragTarget = application.getDraggableBox().getDraggableField();
        dragTarget.setOnDragOver(new DragOverEvent(application));
        dragTarget.setOnDragDropped(new DragDroppedEvent(application));
        dragTarget.setOnDragExited(new DragExitedEvent(application));

        ButtonGroupElement buttons = application.getButtons();
        buttons.getCompress().setOnMouseClicked(new ConvertClickedEvent(application));
        buttons.getClearAll().setOnMouseClicked(new RemoveAllClickedEvent(application));
        buttons.getReplace().setOnMouseClicked(new ReplaceClickedEvent(application));
        buttons.getSaveAs().setOnMouseClicked(new SaveAsClickedEvent(application));
        buttons.getStop().setOnMouseClicked(new StopClickedEvent(application));
    }

    private void setAppIconIfMacOS() {
        if (application.getSystemOS() == SystemOS.MAC) {
            AppUtils.setAppIcon();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}
