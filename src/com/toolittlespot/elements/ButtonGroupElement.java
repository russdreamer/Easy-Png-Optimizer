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
        this.buttons.getChildren().addAll(convert, removeAll, stop, saveAs, replace);
        setDefaultButtonsState();
    }

    private void setButtonState(boolean convert, boolean removeAll, boolean saveButtons, boolean stop) {
        this.convert.setVisible(convert);
        this.removeAll.setVisible(removeAll);
        this.stop.setVisible(stop);
        this.replace.setVisible(saveButtons);
        this.saveAs.setVisible(saveButtons);
    }

    public void setDefaultButtonsState() {
        setButtonState(false, false, false, false);
    }

    public void setFileUploadedButtonsState() {
        setButtonState(true, true, false, false);
    }

    public void setConvertingButtonsState() {
        setButtonState(false, false, false, true);
    }

    public void setAllConvertedButtonsState() {
        setButtonState(false, true, true, false);
    }

    public void setPartlyConvertedButtonsState() {
        setButtonState(true, true, true, false);
    }

    public void setAlignment() {
        StackPane.setAlignment(convert, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(removeAll,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(stop, Pos.BOTTOM_RIGHT );
        StackPane.setAlignment(saveAs,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(replace,Pos.BOTTOM_RIGHT);
        double shift = 0;
        StackPane.setMargin(convert, new Insets(0, shift += removeAll.getWidth(),0 , 0) );
        StackPane.setMargin(replace, new Insets(0, shift += convert.getWidth(),0 , 0) );
        StackPane.setMargin(saveAs, new Insets(0, shift += replace.getWidth(), 0 , 0) );
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
