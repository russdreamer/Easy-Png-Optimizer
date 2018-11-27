package main.java.com.toolittlespot.utils;

import main.java.com.toolittlespot.elements.ImageElement;

import java.util.*;

public class FileMap{
    private HashMap<String, ImageElement> files;

    {
        files = new HashMap<>();
    }

    /**
     * adding file to the file Set
     * @param file file to add
     * @return (true) if file wasn't added before
     */
    boolean putIfDoesNotExist(ImageElement file){
        ImageElement prevVal = files.get(file.getFileNameToSave());
        if (prevVal == null){
            files.put(file.getFileNameToSave(), file);
            return true;
        }
        else if (prevVal.getFileNameToSave().equals(file.getFileNameToSave())){
            if (prevVal.getFile().getAbsolutePath().equals(file.getFile().getAbsolutePath())){
                return false;
            }
            else {
                String newNameToSave = createNewName(file);
                file.setFileNameToSave(newNameToSave);
                files.put(newNameToSave, file);
                return true;
            }
        }
        else {
            files.put(file.getFileNameToSave(), file);
            return true;
        }
    }

    /**
     * creating new file name to avoid name collision when all optimized files will be saved into one directory
     * @param file file to change name
     * @return new file name
     */
    private String createNewName(ImageElement file) {
        String extension = "." + AppUtils.getExtension(file.getFileNameToSave());
        String copy = "_copy";
        int i = 1;
        String newFileName;
        do {
            newFileName = file.getFileNameToSave() + copy + i + extension;
            i++;
        }
        while (files.get(newFileName) != null);

        return newFileName;
    }

    public void clear() {
        this.files.clear();
    }
}
