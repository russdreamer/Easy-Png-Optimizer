package main.java.com.toolittlespot.elements;

import javafx.scene.control.MenuItem;

class MenuItemElement {
    private MenuItem languageItem;
    private String languageName;

    MenuItemElement(MenuItem item, String languageName) {
        this.languageItem = item;
        this.languageName = languageName;
    }

    MenuItem getLanguageItem() {
        return languageItem;
    }

    String getLanguageName() {
        return languageName;
    }
}
