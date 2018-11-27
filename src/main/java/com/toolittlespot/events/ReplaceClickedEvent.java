package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.elements.ImageElement;
import main.java.com.toolittlespot.utils.AppUtils;
import main.java.com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
        List<ImageElement> files = application.getOptimizedFiles();

        for (ImageElement file: files) {
            String fileFrom = file.getTempFilePath();

            try {
                Files.copy(Paths.get(fileFrom), file.getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AppUtils.showSavedAlert();
        application.getButtons().getButtons().setDisable(false);
        application.getLanguageButton().getMenuButton().setDisable(false);
        application.getDraggableBox().enableDraggable();
    }
}
