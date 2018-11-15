package main.java.com.toolittlespot.language;

import main.java.com.toolittlespot.elements.ApplicationArea;
import main.java.com.toolittlespot.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import static main.java.com.toolittlespot.utils.Constants.USER_LANGUAGE;

public class LangMap {
    private static Map<String, String> englishDict = new HashMap<>();
    private static Map<String, String> russianDict = new HashMap<>();
    private static Map<String, String> currentDict;
    private static boolean dictLoaded = false;

    public static Map<String, String> getDict(){
        if (!dictLoaded) {
            loadDictionaries();
            determinateUserLanguage();
        }
        
        return currentDict;
    }

    public static String getDict(Dict word){
            return getDict().get(word.name());
    }

    public static void changeLanguage(String language){
        switch (language){
            case "English": currentDict = englishDict; break;
            case "Russian": currentDict = russianDict; break;
            default: currentDict = englishDict; break;
        }
        AppUtils.userState.put(USER_LANGUAGE, language);
    }

    private static void determinateUserLanguage() {
        String language = ApplicationArea.userLanguage;
        changeLanguage(language);
    }

    private static synchronized void loadDictionaries() {
        Dict[] keys = Dict.values();

        for (Dict key: keys) {
            englishDict.put(key.name(), key.english);
            russianDict.put(key.name(), key.russian);
        }
        dictLoaded = true;
    }
}
