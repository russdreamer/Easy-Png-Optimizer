package main.java.com.toolittlespot.elements;

import main.java.com.toolittlespot.utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PngElement extends ImageElement {

    public PngElement(File file, int rowNumber) {
        super(file, rowNumber);
    }

    public boolean optimize(ApplicationArea application) {
        String[] processCommand = {
                Constants.COMPRESSOR_PATH,
                "--strip", "--speed", "1", "--force", "--output", //options
                getTempFilePath(), getTempFilePath()};

        Process process;
        BufferedReader reader;
        try {
            process = Runtime.getRuntime().exec(processCommand);
            application.getProcessList().add(process);
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            reader.close();
        }
        catch (IOException | InterruptedException e) {
            return false;
        }
        return true;
    }
}
