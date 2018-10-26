package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SaveAsClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public SaveAsClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
