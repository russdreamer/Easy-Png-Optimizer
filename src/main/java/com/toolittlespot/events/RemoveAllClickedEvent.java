package main.java.com.toolittlespot.events;

import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class RemoveAllClickedEvent implements EventHandler<MouseEvent> {
    private final ApplicationArea application;

    public RemoveAllClickedEvent(ApplicationArea application) {
        this.application = application;
    }

    @Override
    public void handle(MouseEvent event) {
        /* clear rows from our application */
        application.getGrid().clearGrid();
        application.getFileMap().clear();
        application.getConvertedFiles().clear();
        application.getUnconvertedFiles().clear();

        /* clear rows from FX's list */
        ObservableList<Node> gridCells = application.getGrid().getGrid().getChildren();
        gridCells.remove(application.getGrid().getHeaderElNum(), gridCells.size());

        /* set default view */
        application.getDraggableBox().getDragLabel().setText(LangMap.getDict(Dict.NOT_DRAGGED));
        application.getButtons().setDefaultButtonsState();
    }

}
