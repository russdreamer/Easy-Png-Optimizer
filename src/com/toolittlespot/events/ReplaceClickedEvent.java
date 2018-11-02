package com.toolittlespot.events;

import com.toolittlespot.AppUtils;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.toolittlespot.Constants.DEFAULT_FILE_PATH;

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

        AppUtils.showSavedAllert();
        application.getButtons().getButtons().setDisable(false);
        application.getDraggableBox().enableDraggable();
    }
}
