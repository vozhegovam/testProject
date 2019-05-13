package com.projects.myproject.fileprocessor.creator;

import com.projects.myproject.fileprocessor.FileCommonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FileBuilderRunner {
    private static final Logger log = LoggerFactory.getLogger(FileBuilderRunner.class);

    private ExecutorService executor;

    public FileBuilderRunner() {
        if (this.executor == null) {
            this.executor = Executors.newFixedThreadPool(4);
        }
    }

    /**
     * The command creates required number of files(size ~1gb) in 4 different threads
     *
     * @param numberOfFiles number of files to create
     */
    public void generateFiles(Integer numberOfFiles) {
        log.info("required number Of Files : {}", numberOfFiles);
        if (numberOfFiles == null || numberOfFiles == 0) {
            throw new RuntimeException("Input Number should be > 0");
        }
        List<Runnable> tasks = new ArrayList<>();
        Integer fileName = FileCommonTools.generateNewFileName();
        for (int i = 1; i <= numberOfFiles; i++) {
            tasks.add(new FileBuilderThread(fileName));
            fileName++;
        }
        CompletableFuture<?>[] futures = tasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executor))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        executor.shutdown();
    }
}
