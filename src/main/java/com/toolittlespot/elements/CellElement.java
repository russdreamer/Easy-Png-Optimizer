package main.java.com.toolittlespot.elements;


import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

class CellElement {
    private Pane background;
    private Label text;

    CellElement(Pane background, Label text) {
        this.background = background;
        this.text = text;
    }

    Pane getBackground() {
        return background;
    }

    void setText(String newText) {
        this.text.setText(newText);
    }
}
