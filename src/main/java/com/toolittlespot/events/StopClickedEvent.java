package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.elements.ApplicationArea;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class StopClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public StopClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        application.getButtons().getButtons().setDisable(true);

        /* stop all convert threads */
        ExecutorService executor = application.getExecutorService();
        executor.shutdownNow();

        /* stop all processes inside of threads */
        List<Process> list = application.getProcessList();
        list.forEach(Process::destroyForcibly);
    }
}
