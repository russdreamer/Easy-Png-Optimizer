package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class LanguageClickedEvent implements EventHandler<ActionEvent> {
    private ApplicationArea application;;
    private final String languageName;

    public LanguageClickedEvent(ApplicationArea application, String languageName) {
        this.application = application;
        this.languageName = languageName;
    }

    @Override
    public void handle(ActionEvent event) {
        /* if no application progress - no need to show notification */
        if (application.getUnconvertedFiles().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType change = new ButtonType(LangMap.getDict(Dict.LANGUAGE_CHANGE_BUTTON));
            ButtonType cancel = new ButtonType(LangMap.getDict(Dict.LANGUAGE_CANCEL_BUTTON));
            alert.getButtonTypes().setAll(change, cancel);
            alert.setTitle(LangMap.getDict(Dict.LANGUAGE_ALERT_TITLE));
            alert.setHeaderText(LangMap.getDict(Dict.LANGUAGE_ALERT_HEADER));
            alert.setContentText(LangMap.getDict(Dict.LANGUAGE_ALERT_CONTEXT));
            alert.showAndWait();
            if (alert.getResult() == change){
                restartApp();
            }
        }
        else restartApp();

    }

    private void restartApp(){
        LangMap.changeLanguage(languageName);
        application.getMainController().restart(languageName);
    }
}
