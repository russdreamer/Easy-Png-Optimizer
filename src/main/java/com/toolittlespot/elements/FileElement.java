package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.utils.Constants;

import java.io.File;

public class FileElement {
    private File file;
    private String fileNameToSave;
    private String tempFilePath;
    private int rowNumber;

    public FileElement(File file, int rowNumber) {
        this.file = file;
        this.rowNumber = rowNumber;
        this.fileNameToSave = file.getName();
        this.tempFilePath = Constants.DEFAULT_FILE_PATH + File.separator + rowNumber + ".png";
    }

    public File getFile() {
        return file;
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

    public String getTempFilePath() {
        return tempFilePath;
    }
}
