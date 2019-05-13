package com.projects.myproject.fileprocessor.scanner;

import com.projects.myproject.fileprocessor.Codes;
import com.projects.myproject.fileprocessor.FileCommonTools;
import localhost._8080.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class FileScannerRunner {

    private static final Logger log = LoggerFactory.getLogger(FileScannerRunner.class);

    private ExecutorService executor;

    public FileScannerRunner() {
        if (this.executor == null) {
            this.executor = Executors.newFixedThreadPool(4);
        }
    }

    /**
     * Search for the required value in all files in the directory
     *
     * @param   number - required value for search
     * @return  Result object with
     *      1) status of the operation
     *      2) list of files that contain required value
     *      3) error description (if error exists)
     */
    public Result getResultByNumber(Long number) {
        List<FileScannerThread> fileScannerThreadList = new ArrayList<>();
        StringBuilder resultString = new StringBuilder();
        Result result = new Result();
        File[] listOfFiles = FileCommonTools.getAllFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            result.setCode(Codes.NOT_FOUND.toString());
            return result;
        }
        log.info("listOfFiles.length : {}", listOfFiles.length);
        for (File curFile : listOfFiles) {
            fileScannerThreadList.add(new FileScannerThread(curFile, number));
        }
        List<Future<String>> futureList = null;
        try {
            futureList = executor.invokeAll(fileScannerThreadList);
            for (Future<String> future : futureList) {
                log.info("future.get() : {}", future.get());
                if (resultString.length() != 0 && future.get() != null) {
                    resultString.append(",").append(future.get());
                } else if (future.get() != null) {
                    resultString.append(future.get());
                }
            }
        } catch (Exception e) {
            result.setError(e.getMessage());
            result.setCode(Codes.ERROR.toString());
            return result;
        }
        if (resultString.length() == 0) {
            result.setCode(Codes.NOT_FOUND.toString());
            return result;
        }
        result.setFileNames(resultString.toString());
        result.setCode(Codes.OK.toString());
        return result;
    }
}
