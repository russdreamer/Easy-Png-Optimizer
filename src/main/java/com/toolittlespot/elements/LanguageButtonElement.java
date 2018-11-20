package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.events.LanguageClickedEvent;
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

import static main.java.com.toolittlespot.utils.Constants.ICON_BUTTON_SIZE;

public class LanguageButtonElement {
    private MenuButton menuButton;
    private ApplicationArea application;

    public LanguageButtonElement(MenuButton menuButton, ApplicationArea application) {
        this.menuButton = menuButton;
        this.application = application;
        createButton();
    }

    /**
     * creating the language switch button
     */
    private void createButton() {
        MenuItemElement eng = createMenuItemEl("resources/images/en.png", "en");
        MenuItemElement rus = createMenuItemEl("resources/images/ru.png", "ru");
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

    /**
     * creating language options
     * @param languages list of languages to set as menu item
     * @return list of created language menu items
     */
    private List<MenuItem> createMenuItems(List<MenuItemElement> languages) {
        List<MenuItem> list = new ArrayList<>();
        languages.forEach((item) -> list.add(item.getLanguageItem() ));
        return list;
    }

    /**
     * setting default language option
     * @param languages list of possible languages
     * @return (true) if user system language is one of app possible languages
     */
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

    /**
     * creating language menu options to switch
     * @param path image with language(country) flag
     * @param language name of language
     * @return language menu item
     */
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
}
