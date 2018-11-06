package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.AppUtils;
import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static main.java.com.toolittlespot.Constants.DEFAULT_FILE_PATH;

public class ReplaceClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public ReplaceClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getLanguageButton().getMenuButton().setDisable(true);
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
        application.getLanguageButton().getMenuButton().setDisable(false);
        application.getDraggableBox().enableDraggable();
    }
}
