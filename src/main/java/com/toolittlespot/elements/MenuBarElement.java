package main.java.com.toolittlespot.elements;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.com.toolittlespot.controller.Main;
import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;
import main.java.com.toolittlespot.utils.AppUtils;
import main.java.com.toolittlespot.utils.SystemOS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MenuBarElement {
    private MenuBar menuBar;
    private Main main;
    private String lastAppVersion;
    private String lastAppLink;


    {
        menuBar = new MenuBar();
        Menu help = createHelpMenu();
        menuBar.getMenus().addAll(help);

        if (AppUtils.getSystemOS() == SystemOS.MAC){
            menuBar.setUseSystemMenuBar(true);
        }
    }

    private MenuItem createUpdateItem() {
        MenuItem update = new MenuItem(LangMap.getDict(Dict.UPDATE_MENU_ITEM));
        update.setOnAction(e->{
            extractLastVersion();
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle(LangMap.getDict(Dict.UPDATE_MENU_ITEM));
            alert.setHeaderText(null);
            ButtonType updateButton = new ButtonType(LangMap.getDict(Dict.UPDATE_MENU_ITEM));
            ButtonType close = new ButtonType(LangMap.getDict(Dict.CLOSE_BUTTON));

            String content;
            if (isCurrentVersionLast()){
                content = LangMap.getDict(Dict.YOU_USE_LAST_VERSION);
            }
            else {
                content = getUpdateContent();
                alert.getButtonTypes().add(updateButton);
            }

            alert.getButtonTypes().add(close);
            alert.setContentText(content);
            alert.showAndWait();
            if (alert.getResult() == updateButton) {
                openDownloadPage();
            }
        });
        return update;
    }

    private void extractLastVersion() {
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
                    this.lastAppVersion = line[1];
                    this.lastAppLink = line[2];
                    reader.close();
                    return;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDownloadPage() {
        if (lastAppLink == null) {
            AppUtils.showErrorAllert(LangMap.getDict(Dict.GET_UPDATE_ERROR));
        }
        else main.getHostServices().showDocument(lastAppLink);
    }

    private String getUpdateContent() {
        if (this.lastAppVersion == null){
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
                    append(lastAppVersion).
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

    private boolean isCurrentVersionLast() {
        return AppUtils.getAppVersionNum().equals(lastAppVersion);
    }

    private Menu createHelpMenu() {
        Menu help = new Menu(LangMap.getDict(Dict.HELP_MENU_ITEM));
        MenuItem about = createAboutItem();
        MenuItem update = createUpdateItem();
        help.getItems().addAll(about, update);
        return help;
    }

    private MenuItem createAboutItem() {
        MenuItem about = new MenuItem(LangMap.getDict(Dict.ABOUT_MENU_ITEM));
        about.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LangMap.getDict(Dict.ABOUT_MENU_ITEM));
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(getAboutItemContext());
            alert.show();
        });
        return about;
    }

    public MenuBarElement(Main main) {
        this.main = main;
    }

    private Pane getAboutItemContext() {
        String appVersion = AppUtils.getAppVersionNum();
        appVersion = appVersion != null ? appVersion : "--";
        Text version = new Text(LangMap.getDict(Dict.APPLICATION_VERSION) + ": " + appVersion);
        Text author = new Text(LangMap.getDict(Dict.AUTHOR) + ": Igor Kovtun");

        Hyperlink facebook = new Hyperlink("facebook");
        facebook.setOnAction(e -> main.getHostServices().showDocument("https://www.facebook.com/Russian.troll"));

        Hyperlink email = new Hyperlink("e-mail");
        email.setOnAction(e -> main.getHostServices().showDocument("mailto:russdreamer@gmail.com"));

        Hyperlink git = new Hyperlink("git");
        git.setOnAction(e -> main.getHostServices().showDocument("https://github.com/russdreamer"));

        return new VBox(version, author, new HBox(facebook, email, git));
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
