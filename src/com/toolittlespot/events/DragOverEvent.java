package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import static com.toolittlespot.Constants.DRAGGED;
import static com.toolittlespot.Constants.DRAGGED_WINDOW_STYLE;

public class DragOverEvent implements EventHandler<DragEvent> {

    private final ApplicationArea application;

    public DragOverEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(DragEvent event) {
        VBox dragTarget = application.getDraggableBox().getDraggableField();
        if (event.getGestureSource() != dragTarget
                && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.LINK);
            application.getDraggableBox().getDragLabel().setText(DRAGGED);
            application.getDraggableBox().getDraggableField().setStyle(DRAGGED_WINDOW_STYLE);
        }
        event.consume();
    }
}