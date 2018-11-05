package com.toolittlespot.events;

import com.toolittlespot.controller.Main;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.language.LangMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class LanguageClickedEvent implements EventHandler<ActionEvent> {
    private final Main mainController;
    private final String languageName;

    public LanguageClickedEvent(Main main, String languageName) {
        this.mainController = main;
        this.languageName = languageName;
    }

    @Override
    public void handle(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Language changing");
        alert.setHeaderText("Current progress will be lost");
        alert.setContentText("Click OK to continue. To cancel, click CANCEL");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK){
            LangMap.changeLanguage(languageName);
            mainController.restart(languageName);
        }
    }
}
