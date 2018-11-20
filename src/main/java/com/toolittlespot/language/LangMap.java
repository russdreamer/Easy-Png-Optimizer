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

    /**
     * getting language dictionary with  user language
     * @return language dictionary
     */
    public static Map<String, String> getDict(){
        if (!dictLoaded) {
            loadDictionaries();
            determinateUserLanguage();
        }
        
        return currentDict;
    }

    /**
     * getting a phrase from language dictionary in current language
     * @param word key word from language dictionary to extract
     * @return phrase from language dictionary
     */
    public static String getDict(Dict word){
            return getDict().get(word.name());
    }

    /**
     * changing current language to chosen one
     * @param language language to change
     */
    public static void changeLanguage(String language){
        switch (language){
            case "en": currentDict = englishDict; break;
            case "ru": currentDict = russianDict; break;
            default: currentDict = englishDict; break;
        }
        AppUtils.userState.put(USER_LANGUAGE, language);
    }

    /**
     * determination user's system language
     */
    private static void determinateUserLanguage() {
        String language = ApplicationArea.userLanguage;
        changeLanguage(language);
    }

    /**
     * loading language dictionary to use
     */
    private static synchronized void loadDictionaries() {
        Dict[] keys = Dict.values();

        for (Dict key: keys) {
            englishDict.put(key.name(), key.english);
            russianDict.put(key.name(), key.russian);
        }
        dictLoaded = true;
    }
}
