package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import static com.toolittlespot.Constants.NOT_DRAGGED;

public class RemoveAllClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public RemoveAllClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        /* clear rows from our application */
        application.getGrid().clearGrid();
        application.getFileMap().clear();

        /* clear rows from FX's list */
        ObservableList<Node> gridCells = application.getGrid().getGrid().getChildren();
        gridCells.remove(application.getGrid().getHeaderElNum(), gridCells.size());

        /* set default view */
        application.getDraggableBox().getDragLabel().setText(NOT_DRAGGED);
        application.getButtons().setDefaultButtonsState();
    }

}
