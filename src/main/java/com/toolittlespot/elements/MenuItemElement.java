package main.java.com.toolittlespot.elements;

import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;


public class MenuItemElement {
    private MenuItem languageItem;
    private String languageName;

    public MenuItemElement(MenuItem item, String languageName) {
        this.languageItem = item;
        this.languageName = languageName;
    }

    public MenuItem getLanguageItem() {
        return languageItem;
    }

    public String getLanguageName() {
        return languageName;
    }
}
