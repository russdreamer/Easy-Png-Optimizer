package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;

public class RowElement {
    private CellElement nameCell;
    private CellElement beforeCell;
    private CellElement afterCell;
    private CellElement reduceCell;

    private CellElement getAfterCell() {
        return afterCell;
    }

    private CellElement getReduceCell() {
        return reduceCell;
    }

    /**
     * setting cell elements from known index
     * @param index index of cell to change
     * @param cell new cell element
     */
    void setCellFromIndex(int index, CellElement cell) {
        switch (index){
            case 0: nameCell = cell; break;
            case 1: beforeCell = cell; break;
            case 2: afterCell = cell; break;
            case 3: reduceCell = cell; break;
            default: throw new IndexOutOfBoundsException();
        }
    }

    /**
     * setting the reduce cell value
     * @param afterValue new file size
     * @param reduceValue quantity of compression as percent value
     */
    public void changeRowConvertValues(long afterValue, long reduceValue){
        getAfterCell().setText(afterValue + LangMap.getDict(Dict.BYTES));
        getReduceCell().setText(reduceValue + "%");
    }

    /**
     * changing row color when file optimization process is done
     * @param style row color style
     */
    public void changeRowColor(String style){
        getReduceCell().getBackground().setStyle(style);
    }
}
