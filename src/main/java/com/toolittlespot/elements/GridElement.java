package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import main.java.com.toolittlespot.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class GridElement {
    private GridPane grid;
    private RowElement headerRow;
    private List<RowElement> fileRows;
    private int columnNum;
    private int headerElNum;

    public GridElement(int columnNum) {
        this.columnNum = columnNum;
    }


    /**
     * creating a grid for uploaded files
     * @return base grid with the only header row
     */
    public GridPane createGrid(){
        grid = new GridPane();
        grid.setOnMouseClicked(event -> {
            System.out.println(event.getEventType().getName());
            System.out.println(event.getSource());
            System.out.println(event.getTarget());

        });
        grid.setGridLinesVisible(true);

        for (int i = 0; i < columnNum; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / columnNum);
            grid.getColumnConstraints().add(colConst);
        }

        createHeaderRow();
        fileRows = new ArrayList<>();
        return grid;
    }

    private RowElement createHeaderRow() {
        List<Label> labels = LabelElement.createLabels(
                LangMap.getDict(Dict.NAME_ROW)
                , LangMap.getDict(Dict.BEFORE_ROW)
                , LangMap.getDict(Dict.AFTER_ROW)
                , LangMap.getDict(Dict.REDUCE_ROW));
        headerRow = createRowFromLabels(labels, 0, Constants.HEADER_BG_STYLE);
        setHeaderElNum(grid.getChildren().size());
        return headerRow;
    }

    public RowElement createRowFromLabels(List<Label> regions, int rowIndex, String paneStyle){
        RowElement row = new RowElement();

        for (int i = 0; i < columnNum; i++){
            Pane pane = new Pane();
            pane.setStyle(paneStyle);

            row.setCellFromIndex(i, new CellElement(pane, regions.get(i)));
            grid.add(pane, i, rowIndex);
            grid.add(regions.get(i), i, rowIndex);
            GridPane.setHalignment(regions.get(i), HPos.CENTER);
        }
        return row;
    }

    public void clearGrid(){
        getFileRows().clear();
    }

    public GridPane getGrid() {
        return grid;
    }

    public List<RowElement> getFileRows() {
        return fileRows;
    }

    public void setFileRows(List<RowElement> fileRows) {
        this.fileRows = fileRows;
    }

    public int getHeaderElNum() {
        return headerElNum;
    }

    public void setHeaderElNum(int headerElNum) {
        this.headerElNum = headerElNum;
    }

}
