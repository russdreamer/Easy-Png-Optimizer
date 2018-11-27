package main.java.com.toolittlespot.convert;

import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.elements.ImageElement;
import main.java.com.toolittlespot.elements.RowElement;
import javafx.application.Platform;
import main.java.com.toolittlespot.utils.Constants;

import java.io.File;
import java.util.concurrent.Callable;

public class ConvertFile implements Callable<Boolean> {
    private ImageElement fileElement;
    private ApplicationArea application;

    /**
     *
     * @param application instance of running application element
     * @param fileElement wrapped original file to optimize
     */
    ConvertFile(ApplicationArea application, ImageElement fileElement) {
        this.application = application;
        this.fileElement = fileElement;
    }

    /**
     * gettin size of optimized file
     * @param fileElement wrapped original file
     * @return size of optimized file
     */
    private long getNewSize(ImageElement fileElement) {
        return new File(fileElement.getTempFilePath()).length();
    }

    @Override
    public Boolean call() {
        if (fileElement.optimize(application)){
            if (! Thread.interrupted()) {
                updateRow();
                setAsConverted();
                return true;
            }
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
