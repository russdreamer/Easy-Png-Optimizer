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
    Main main;

    {
        menuBar = new MenuBar();
        Menu help = new Menu(LangMap.getDict(Dict.HELP_MENU_ITEM));
        MenuItem about = new MenuItem(LangMap.getDict(Dict.ABOUT_MENU_ITEM));
        about.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LangMap.getDict(Dict.ABOUT_MENU_ITEM));
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(getAboutItemContext());
            alert.show();
        });
        help.getItems().add(about);
        menuBar.getMenus().add(help);

        if (AppUtils.getSystemOS() == SystemOS.MAC){
            menuBar.setUseSystemMenuBar(true);
        }
    }

    public MenuBarElement(Main main) {
        this.main = main;
    }

    private Pane getAboutItemContext() {
        Text version = new Text(LangMap.getDict(Dict.APPLICATION_VERSION) + ": " + AppUtils.getAppVersion());
        Text author = new Text(LangMap.getDict(Dict.AUTHOR) + ": Igor Kovtun");

        Hyperlink facebook = new Hyperlink("facebook");
        facebook.setOnAction((e)->main.getHostServices().showDocument("https://www.facebook.com/Russian.troll"));

        Hyperlink email = new Hyperlink("e-mail");
        email.setOnAction((e)->main.getHostServices().showDocument("mailto:russdreamer@gmail.com"));

        Hyperlink git = new Hyperlink("git");
        git.setOnAction((e)->main.getHostServices().showDocument("https://github.com/russdreamer"));

        return new VBox(version, author, new HBox(facebook, email, git));
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
