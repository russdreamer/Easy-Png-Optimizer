package main.java.com.toolittlespot.elements;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import main.java.com.toolittlespot.language.Dict;
import main.java.com.toolittlespot.language.LangMap;

public class MenuBarElement {
    private MenuBar menuBar;
    private Menu help;
    private MenuItem about;

    {
        menuBar = new MenuBar();
        help = new Menu(LangMap.getDict(Dict.HELP_MENU_ITEM));
        about = new MenuItem(LangMap.getDict(Dict.ABOUT_MENU_ITEM));
        about.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(LangMap.getDict(Dict.ABOUT_MENU_ITEM));
            alert.setHeaderText(null);
            alert.setContentText(getAboutItemContext());
            alert.show();
        });
        help.getItems().add(about);
        menuBar.getMenus().add(help);
        menuBar.setUseSystemMenuBar(true);
    }

    private String getAboutItemContext() {
        String author = "Author"+ ": Igor Kovtun";
        String version = "Application version" + ": 1.0";
        // TODO: 13/11/2018 write website page and make it a link
        String website = "Website link" + ": linkedIn or something";

        return version + "\n" + author + "\n" + website;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
