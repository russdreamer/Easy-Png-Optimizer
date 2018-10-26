package com.toolittlespot.elements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ButtonGroupElement {
    private StackPane buttons;
    private Button convert;
    private Button removeAll;
    private Button replace;
    private Button saveAs;
    private Button stop;

    public ButtonGroupElement(StackPane buttons) {
        this.buttons = buttons;
        convert = new Button("convert");
        removeAll = new Button("remove all");
        stop = new Button("stop");
        replace = new Button("replace files");
        saveAs = new Button("save as...");
        setAlignment();
        this.buttons.getChildren().addAll(convert, removeAll, stop, saveAs, replace);
        setDefaultButtonsState();
    }

    private void setButtonState(boolean group, boolean subGroup1, boolean subGroup2, boolean subGroup3) {
        buttons.setDisable(group);
        convert.setVisible(subGroup1);
        removeAll.setVisible(subGroup1);
        stop.setVisible(subGroup2);
        replace.setVisible(subGroup3);
        saveAs.setVisible(subGroup3);
    }

    public void setDefaultButtonsState() {
        setButtonState(true, false, false, false);
    }

    public void setDownloadedButtonsState() {
        setButtonState(false, true, false, false);
    }

    public void setConvertingButtonsState() {
        setButtonState(false, false, true, false);
    }

    public void setConvertedButtonsState() {
        setButtonState(false, false, false, true);
    }

    private void setAlignment() {
        StackPane.setAlignment(convert, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(removeAll,Pos.BOTTOM_RIGHT);
        StackPane.setMargin(convert, new Insets(0, 90,0 , 0) );
        StackPane.setAlignment(stop, Pos.BOTTOM_RIGHT );
        StackPane.setAlignment(saveAs,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(replace,Pos.BOTTOM_RIGHT);
        StackPane.setMargin(saveAs, new Insets(0, 100,0 , 0) );
    }

    public StackPane getButtons() {
        return buttons;
    }

    public Button getConvert() {
        return convert;
    }

    public Button getRemoveAll() {
        return removeAll;
    }

    public Button getReplace() {
        return replace;
    }

    public Button getSaveAs() {
        return saveAs;
    }

    public Button getStop() {
        return stop;
    }
}
