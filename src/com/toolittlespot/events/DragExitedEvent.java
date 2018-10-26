package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import static com.toolittlespot.Constants.NOT_DRAGGED;

public class DragExitedEvent implements EventHandler<DragEvent> {

    private final ApplicationArea application;

    public DragExitedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(DragEvent event) {
        VBox dragTarget = application.getDraggableBox().getDraggableField();
        if (event.getGestureSource() != dragTarget
                && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.LINK);
            if (application.getGrid().getFileRows().size() > 0) {
                application.getDraggableBox().getDragLabel().setText(null);
            }
            else application.getDraggableBox().getDragLabel().setText(NOT_DRAGGED);

            application.getDraggableBox().getDraggableField().setStyle(null);
        }
        event.consume();
    }
}
