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
    private List<RowElement> fileRows;
    private int columnNum;
    private int headerElNum;

    public GridElement(int columnNum) {
        this.columnNum = columnNum;
    }


    /**
     * creating a grid for uploaded files
     */
    public void createGrid(){
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
    }

    /**
     * creating and filling up one row of grid which is a grid header
     */
    private void createHeaderRow() {
        List<Label> labels = LabelElement.createLabels(
                LangMap.getDict(Dict.NAME_ROW)
                , LangMap.getDict(Dict.BEFORE_ROW)
                , LangMap.getDict(Dict.AFTER_ROW)
                , LangMap.getDict(Dict.REDUCE_ROW));
        createRowFromLabels(labels, 0, Constants.HEADER_BG_STYLE);
        setHeaderElNum(grid.getChildren().size());
    }

    /**
     * creating a row
     * @param regions list of fields (columns) for the row
     * @param rowIndex index of row to create
     * @param paneStyle color style of the row
     * @return created row element
     */
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

    /**
     * clear whole table except the header row
     */
    public void clearGrid(){
        getFileRows().clear();
    }

    public GridPane getGrid() {
        return grid;
    }

    public List<RowElement> getFileRows() {
        return fileRows;
    }

    public int getHeaderElNum() {
        return headerElNum;
    }

    private void setHeaderElNum(int headerElNum) {
        this.headerElNum = headerElNum;
    }

}
