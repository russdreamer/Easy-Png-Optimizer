package com.toolittlespot.elements;


import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CellElement {
    private Pane background;
    private Label text;

    public CellElement(Pane background, Label text) {
        this.background = background;
        this.text = text;
    }

    public Pane getBackground() {
        return background;
    }

    public void setBackground(Pane background) {
        this.background = background;
    }

    public String getText() {
        return text.getText();
    }

    public boolean setText(String newText) {
        this.text.setText(newText);
        return true;
    }
}
