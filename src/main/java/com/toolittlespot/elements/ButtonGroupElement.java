package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
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

    /**
     * setting buttons' visibility and availability
     * @param convert convert button
     * @param clearAll clearAll button
     * @param saveButtons replace and saveAs buttons
     * @param stop stop button
     */
    private void setButtonState(boolean convert, boolean clearAll, boolean saveButtons, boolean stop) {
        this.compress.setVisible(convert);
        this.clearAll.setVisible(clearAll);
        this.stop.setVisible(stop);
        this.replace.setVisible(saveButtons);
        this.saveAs.setVisible(saveButtons);
    }

    /**
     * app state at the beginning
     */
    public void setDefaultButtonsState() {
        setButtonState(false, false, false, false);
    }

    /**
     * app state when 1 or more files uploaded
     */
    public void setFileUploadedButtonsState() {
        setButtonState(true, true, false, false);
    }

    /**
     * app state when app in in optimizing process
     */
    public void setConvertingButtonsState() {
        setButtonState(false, false, false, true);
    }

    /**
     * app state when all files optimized successful
     */
    public void setAllOptimizedButtonsState() {
        setButtonState(false, true, true, false);
    }

    /**
     * app state if 1 or move files optimized but not all of them
     */
    public void setPartlyConvertedButtonsState() {
        setButtonState(true, true, true, false);
    }

    /**
     * setting buttons' alignment
     */
    void setAlignment() {
        StackPane.setAlignment(compress, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(clearAll,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(stop, Pos.BOTTOM_RIGHT );
        StackPane.setAlignment(saveAs,Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(replace,Pos.BOTTOM_RIGHT);
        double shift = 0;
        StackPane.setMargin(compress, new Insets(0, shift += clearAll.getWidth(),0 , 0) );
        StackPane.setMargin(replace, new Insets(0, shift += compress.getWidth(),0 , 0) );
        StackPane.setMargin(saveAs, new Insets(0, shift + replace.getWidth(), 0 , 0) );
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
