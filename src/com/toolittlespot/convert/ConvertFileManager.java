package com.toolittlespot.convert;

import com.toolittlespot.AppUtils;
import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ConvertFileManager extends Thread{
    private ApplicationArea application;
    private Collection<FileElement> files;

    public ConvertFileManager(ApplicationArea application, Collection<FileElement> files) {
        this.application = application;
        this.files = files;
    }

    @Override
    public void run() {
        try {
            convert();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void convert() throws InterruptedException {
        ExecutorService executor = application.getExecutorService();
        List<ConvertFile> threads = new ArrayList<>();

        for (FileElement file: files) {
            if (file != null){
                ConvertFile thread = new ConvertFile(application, file);
                threads.add(thread);
            }
        }

        executor.invokeAll(threads);
        AppUtils.setButtonState(application);
        application.getDraggableBox().enableDraggable();
        application.getLanguageButton().getMenuButton().setDisable(false);
    }
}
