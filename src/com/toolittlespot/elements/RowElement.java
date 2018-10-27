package com.toolittlespot.elements;


import static com.toolittlespot.Constants.BYTE;

public class RowElement {
    private CellElement nameCell;
    private CellElement beforeCell;
    private CellElement afterCell;
    private CellElement reduceCell;

    public CellElement getNameCell() {
        return nameCell;
    }

    public void setNameCell(CellElement nameCell) {
        this.nameCell = nameCell;
    }

    public CellElement getBeforeCell() {
        return beforeCell;
    }

    public void setBeforeCell(CellElement beforeCell) {
        this.beforeCell = beforeCell;
    }

    public CellElement getAfterCell() {
        return afterCell;
    }

    public void setAfterCell(CellElement afterCell) {
        this.afterCell = afterCell;
    }

    public CellElement getReduceCell() {
        return reduceCell;
    }

    public void setReduceCell(CellElement reduceCell) {
        this.reduceCell = reduceCell;
    }

    public void setCellFromIndex(int index, CellElement cell) {
        switch (index){
            case 0: nameCell = cell; break;
            case 1: beforeCell = cell; break;
            case 2: afterCell = cell; break;
            case 3: reduceCell = cell; break;
            default: throw new IndexOutOfBoundsException();
        }
    }

    public CellElement getCellFromIndex(int index) {
        switch (index){
            case 0: return nameCell;
            case 1: return beforeCell;
            case 2: return afterCell;
            case 3: return reduceCell;
            default: return null;
        }
    }

    public void changeRowConvertValues(long afterValue, long reduceValue){
        getAfterCell().setText(afterValue + BYTE);
        getReduceCell().setText(reduceValue + "%");
    }

    public void changeRowColor(String style){
        getReduceCell().getBackground().setStyle(style);
    }
}
