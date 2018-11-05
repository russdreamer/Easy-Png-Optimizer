package com.toolittlespot.events;

import com.toolittlespot.controller.Main;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.language.LangMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
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
        LangMap.changeLanguage(languageName);
        mainController.restart(languageName);
    }
}
