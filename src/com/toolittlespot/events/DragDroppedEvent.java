package com.toolittlespot.events;

import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

import java.io.File;
import java.util.List;

public class DragDroppedEvent implements EventHandler<DragEvent> {

    private final ApplicationArea application;

    public DragDroppedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            List<File> files = event.getDragboard().getFiles();
            if (FileElement.downloadFiles(files, application)){
                application.getButtons().setDownloadedButtonsState();
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
