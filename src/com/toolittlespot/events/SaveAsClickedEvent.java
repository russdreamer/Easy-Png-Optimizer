package com.toolittlespot.events;

import com.toolittlespot.AppUtils;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.toolittlespot.Constants.DEFAULT_FILE_PATH;

public class SaveAsClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;
    private final Logger LOGGER = Logger.getLogger(SaveAsClickedEvent.class);

    public SaveAsClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getDraggableBox().disableDraggable();
        application.getButtons().getButtons().setDisable(true);
        application.getLanguageButton().getMenuButton().setDisable(true);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        /* can be fatal errors due to JDK bug https://bugs.openjdk.java.net/browse/JDK-8211137 */
        File dir = directoryChooser.showDialog(application.getScene().getWindow());
        if (dir != null){
            try {
                saveFilesToDir(application.getConvertedFiles(), dir);
                AppUtils.showSavedAllert();
            } catch (IOException e) {
                LOGGER.error("wrong destination path: " + dir.toPath());
            }
        }
        application.getDraggableBox().enableDraggable();
        application.getButtons().getButtons().setDisable(false);
        application.getLanguageButton().getMenuButton().setDisable(true);
    }

    private void saveFilesToDir(List<FileElement> fileList, File dir) throws IOException {
        for (FileElement file: fileList) {
            Path fileSource = Paths.get(DEFAULT_FILE_PATH + file.getFileNameToSave());
            Path destFile = Paths.get(dir.getAbsolutePath() + "/" + file.getFileNameToSave());
            Files.copy(fileSource, destFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
