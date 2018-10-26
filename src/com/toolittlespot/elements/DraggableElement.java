package com.toolittlespot.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static com.toolittlespot.Constants.NOT_DRAGGED;

public class DraggableElement {
    private VBox draggableField;
    private Label dragLabel;

    {
       dragLabel = new Label(NOT_DRAGGED);
    }

    public DraggableElement() {
        draggableField = new VBox();
        draggableField.getChildren().addAll(dragLabel);
        draggableField.setAlignment(Pos.CENTER);
        draggableField.setMaxSize(440,220 );
    }

    public VBox getDraggableField() {
        return draggableField;
    }

    public void disableDraggable(){
        draggableField.setDisable(true);
        draggableField.setVisible(false);
    }

    public Label getDragLabel() {
        return dragLabel;
    }
}
