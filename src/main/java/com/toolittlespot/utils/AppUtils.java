package main.java.com.toolittlespot.utils;

import com.apple.eawt.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import main.java.com.toolittlespot.controller.Main;
import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.elements.FileElement;
import main.java.com.toolittlespot.elements.LabelElement;
import main.java.com.toolittlespot.elements.RowElement;
import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.management.RuntimeErrorException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

import static main.java.com.toolittlespot.utils.Constants.*;

public class AppUtils {
    private static String appVersionNumber; // version of application
    private static String appVersionName; // name of application version
    private static String lastAppVersion; // last available version of application
    private static String lastAppVersionLink; // download link of last application version
    public static Map<String, Object> userState; // application configurations

    /**
     * upload files to application
     * @param files list of files to upload
     * @param application current application instance
     * @return (true) if file is correct and added
     */
    public static boolean uploadFiles(List<File> files, ApplicationArea application) {
        List<RowElement> fileRows = new ArrayList<>();

        files.stream()
                .filter(AppUtils::isCorrectImage)
                .map(file -> new FileElement(file, getNumberOfRows(application, fileRows)))
                .filter(fileElement -> application.getFileMap().putIfDoesNotExist(fileElement))
                .filter(AppUtils::copyFileToTempDir)
                .forEach(fileElement -> {
                    application.getUnoptimizedFiles().add(fileElement.getRowNumber(), fileElement);
                    List<Label> labels = LabelElement.createLabels(fileElement.getFile());
                    RowElement fileRow = application.getGrid().createRowFromLabels(
                            labels, fileElement.getRowNumber() + 1, Constants.FILE_ROW_STYLE);
                    fileRows.add(fileRow);
                });

        application.getGrid().getFileRows().addAll(fileRows);
        return fileRows.size() > 0;
    }

    /**
     * get number of filled file rows
     * @param application current application
     * @param fileRows temporary uploaded files
     * @return number of filled file rows
     */
    private static int getNumberOfRows(ApplicationArea application, List<RowElement> fileRows) {
        int gridRows = application.getGrid().getFileRows().size();
        int lastUploadedFileSize = fileRows.size();

        return  gridRows + lastUploadedFileSize;
    }

    /**
     * copy image to temporary system directory
     * @param fileElement image to upload
     * @return true if image is uploaded
     */
    private static boolean copyFileToTempDir(FileElement fileElement) {
        Path source = fileElement.getFile().toPath();
        Path destDir = Paths.get(fileElement.getTempFilePath());
        try {
            Files.copy(source, destDir, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * define buttons states depending on current application state
     * @param application current application
     */
    public static void setButtonState(ApplicationArea application) {
        int optimizedImages = application.getOptimizedFiles().size();
        int numOfImages = application.getUnoptimizedFiles().size();

        if (optimizedImages == 0){
            application.getButtons().setFileUploadedButtonsState();
        }
        else if (optimizedImages < numOfImages){
            application.getButtons().setPartlyConvertedButtonsState();
        }
        else application.getButtons().setAllOptimizedButtonsState();
    }

    /**
     * get file extension
     * @param name file name or file path
     * @return file extension
     */
    static String getExtension(String name) {
        String[] arr = name.split("\\.");
        return arr[arr.length - 1];
    }

    /**
     * get root path of application
     * @return root path application
     */
    private static String getAppLocation() {
        String location = null;
        try {
            location = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParentFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * show alert when files are saved
     */
    public static void showSavedAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LangMap.getDict(Dict.SAVE_ALERT_TITLE));
        alert.setHeaderText(LangMap.getDict(Dict.SAVE_ALERT_HEADER));
        alert.setContentText(LangMap.getDict(Dict.SAVE_ALERT_CONTEXT));

        alert.showAndWait();
    }

    /**
     * create temporary directory
     * @return temporary directory path
     */
    static String createTempDir() {
        try {
            File file = new File(Files.createDirectories(
                    Paths.get(System.getProperty("java.io.tmpdir") + "easyPng/converted_files")).toUri());
            file.deleteOnExit();
            return file.getAbsolutePath() + File.separator;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * create native compressor file copy as temporary file
     * @return temporary compressor file path
     */
    static String createTempCompressorFile(){
        /* get compressor from source and copy to temp dir */
        String compressor = getCompressorName();
        if (compressor == null){
            throw new RuntimeErrorException(new Error(), "This application can be run on MacOS and Windows only");
        }
        try {
            InputStream inputStream = ClassLoader
                    .getSystemResourceAsStream("resources/compressor/" + compressor);
            Files.copy(
                    inputStream,
                    Paths.get(System.getProperty("java.io.tmpdir") + "easyPng/" + compressor),
                    StandardCopyOption.REPLACE_EXISTING
            );

            File file = new File(System.getProperty("java.io.tmpdir") + "easyPng/" + compressor);
            file.deleteOnExit();
            if (getSystemOS() == SystemOS.MAC) makeFileExecutable(file);
            inputStream.close();

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get compressor file name depending on user operating system
     * @return compressor file name. (null) if user operating system is not defined or not supported
     */
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

    /**
     * get user operating system
     * @return user operating system
     */
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

    /**
     * set application icon for the jar file
     */
    public static void setAppIcon(){
        try (InputStream is = ClassLoader.getSystemResourceAsStream("resources/images/icon.png")) {
            BufferedImage image = ImageIO.read(is);
            Application.getApplication().setDockIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create temporary files for app working
     */
    public static void createTempFiles() {
        createTempDir();
        createTempCompressorFile();
    }

    /**
     * get current application name
     * @return application name
     */
    private static String getAppVersionName() {
        if (AppUtils.appVersionName == null){
            extractAppVersion();
        }
       return AppUtils.appVersionName;
    }

    /**
     * get current application version
     * @return application version
     */
    public static String getAppVersionNumber() {
        if (AppUtils.appVersionNumber == null){
            extractAppVersion();
        }
        return AppUtils.appVersionNumber;
    }

    /**
     * extract application version and name
     * @see #getAppVersionName()
     * @see #getLastAppVerNum()
     */
    private static void extractAppVersion() {
        try(
            InputStream is = ClassLoader.getSystemResourceAsStream("version");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))
        ){
            String[] line = reader.readLine().split(" ");
            AppUtils.appVersionName = line[0];
            AppUtils.appVersionNumber = line[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * make MacOS compressor file executable
     * @param file compressor file
     */
    private static void makeFileExecutable(File file) {
        String[] processCommand = {"chmod", "+x", file.getAbsolutePath()};
        try {
            Process process = Runtime.getRuntime().exec(processCommand);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * check if file is image
     * @param file file to check
     * @return (true) if file is image
     */
    private static boolean isCorrectImage(File file) {
        String imageFormat;
        try {
            imageFormat = getImageFormat(file);
        } catch (IOException e) {
            return false;
        }

        if ("jpeg".equals(imageFormat) || "png".equals(imageFormat)) {
            return true;
        }
        return false;
    }

    /**
     * get image format name
     * @param file image file
     * @return image format name or "null" if no image format found
     * @throws IOException if file is not image
     */
    private static String getImageFormat(File file) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

        while (imageReaders.hasNext()) {
            ImageReader reader = imageReaders.next();
            return reader.getFormatName().toLowerCase();
        }
        return null;
    }

    /**
     * show error alert
     * @param message error message
     */
    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * get last available application version
     * @return last application version
     */
    private static String getLastAppVerNum() {
        if (AppUtils.lastAppVersion == null){
            extractLastVersion();
        }
        return AppUtils.lastAppVersion;
    }

    /**
     * get last available application version link to download
     * @return link to download last app version
     */
    public static String getLastAppVerLink() {
        if (AppUtils.lastAppVersionLink == null){
            extractLastVersion();
        }
        return AppUtils.lastAppVersionLink;
    }

    /**
     * extract last available application version and link from remote git repository
     */
    private static void extractLastVersion () {
        String lastVersionPath = "https://raw.githubusercontent.com/russdreamer/Easy-Png-Optimizer/master/src/new_version_links";
        try {
            URL url = new URL(lastVersionPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result;
            while ((result = reader.readLine()) != null) {
                String[] line = result.split(" ");
                if (AppUtils.getAppVersionName().equals(line[0])){
                    AppUtils.lastAppVersion = line[1];
                    AppUtils.lastAppVersionLink = line[2];
                    reader.close();
                    return;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get last app version changes
     * @return text of changes
     */
    public static String getPatchContent() {
        if (AppUtils.lastAppVersion == null){
            return  LangMap.getDict(Dict.GET_PATCH_NOTE_ERROR);
        }

        String patchNotePath = "https://raw.githubusercontent.com/russdreamer/Easy-Png-Optimizer/master/src/" +
                LangMap.getDict(Dict.PATCH_NOTE_FILE);
        try {
            URL url = new URL(patchNotePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String result;
            builder.append("New version «Easy Png» ").
                    append(AppUtils.lastAppVersion).
                    append("\n");
            while ((result = reader.readLine()) != null) {
                builder.append(result).
                        append("\n");
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LangMap.getDict(Dict.GET_PATCH_NOTE_ERROR);
    }

    /**
     * compare current and last available app version
     * @return (true) if current version is equal to the last one
     */
    public static boolean isNewVersionAppAvailable() {
        if (AppUtils.getLastAppVerNum() == null) {
            return false;
        }
        return ! AppUtils.getAppVersionNumber().equals(AppUtils.getLastAppVerNum());
    }

    /**
     * check application updates
     * @param application current application
     */
    public static void runUpdater(ApplicationArea application) {
        long now = Date.from(Instant.now()).getTime();
        if (now >= (long)AppUtils.userState.get(UPDATE_DAY)) {
            Platform.runLater(() -> {
                if (AppUtils.isNewVersionAppAvailable()){
                    application.getMenuBar().getUpdate().fire();
                    postponeNextCheck(5);
                }
                else postponeNextCheck(1);
            });
        }
    }

    /**
     * postpone next check
     * @param days days to postpone from now
     */
    private static void postponeNextCheck(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        AppUtils.userState.put(UPDATE_DAY, calendar.getTime().getTime());
    }

    /**
     * get user's system language
     * @return user's system language
     */
    private static String getLocalLanguage(){
        return Locale.getDefault().getLanguage();
    }

    /**
     * extract application configurations
     */
    public static void loadStateFile() {
        String  appPath = AppUtils.getAppLocation() + File.separator + STATE_FILE_NAME;
        if (Files.exists(Paths.get(appPath), LinkOption.NOFOLLOW_LINKS)){
            AppUtils.deserializeUserState(appPath);
        }
        else {
            AppUtils.putInitState();
            AppUtils.serializeUserState(appPath);
        }
    }

    /**
     * initialize app configuration if the app is launched for the first time
     */
    private static void putInitState() {
        userState = new HashMap<>();
        userState.put(USER_LANGUAGE, AppUtils.getLocalLanguage());
        userState.put(UPDATE_DAY, Date.from(Instant.now()).getTime());
    }

    /**
     * deserialize application configuration
     * @param filePath application configuration file path
     */
    private static void deserializeUserState(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userState = (HashMap) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * serialize application configuration
     * @param filePath application configuration file path
     */
    private static void serializeUserState(String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(userState);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * save application states to application configuration file
     */
    public static void saveState() {
        serializeUserState(AppUtils.getAppLocation() + File.separator + STATE_FILE_NAME);
    }
}
