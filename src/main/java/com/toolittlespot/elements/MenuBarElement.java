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
    Main main;

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
            alert.show();
        });
        return update;
    }

    private String getUpdateContent() {
        String patchNotePath = "https://raw.githubusercontent.com/russdreamer/Easy-Png-Optimizer/master/src/" +
                LangMap.getDict(Dict.PATCH_NOTE_FILE);
        try {
            URL url = new URL(patchNotePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String result;
            while ((result = reader.readLine()) != null) {
                builder.append(result);
            }
            reader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LangMap.getDict(Dict.GET_PATCH_NOTE_ERROR);
    }

    private boolean isCurrentVersionLast() {
        String lastVersionPath = "https://raw.githubusercontent.com/russdreamer/Easy-Png-Optimizer/master/src/version";
        try {
            URL url = new URL(lastVersionPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lastVersion = reader.readLine();
            reader.close();
            return AppUtils.getAppVersion().equals(lastVersion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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
        Text version = new Text(LangMap.getDict(Dict.APPLICATION_VERSION) + ": " + AppUtils.getAppVersion());
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
