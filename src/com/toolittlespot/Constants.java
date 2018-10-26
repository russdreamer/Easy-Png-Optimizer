package com.toolittlespot;

public class Constants {
    public static final String NOT_DRAGGED = "Drag files here";
    public static final String DRAGGED = "Drop here";
    public static final String EMPTY_FIELD = "--";

    /* colors */
    public static final String HEADER_BG_STYLE = "-fx-background-color: rgba(115, 248, 215, 0.5);";
    public static final String FILE_ROW_STYLE = "-fx-background-color: rgba(240, 255, 0, 0.2);";
    public static final String DRAGGED_WINDOW_STYLE = "-fx-background-color: rgba(255, 255, 255, 0.7);";

    public static final int SCENE_WIDTH = 500;
    public static final int SCENE_HEIGHT = 250;

    public static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + "/src/com/toolittlespot/temporary_files_dir/";
    public static final String COMPRESSOR_PATH = "src/com/toolittlespot/compressor/pngCompressor";
}