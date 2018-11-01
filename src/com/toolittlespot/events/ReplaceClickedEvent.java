package com.toolittlespot.events;

import com.toolittlespot.AppUtils;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.toolittlespot.Constants.*;

public class ReplaceClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public ReplaceClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getButtons().getButtons().setDisable(true);
        application.getDraggableBox().disableDraggable();
        List<FileElement> files = application.getConvertedFiles();

        for (FileElement file: files) {
            String fileFrom = DEFAULT_FILE_PATH + file.getFileNameToSave();

            try {
                Files.copy(Paths.get(fileFrom), file.getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ALERT_TITLE);
        alert.setHeaderText(ALERT_HEADER);
        alert.setContentText(ALERT_CONTEXT);

        /* better to use showAndWait but due to jdk bug we use show() https://bugs.openjdk.java.net/browse/JDK-8211137 */
        alert.show();

        application.getButtons().getButtons().setDisable(false);
        application.getDraggableBox().enableDraggable();
        AppUtils.setButtonState(application);
    }
}
