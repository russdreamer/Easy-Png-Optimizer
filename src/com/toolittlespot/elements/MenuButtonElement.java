package com.toolittlespot.elements;

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
    private ImageView rusFlag;
    private ImageView engFlag;

    public MenuButtonElement(MenuButton menuButton) {
        this.menuButton = menuButton;
        createButton();
    }

    private void createButton() {
        engFlag = createImageView("resources/images/engFlag.png");
        rusFlag = createImageView("resources/images/rusFlag.png");
        List<ImageView> languages = new ArrayList<>();
        languages.add(engFlag);
        languages.add(rusFlag);

        switch (ApplicationArea.userLanguage){
            case "Russian": menuButton.setGraphic(rusFlag); languages.remove(rusFlag); break;
            default: menuButton.setGraphic(engFlag); languages.remove(engFlag); break;
        }

        List<MenuItem> items = createItems(languages);
        menuButton.getItems().addAll(items);
        StackPane.setAlignment(menuButton, Pos.BOTTOM_LEFT );
    }

    private List<MenuItem> createItems(List<ImageView> languages) {
        List<MenuItem> items = new ArrayList<>();
        languages.forEach((lang)-> items.add(new MenuItem(null, lang)));
        return items;
    }

    private ImageView createImageView(String path) {
        InputStream img = ClassLoader.getSystemResourceAsStream(path);
        ImageView imageView = new ImageView(new Image(img));
        try {
            img.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setFitHeight(ICON_BUTTON_SIZE);
        imageView.setFitWidth(ICON_BUTTON_SIZE);
        return imageView;
    }

    public MenuButton getMenuButton() {
        return menuButton;
    }

    public void setMenuButton(MenuButton menuButton) {
        this.menuButton = menuButton;
    }
}
