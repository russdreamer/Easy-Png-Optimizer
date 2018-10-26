package com.toolittlespot.elements;

import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.toolittlespot.Constants.EMPTY_FIELD;

public class LabelElement {

    public static List<Label> createLabels(String ...names){
        List<Label> labels = new ArrayList<>();
        for (String name: names) {
            labels.add(new Label(name));
        }
        return labels;
    }

    public static List<Label> createLabels(File file){
        String name = file.getName();
        String before = file.length() + " bytes";

        return createLabels(name, before, EMPTY_FIELD, EMPTY_FIELD);
    }
}
