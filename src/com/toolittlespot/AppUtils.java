package com.toolittlespot;

import com.toolittlespot.controller.Main;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import com.toolittlespot.elements.LabelElement;
import com.toolittlespot.elements.RowElement;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static com.toolittlespot.Constants.*;

public class AppUtils {

    public static boolean downloadFiles(List<File> files, ApplicationArea application){
        boolean isCorrect = false;
        int fileRowsNum = application.getGrid().getFileRows().size();
        List<RowElement> fileRows = new ArrayList<>();

        for (File file: files) {
            if (isImage(file) && isPng(file)){
                FileElement fileElement = new FileElement(file, fileRowsNum);
                if (application.getFileMap().putIfDontExists(fileElement)){
                    application.getUnconvertedFiles().add(fileElement.getRowNumber(), fileElement);
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

    public static void setButtonState(ApplicationArea application) {
        int done = application.getConvertedFiles().size();
        int fileSize = application.getUnconvertedFiles().size();

        if (done == 0){
            application.getButtons().setFileUploadedButtonsState();
        }
        else if (done < fileSize){
            application.getButtons().setPartlyConvertedButtonsState();
        }
        else application.getButtons().setAllConvertedButtonsState();
    }

    static String getExtension(String name) {
        String[] arr = name.split("\\.");
        return arr[arr.length - 1];
    }

    public static String getAppLocation() {
        String location = null;
        try {
            location = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return location;
    }

    public static void showSavedAllert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ALERT_TITLE);
        alert.setHeaderText(ALERT_HEADER);
        alert.setContentText(ALERT_CONTEXT);

        /* might be better to use .show() due to jdk bug https://bugs.openjdk.java.net/browse/JDK-8211137 */
        alert.showAndWait();
    }

    public static String createTempDir() {
        try {
            File file = new File(Files.createDirectories(
                    Paths.get(System.getProperty("java.io.tmpdir") + "easyPngOptimizer/converted_files")).toUri());
            file.deleteOnExit();
            return file.getAbsolutePath() + "/";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createTempCompressorFile(){
        /* get compressor from source and copy to temp dir */
        try {
            InputStream inputStream = ClassLoader
                    .getSystemResourceAsStream("com/toolittlespot/compressor/pngCompressor");
            Files.copy(
                    inputStream,
                    Paths.get(System.getProperty("java.io.tmpdir") + "easyPngOptimizer/pngCompressor"),
                    StandardCopyOption.REPLACE_EXISTING
            );

            File file = new File(System.getProperty("java.io.tmpdir") + "easyPngOptimizer/pngCompressor");
            file.deleteOnExit();
            makeFileExecutable(file);

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void makeFileExecutable(File file) {
        String[] processCommand = {"chmod", "+x", file.getAbsolutePath()};
        try {
            Process process = Runtime.getRuntime().exec(processCommand);
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean isImage(File file) {
        try {
           ImageIO.read(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static boolean isPng(File file) {
        String extension = getExtension(file.getName());
        return "png".equals(extension);

    }

}
