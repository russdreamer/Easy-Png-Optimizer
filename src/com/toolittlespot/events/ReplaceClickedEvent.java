package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ReplaceClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public ReplaceClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
