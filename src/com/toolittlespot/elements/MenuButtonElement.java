package com.toolittlespot.elements;

import com.toolittlespot.controller.Main;
import com.toolittlespot.events.LanguageClickedEvent;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.toolittlespot.Constants.ICON_BUTTON_SIZE;

public class MenuButtonElement {
    private MenuButton menuButton;
    private ApplicationArea application;

    public MenuButtonElement(MenuButton menuButton, ApplicationArea application) {
        this.menuButton = menuButton;
        this.application = application;
        createButton();
    }

    private void createButton() {
        MenuItemElement eng = createMenuItemEl("resources/images/English.png", "English");
        MenuItemElement rus = createMenuItemEl("resources/images/Russian.png", "Russian");
        List<MenuItemElement> languages = new ArrayList<>();
        languages.add(eng);
        languages.add(rus);

        if ( !setMenuButtonGraphic(languages) ){
            menuButton.setGraphic(eng.getLanguageItem().getGraphic());
        }

        List<MenuItem> menuItems = createMenuItems(languages);
        menuButton.getItems().addAll(menuItems);
        StackPane.setAlignment(menuButton, Pos.BOTTOM_LEFT );
    }

    private List<MenuItem> createMenuItems(List<MenuItemElement> languages) {
        List<MenuItem> list = new ArrayList<>();
        languages.forEach((item) -> list.add(item.getLanguageItem() ));
        return list;
    }

    private boolean setMenuButtonGraphic(List<MenuItemElement> languages) {
        for (MenuItemElement element: languages) {
            if (ApplicationArea.userLanguage.equals(element.getLanguageName())){
                menuButton.setGraphic(element.getLanguageItem().getGraphic());
                languages.remove(element);
                return true;
            }
        }
        return false;
    }

    private MenuItemElement createMenuItemEl(String path, String language) {
        InputStream img = ClassLoader.getSystemResourceAsStream(path);
        ImageView imageView = new ImageView(new Image(img));
        imageView.setFitHeight(ICON_BUTTON_SIZE);
        imageView.setFitWidth(ICON_BUTTON_SIZE);

        try {
            img.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuItem menuItem = new MenuItem(null, imageView);
        menuItem.setOnAction(new LanguageClickedEvent(application, language));
        return new MenuItemElement(menuItem, language);
    }

    public MenuButton getMenuButton() {
        return menuButton;
    }

    public void setMenuButton(MenuButton menuButton) {
        this.menuButton = menuButton;
    }
}
