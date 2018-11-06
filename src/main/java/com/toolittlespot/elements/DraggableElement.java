package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DraggableElement {
    private VBox draggableField;
    private Label dragLabel;

    {
       dragLabel = new Label(LangMap.getDict(Dict.NOT_DRAGGED));
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

    public void enableDraggable(){
        draggableField.setDisable(false);
        draggableField.setVisible(true);
    }

    public Label getDragLabel() {
        return dragLabel;
    }
}
