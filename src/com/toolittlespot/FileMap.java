package com.toolittlespot;

import com.toolittlespot.elements.FileElement;

import java.util.*;

public class FileMap{
    private HashMap<String, FileElement> files;

    {
        files = new HashMap<>();
    }

    public boolean putIfDontExists(FileElement file){
        FileElement prevVal = files.get(file.getFileNameToSave());
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

    public Collection<FileElement> getFiles(){
        return this.files.values();
    }

    private String createNewName(FileElement file) {
        String extension = "." + FileElement.getExtension(file.getFileNameToSave());
        String copy = "_copy";
        int i = 1;
        String newFileName = null;
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
