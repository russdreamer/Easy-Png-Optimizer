package com.toolittlespot.elements;

import com.toolittlespot.language.Dict;
import com.toolittlespot.language.LangMap;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        String before = file.length() + LangMap.getDict(Dict.BYTES);

        return createLabels(name, before, LangMap.getDict(Dict.EMPTY_FIELD), LangMap.getDict(Dict.EMPTY_FIELD));
    }
}
