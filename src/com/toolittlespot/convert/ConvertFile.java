package com.toolittlespot.convert;

import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import com.toolittlespot.elements.RowElement;
import javafx.application.Platform;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import static com.toolittlespot.Constants.*;

public class ConvertFile implements Callable<Boolean> {
    final static Logger LOGGER = Logger.getLogger(ConvertFile.class);
    private FileElement fileElement;
    private ApplicationArea application;

    public ConvertFile(ApplicationArea application, FileElement fileElement) {
        this.application = application;
        this.fileElement = fileElement;
    }

    private void processFile(FileElement fileElement) throws InterruptedException {
        String filePath = fileElement.getFile().getAbsolutePath();
        String fileNameToSave = fileElement.getFileNameToSave();
        convertFile(filePath, DEFAULT_FILE_PATH + fileNameToSave);
    }

    private void convertFile(String pathFrom, String pathTo) throws InterruptedException {
        String[] processCommand = {COMPRESSOR_PATH, pathFrom, pathTo};
        Process process;
        BufferedReader reader;

        try {
            process = Runtime.getRuntime().exec(processCommand);
            application.getProcessList().add(process);
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            LOGGER.trace(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateRow();
    }

    private long getNewSize(FileElement fileElement) {
        return new File(DEFAULT_FILE_PATH + fileElement.getFileNameToSave()).length();
    }

    @Override
    public Boolean call() {
        try {
            processFile(fileElement);
        } catch (InterruptedException e) {
            LOGGER.warn("converting file is interrupted.");
            return false;
        }

        updateRow();
        return true;
    }

    private void updateRow() {
        long oldSize = fileElement.getFile().length();
        long newSize = getNewSize(fileElement);
        long reduce = (oldSize - newSize) * 100 / oldSize;
        String rowColor = oldSize - newSize > 0 ? SUCCESS_ROW_STYLE : FAILURE_ROW_STYLE;
        RowElement row = application.getGrid().getFileRows().get(fileElement.getRowNumber());
        Platform.runLater(()-> {
            row.changeRowConvertValues(newSize, reduce);
            row.changeRowColor(rowColor);
        });
    }
}
