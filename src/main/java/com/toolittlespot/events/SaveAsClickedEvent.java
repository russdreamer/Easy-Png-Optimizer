package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.utils.AppUtils;
import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class SaveAsClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public SaveAsClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getDraggableBox().disableDraggable();
        application.getButtons().getButtons().setDisable(true);
        application.getLanguageButton().getMenuButton().setDisable(true);

        DirectoryChooser directoryChooser = new DirectoryChooser();

        File dir = directoryChooser.showDialog(application.getScene().getWindow());
        if (dir != null){
            try {
                saveFilesToDir(application.getOptimizedFiles(), dir);
                AppUtils.showSavedAlert();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        application.getDraggableBox().enableDraggable();
        application.getButtons().getButtons().setDisable(false);
        application.getLanguageButton().getMenuButton().setDisable(false);
    }

    /**
     * saving all optimised files to the chosen directory
     * @param fileList files to save
     * @param dir directory to save
     * @throws IOException if optimized file is removed
     */
    private void saveFilesToDir(List<FileElement> fileList, File dir) throws IOException {
        for (FileElement file: fileList) {
            Path fileSource = Paths.get(file.getTempFilePath());
            Path destFile = Paths.get(dir.getAbsolutePath() + File.separator + file.getFileNameToSave());
            Files.copy(fileSource, destFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
