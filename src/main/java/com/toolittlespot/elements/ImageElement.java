package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.utils.Constants;

import java.io.File;

public abstract class ImageElement {
    private File file;
    private int rowNumber;
    private String fileNameToSave;
    private String tempFilePath;

    public ImageElement(File file, int rowNumber) {
        this.file = file;
        this.rowNumber = rowNumber;
        this.fileNameToSave = file.getName();
        this.tempFilePath = Constants.DEFAULT_FILE_PATH + rowNumber;
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

    /**
     * optimization png file with compressor
     * @param application current application
     * @return true if optimization completed successful
     */
    public abstract boolean optimize(ApplicationArea application);
}
