package com.toolittlespot.events;

import com.toolittlespot.controller.Main;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.language.Dict;
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
        ButtonType change = new ButtonType(LangMap.getDict(Dict.LANGUAGE_CHANGE_BUTTON));
        ButtonType cancel = new ButtonType(LangMap.getDict(Dict.LANGUAGE_CANCEL_BUTTON));
        alert.getButtonTypes().setAll(change, cancel);
        alert.setTitle(LangMap.getDict(Dict.LANGUAGE_ALERT_TITLE));
        alert.setHeaderText(LangMap.getDict(Dict.LANGUAGE_ALERT_HEADER));
        alert.setContentText(LangMap.getDict(Dict.LANGUAGE_ALERT_CONTEXT));
        alert.showAndWait();
        if (alert.getResult() == change){
            LangMap.changeLanguage(languageName);
            mainController.restart(languageName);
        }
    }
}
