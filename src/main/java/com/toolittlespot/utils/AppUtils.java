package main.java.com.toolittlespot.utils;

import com.apple.eawt.Application;
import main.java.com.toolittlespot.controller.Main;
import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.elements.FileElement;
import main.java.com.toolittlespot.elements.LabelElement;
import main.java.com.toolittlespot.elements.RowElement;
import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    private static Path logFile;

    public static String defaultFilePath;
    public static String compressorPath;

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
                    RowElement fileRow = application.getGrid().createRowFromLabels(labels, fileRowsNum, Constants.FILE_ROW_STYLE);
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
        alert.setTitle(LangMap.getDict(Dict.SAVE_ALERT_TITLE));
        alert.setHeaderText(LangMap.getDict(Dict.SAVE_ALERT_HEADER));
        alert.setContentText(LangMap.getDict(Dict.SAVE_ALERT_CONTEXT));

        alert.showAndWait();
    }

    public static String createTempDir() {
        try {
            File file = new File(Files.createDirectories(
                    Paths.get(System.getProperty("java.io.tmpdir") + "easyPng/converted_files")).toUri());
            file.deleteOnExit();
            return file.getAbsolutePath() + "/";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createTempCompressorFile(){
        /* get compressor from source and copy to temp dir */
        String compressor = getCompressorName();
        if (compressor == null){
            throw new RuntimeErrorException(new Error(), "This application can be run on MacOS and Windows only");
        }
        try {
            InputStream inputStream = ClassLoader
                    .getSystemResourceAsStream("main/java/com/toolittlespot/compressor/" + compressor);
            Files.copy(
                    inputStream,
                    Paths.get(System.getProperty("java.io.tmpdir") + "easyPng/" + compressor),
                    StandardCopyOption.REPLACE_EXISTING
            );

            File file = new File(System.getProperty("java.io.tmpdir") + "easyPng/" + compressor);
            file.deleteOnExit();
            makeFileExecutable(file);
            inputStream.close();

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getCompressorName() {
        SystemOS currentOS = getSystemOS();
        if ( currentOS == SystemOS.WINDOWS ){
            return "pngquant_win.exe";
        }
        else if (currentOS == SystemOS.MAC) {
            return "pngquant_mac";
        }
        else return null;
    }

    public static SystemOS getSystemOS(){
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")){
            return SystemOS.WINDOWS;
        }
        else if (OS.contains("mac")){
            return SystemOS.MAC;
        }
        else if (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0){
            return SystemOS.UNIX;
        }
        return SystemOS.OTHER;
    }

    public static void setAppIcon(){
        InputStream is = ClassLoader.getSystemResourceAsStream("resources/images/icon.png");
        java.awt.Image image = null;
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Application.getApplication().setDockIconImage(image);
    }

    public static void createTempFiles() {
        defaultFilePath = createTempDir();
        compressorPath = createTempCompressorFile();
    }

    private static void makeFileExecutable(File file) {
        String[] processCommand = {"chmod", "+x", file.getAbsolutePath()};
        try {
            Process process = Runtime.getRuntime().exec(processCommand);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
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
