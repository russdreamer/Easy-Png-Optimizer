package com.toolittlespot.elements;

import com.toolittlespot.language.Dict;
import com.toolittlespot.language.LangMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ButtonGroupElement {
    private StackPane buttons;
    private Button compress;
    private Button clearAll;
    private Button replace;
    private Button saveAs;
    private Button stop;

    public ButtonGroupElement(StackPane buttons) {
        this.buttons = buttons;
        compress = new Button(LangMap.getDict(Dict.COMPRESS_BUTTON));
        clearAll = new Button(LangMap.getDict(Dict.CLEAR_ALL_BUTTON));
        stop = new Button(LangMap.getDict(Dict.STOP_BUTTON));
        replace = new Button(LangMap.getDict(Dict.REPLACE_BUTTON));
        saveAs = new Button(LangMap.getDict(Dict.SAVE_AS_BUTTON));
        this.buttons.getChildren().addAll(compress, clearAll, stop, saveAs, replace);
        setDefaultButtonsState();
    }

    private void setButtonState(boolean convert, boolean removeAll, boolean saveButtons, boolean stop) {
        this.compress.setVisible(convert);
        this.clearAll.setVisible(removeAll);
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
        StackPane.setAlignment(compress, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(clearAll,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(stop, Pos.BOTTOM_RIGHT );
        StackPane.setAlignment(saveAs,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(replace,Pos.BOTTOM_RIGHT);
        double shift = 0;
        StackPane.setMargin(compress, new Insets(0, shift += clearAll.getWidth(),0 , 0) );
        StackPane.setMargin(replace, new Insets(0, shift += compress.getWidth(),0 , 0) );
        StackPane.setMargin(saveAs, new Insets(0, shift += replace.getWidth(), 0 , 0) );
    }

    public StackPane getButtons() {
        return buttons;
    }

    public Button getCompress() {
        return compress;
    }

    public Button getClearAll() {
        return clearAll;
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
