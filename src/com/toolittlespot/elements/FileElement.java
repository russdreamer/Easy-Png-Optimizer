package com.toolittlespot.elements;

import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.toolittlespot.Constants.FILE_ROW_STYLE;

public class FileElement {
    private File file;
    private String fileNameToSave;
    private int rowNumber;

    public FileElement(File file, int rowNumber) {
        this.file = file;
        this.rowNumber = rowNumber;
        this.fileNameToSave = file.getName();
    }

    public static boolean downloadFiles(List<File> files, ApplicationArea application){
        boolean isCorrect = false;
        int fileRowsNum = application.getGrid().getFileRows().size();
        List<RowElement> fileRows = new ArrayList<>();

        for (File file: files) {
            if (isImage(file) && isPng(file)){
                FileElement fileElement = new FileElement(file, fileRowsNum);
                if (application.getFileMap().putIfDontExists(fileElement)){
                    application.getUnconvertedFiles().add(fileElement.rowNumber, fileElement);
                    fileRowsNum++;
                    List<Label> labels = LabelElement.createLabels(file);
                    RowElement fileRow = application.getGrid().createRowFromLabels(labels, fileRowsNum, FILE_ROW_STYLE);
                    fileRows.add(fileRow);
                    isCorrect = true;
                }
            }
        }
        application.getGrid().getFileRows().addAll(fileRows);

        return isCorrect;
    }


    private static boolean isPng(File file) {
        String extension = getExtension(file.getName());
        return "png".equals(extension);

    }

    public static String getExtension(String name) {
        String[] arr = name.split("\\.");
        return arr[arr.length - 1];
    }

    private static boolean isImage(File file) {
        try {
            BufferedImage im = ImageIO.read(file);
        } catch (IOException e) {
            return false;
        }
        return true;
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
