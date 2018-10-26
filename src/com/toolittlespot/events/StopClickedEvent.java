package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class StopClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public StopClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getButtons().setConvertingButtonsState();
    }
}
