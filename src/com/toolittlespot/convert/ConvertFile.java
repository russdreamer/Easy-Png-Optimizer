package com.toolittlespot.convert;

import com.toolittlespot.controller.Main;
import com.toolittlespot.elements.FileElement;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.toolittlespot.Constants.COMPRESSOR_PATH;
import static com.toolittlespot.Constants.DEFAULT_FILE_PATH;

public class ConvertFile extends Task{
    private FileElement fileElement;

    public ConvertFile(FileElement fileElement) {
        this.fileElement = fileElement;
    }

    private void processFile(FileElement fileElement) {
        String filePath = fileElement.getFile().getAbsolutePath();
        String fileNameToSave = fileElement.getFileNameToSave();
        try {
            convertFile(filePath, DEFAULT_FILE_PATH + fileNameToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertFile(String pathFrom, String pathTo) throws IOException {
        Main.runtime = Runtime.getRuntime();
        Process process = null;
        String[] processCommand = {COMPRESSOR_PATH, pathFrom, pathTo};

        try {
            String command = COMPRESSOR_PATH + " " + pathFrom + " " + pathTo;
            process = Main.runtime.exec(processCommand);
            System.out.println(command);

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        System.out.println(br.readLine());
    }

    @Override
    public String call() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processFile(fileElement);
        return fileElement.getFileNameToSave();
    }
}
