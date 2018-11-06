package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class StopClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public StopClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        /* stop all convert threads */
        application.getExecutorService().shutdownNow();

        /* stop all processes inside of threads */
        List<Process> list = application.getProcessList();
        for (Process process: list) {
            process.destroyForcibly();
        }
    }
}
