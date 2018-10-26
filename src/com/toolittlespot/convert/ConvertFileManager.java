package com.toolittlespot.convert;

import com.toolittlespot.elements.ApplicationArea;
import com.toolittlespot.elements.FileElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConvertFileManager extends Thread{
    private ApplicationArea application;
    private Collection<FileElement> files;

    public ConvertFileManager(ApplicationArea application, Collection<FileElement> files) {
        this.application = application;
        this.files = files;
    }

    @Override
    public void run() {
        convert();
        System.out.println("finished");
    }

    private void convert() {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<ConvertFile> threads = new ArrayList<>();

        for (FileElement file: files) {
            ConvertFile thread = new ConvertFile(file);
            threads.add(thread);
            executor.execute(thread);
        }

        /* wait till all threads are completed */
        for (ConvertFile item: threads) {
            try {
                item.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        application.getButtons().setConvertedButtonsState();
    }
}
