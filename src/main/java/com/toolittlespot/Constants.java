package main.java.com.toolittlespot;

public class Constants {
    /* colors */
    public static final String HEADER_BG_STYLE = "-fx-background-color: rgba(115, 248, 215, 0.5);";
    public static final String FILE_ROW_STYLE = "-fx-background-color: rgba(240, 255, 0, 0.2);";
    public static final String SUCCESS_ROW_STYLE = "-fx-background-color: rgba(7, 190, 13, 0.4);";
    public static final String FAILURE_ROW_STYLE = "-fx-background-color: rgba(247, 4, 4, 0.4);";
    public static final String DRAGGED_WINDOW_STYLE = "-fx-background-color: rgba(255, 255, 255, 0.7);";

    public static final int SCENE_WIDTH = 500;
    public static final int SCENE_HEIGHT = 250;
    public static final int ICON_BUTTON_SIZE = 17;

    // TODO: 02/11/2018 поменять для джарника
    public static final String DEFAULT_FILE_PATH = AppUtils.createTempDir();
    public static final String COMPRESSOR_PATH = AppUtils.createTempCompressorFile();
}
