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

public class MenuBarElement {
    private MenuBar menuBar;
    private Main main;
    private Menu help;
    private MenuItem about;
    private MenuItem update;


    {
        menuBar = new MenuBar();
        help = createHelpMenu();
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
            if (AppUtils.isCurrentVersionLast()){
                content = LangMap.getDict(Dict.YOU_USE_LAST_VERSION);
            }
            else {
                content = AppUtils.getPatchContent();
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

    private void openDownloadPage() {
        if (AppUtils.getLastAppVerLink() == null) {
            AppUtils.showErrorAllert(LangMap.getDict(Dict.GET_UPDATE_ERROR));
        }
        else main.getHostServices().showDocument(AppUtils.getLastAppVerLink());
    }

    private Menu createHelpMenu() {
        Menu help = new Menu(LangMap.getDict(Dict.HELP_MENU_ITEM));
        about = createAboutItem();
        update = createUpdateItem();
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

    public MenuItem getUpdate() {
        return update;
    }
}
