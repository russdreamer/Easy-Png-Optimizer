package main.java.com.toolittlespot.convert;

import main.java.com.toolittlespot.elements.ImageElement;
import main.java.com.toolittlespot.utils.AppUtils;
import main.java.com.toolittlespot.elements.ApplicationArea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ConvertFileManager extends Thread{
    private ApplicationArea application;
    private Collection<ImageElement> files;

    /**
     * @param application instance of running application element
     * @param files wrapped files to optimize
     */
    public ConvertFileManager(ApplicationArea application, Collection<ImageElement> files) {
        this.application = application;
        this.files = files;
    }

    @Override
    public void run() {
        try {
            optimize();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * run files optimization in different threads. Number of threads = number of available processor cores
     * @throws InterruptedException if optimization process will stopped by user or by error
     */
    private void optimize() throws InterruptedException {
        ExecutorService executor = application.getExecutorService();
        List<ConvertFile> threads = new ArrayList<>();

        for (ImageElement file: files) {
            if (file != null){
                ConvertFile thread = new ConvertFile(application, file);
                threads.add(thread);
            }
        }

        executor.invokeAll(threads);

        /* check if all threads are terminated */
        waitIfShutdown(executor);

        AppUtils.setButtonState(application);
        application.getDraggableBox().enableDraggable();
        application.getLanguageButton().getMenuButton().setDisable(false);
    }

    /**
     * wait executor terminated if it is shutdown
     * @param executor executor service
     */
    private void waitIfShutdown(ExecutorService executor) {
        if (executor.isShutdown()) {
            while (! executor.isTerminated() ) {
                try {
                    executor.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
