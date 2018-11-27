package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.convert.ConvertFileManager;
import main.java.com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.java.com.toolittlespot.elements.ImageElement;

import java.util.Collection;

public class ConvertClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public ConvertClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getDraggableBox().disableDraggable();
        application.getButtons().setConvertingButtonsState();
        application.getLanguageButton().getMenuButton().setDisable(true);
        Collection<ImageElement> files = application.getUnoptimizedFiles();
        ConvertFileManager manager = new ConvertFileManager(application, files);
        manager.start();
    }
}
