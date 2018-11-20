package main.java.com.toolittlespot.convert;

import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.elements.FileElement;
import main.java.com.toolittlespot.elements.RowElement;
import javafx.application.Platform;
import main.java.com.toolittlespot.utils.Constants;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConvertFile implements Callable<Boolean> {
    private final static Logger LOGGER = Logger.getLogger(ConvertFile.class);
    private FileElement fileElement;
    private ApplicationArea application;

    /**
     *
     * @param application instance of running application element
     * @param fileElement wrapped original file to optimize
     */
    ConvertFile(ApplicationArea application, FileElement fileElement) {
        this.application = application;
        this.fileElement = fileElement;
    }

    /**
     * processing file before optimization
     * @param fileElement wrapped file to process
     * @see #optimizeFile(String, String)
     */
    private boolean processFile(FileElement fileElement) {
        String filePath = fileElement.getFile().getAbsolutePath();
        return optimizeFile(filePath, fileElement.getTempFilePath());
    }

    /**
     * optimization png file with compressor
     * @param pathFrom original file location
     * @param pathTo optimized file location
     * @return true if optimization completed successful
     */
    private boolean optimizeFile(String pathFrom, String pathTo) {
        String[] processCommand = {
                Constants.COMPRESSOR_PATH,
                "--strip", "--speed", "1", "--nofs", "--force", "--output", //options
                pathTo, pathFrom};

        Process process;
        BufferedReader reader;
        try {
            process = Runtime.getRuntime().exec(processCommand);
            application.getProcessList().add(process);
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            LOGGER.trace(reader.readLine());
            reader.close();
        }
        catch (IOException | InterruptedException e) {
            LOGGER.warn("converting file is interrupted.");
            return false;
        }
        return true;
    }

    /**
     * gettin size of optimized file
     * @param fileElement wrapped original file
     * @return size of optimized file
     */
    private long getNewSize(FileElement fileElement) {
        return new File(fileElement.getTempFilePath()).length();
    }

    @Override
    public Boolean call() {
        if (processFile(fileElement)){
            setDeleteOnExit();
            updateRow();
            setAsConverted();
            return true;
        }
        return false;
    }

    /**
     * move file from unoptimized list to optimized list
     */
    private void setAsConverted() {
        application.getUnoptimizedFiles().set(fileElement.getRowNumber(), null);
        application.getOptimizedFiles().add(fileElement);
    }

    /**
     * delete optimized file from user temp directory when app closed
     */
    private void setDeleteOnExit() {
        new File(fileElement.getTempFilePath()).deleteOnExit();
    }

    /**
     * update processing file row with new size and compress quality
     */
    private void updateRow() {
        long oldSize = fileElement.getFile().length();
        long newSize = getNewSize(fileElement);
        long reduce = (oldSize - newSize) * 100 / oldSize;
        String rowColor = reduce > 0 ? Constants.SUCCESS_ROW_STYLE : Constants.FAILURE_ROW_STYLE;
        RowElement row = application.getGrid().getFileRows().get(fileElement.getRowNumber());
        Platform.runLater(()-> {
            row.changeRowConvertValues(newSize, reduce);
            row.changeRowColor(rowColor);
        });
    }
}
