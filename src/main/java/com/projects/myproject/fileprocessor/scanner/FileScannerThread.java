package com.projects.myproject.fileprocessor.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class FileScannerThread implements Callable<String> {

    private static final Logger log = LoggerFactory.getLogger(FileScannerThread.class);
    private File currentFile;
    private Long num;

    FileScannerThread(File currentFile, Long num) {
        this.currentFile = currentFile;
        this.num = num;
    }

    /**
     * Search for the required value for one file in the directory
     *
     * @return  - name of the file if the required value is exist
     */
    @Override
    public String call() {
        boolean isExists = false;
        Scanner scanner;
        long currentNum = 0;
        log.info("Thread : " + Thread.currentThread().getName() + ", Folder : " + currentFile.getAbsolutePath());
        try {
            scanner = new Scanner(currentFile).useDelimiter(",");
            while (scanner.hasNext() && !isExists) {
                currentNum = scanner.nextLong();
                isExists = num == currentNum;
            }
            log.info("curFileContain : " + currentFile + " isExists = " + isExists + " currentNum : " + currentNum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return isExists ? currentFile.getName() : null;
    }
}
