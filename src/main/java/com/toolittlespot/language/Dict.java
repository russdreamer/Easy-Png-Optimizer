package main.java.com.toolittlespot.language;

public enum Dict {
    NAME_ROW(
            "имя файла",
            "name"
    ),
    BEFORE_ROW(
            "до",
            "before"
    ),
    AFTER_ROW(
            "после",
            "after"
    ),
    REDUCE_ROW(
            "сжатие",
            "reduce"
    ),
    NOT_DRAGGED(
            "Перетащите файл сюда",
            "Drag files here"
    ),
    DRAGGED(
            "Поместите здесь",
            "Drop here"
    ),
    EMPTY_FIELD(
            "--",
            "--"
    ),
    COMPRESS_BUTTON(
            "сжать",
            "compress"
    ),
    CLEAR_ALL_BUTTON(
            "очистить",
            "clear all"
    ),
    STOP_BUTTON(
            "стоп",
            "stop"
    ),
    REPLACE_BUTTON(
            "заменить",
            "replace"
    ),
    SAVE_AS_BUTTON(
            "сохранить как...",
            "save as..."
    ),
    BYTES(
            "байт",
            "bytes"
    ),
    SAVE_ALERT_TITLE(
            "Информация",
            "Information"
    ),
    SAVE_ALERT_HEADER(
            "Успешно сохранено",
            "Save successful"
    ),
    SAVE_ALERT_CONTEXT(
            "Нажмите ОК, чтобы закрыть это окно",
            "Click OK to close this window"
    ),
    LANGUAGE_ALERT_TITLE(
            "Смена языка",
            "Language changing"
    ),
    LANGUAGE_ALERT_HEADER(
            "Текущий прогресс будет потерян",
            "Current progress will be lost"
    ),
    LANGUAGE_ALERT_CONTEXT(
            "Нажмите \'Сменить\', чтобы продолжить. \'Отмена\' для отмены",
            "Click \'Ok\' to continue. To cancel, click \'Cancel\'"
    ),
    LANGUAGE_CHANGE_BUTTON(
            "Сменить",
            "Ok"
    ),
    LANGUAGE_CANCEL_BUTTON(
            "Отмена",
            "Cancel"
    ),
    HELP_MENU_ITEM(
            "Помощь",
            "Help"
    ),
    ABOUT_MENU_ITEM(
            "Об Easy Png",
            "About Easy Png"
    ),
    AUTHOR(
            "Автор",
            "Author"
    ),
    APPLICATION_VERSION(
            "Версия приложения",
            "Application version"
    ),
    UPDATE_MENU_ITEM(
            "Обновить",
            "Update"
    ),
    CLOSE_BUTTON(
            "Закрыть",
            "Close"
    ),
    YOU_USE_LAST_VERSION(
            "Вы используете самую свежую версию «Easy Png»",
            "You are using the latest version of «Easy Png»"
    ),
    PATCH_NOTE_FILE(
            "patchNote_russian",
            "patchNote_english"
    ),
    GET_PATCH_NOTE_ERROR(
            "Не удалось получить описание обновления. Сервис временно недоступен",
            "Failed to get update description. Service is temporarily unavailable"
    ),
    ;


    public String russian;
    public String english;

    Dict(String russian, String english) {
        this.russian = russian;
        this.english = english;
    }
}
