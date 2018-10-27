package com.toolittlespot.convert;

import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ConvertFileManager extends Thread{
    final static Logger LOGGER = Logger.getLogger(ConvertFileManager.class);
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
        System.out.println("finished");
    }

    private void convert() throws InterruptedException {
        ExecutorService executor = application.getExecutorService();
        List<ConvertFile> threads = new ArrayList<>();

        for (FileElement file: files) {
            ConvertFile thread = new ConvertFile(application, file);
            threads.add(thread);
        }

        executor.invokeAll(threads);
        application.getButtons().setConvertedButtonsState();
        application.getDraggableBox().enableDraggable();
    }
}
