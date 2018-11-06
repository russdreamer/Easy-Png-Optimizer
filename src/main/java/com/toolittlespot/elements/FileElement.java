package main.java.com.toolittlespot.elements;

import java.io.File;

public class FileElement {
    private File file;
    private String fileNameToSave;
    private int rowNumber;

    public FileElement(File file, int rowNumber) {
        this.file = file;
        this.rowNumber = rowNumber;
        this.fileNameToSave = file.getName();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileNameToSave() {
        return fileNameToSave;
    }

    public void setFileNameToSave(String fileNameToSave) {
        this.fileNameToSave = fileNameToSave;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
