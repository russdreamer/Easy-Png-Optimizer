package com.toolittlespot.events;

import com.toolittlespot.convert.ConvertFileManager;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Collection;

public class ConvertClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public ConvertClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getButtons().setConvertingButtonsState();
        Collection<FileElement> files = application.getFileMap().getFiles();
        ConvertFileManager manager = new ConvertFileManager(application, files);
        manager.start();
    }
}
